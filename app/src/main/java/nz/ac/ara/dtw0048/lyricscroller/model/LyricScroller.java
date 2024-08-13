package nz.ac.ara.dtw0048.lyricscroller.model;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LyricScroller {

    private static final String HTTP_TYPE_SEARCH = "search";
    private static final String HTTP_TYPE_LYRIC = "lyric";

    private final SearchResultListener searchResultListener;
    private LyricDatabase database;

    public LyricScroller(SearchResultListener listener) {
        this.searchResultListener = listener;
    }

    public void getSearchResults(String query) {
        //new WebTask(this).execute(query);
        Single.create(new SearchWebTask(query))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Song>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Song song) {
                searchResultListener.onSearchResultsFound(song);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                searchResultListener.onSearchResultsFound(null);
            }
        });
    }


    public void openDatabase(Context context) {
        database = Room.inMemoryDatabaseBuilder(context, LyricDatabase.class).build();
    }

    public void closeDatabase() {
        database.close();
    }

    public Completable addSong(Song song) {
        return database.songDao().insert(song)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Song>> findByArtist(String artist) {
        return database.songDao().findByArtist(artist)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable addSetlist(String setlistName) {
        return database.setlistDao().insert(new Setlist(setlistName))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable addSetlistSong(SetlistSong setlistSong) {
        return database.setlistSongDao().insert(setlistSong)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Song>> getSetlistSongs(String setlistName) {
        return database.setlistDao().findSongs(setlistName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Map<Setlist, List<Song>>> getSetlistsAndSongs() {
        return database.setlistSongDao().setlistsWithSongs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
