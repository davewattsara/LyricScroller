package nz.ac.ara.dtw0048.lyricscroller.model;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
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

    public Completable updateSong(Song song) {
        return database.songDao().update(song)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Song>> findByArtist(String artist) {
        return database.songDao().findByArtist(artist)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Song> findSong(String songName, String artist) {
        return database.songDao().findSong(songName, artist)
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

    public Completable deleteSetlistSong(SetlistSong setlistSong) {
        return database.setlistSongDao().delete(setlistSong)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<List<Song>> getSetlistSongs(String setlistName) {
        return database.setlistDao().findSongs(setlistName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Map<Setlist, List<Song>>> getSetlistsAndSongs() {
        return database.setlistSongDao().setlistsAndSongs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable deleteSong(String songName, String artistName) {
        return Completable.mergeArray(
                database.songDao().delete(songName, artistName),
                database.setlistSongDao().deleteSong(songName, artistName)
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable renameAndUpdateSongOld(String oldSongName, String oldArtist, Song newSong) {
        return database.setlistSongDao().findBySong(oldSongName, oldArtist).flatMapCompletable(setlistSongs -> {
            ArrayList<SetlistSong> newSetlistSongs = new ArrayList<>();
            for (SetlistSong setlistSong : setlistSongs) {
                newSetlistSongs.add(new SetlistSong(newSong.songName,
                        newSong.artistName, setlistSong.setlistName));
            }
            return Completable.mergeArray(
                    database.songDao().delete(oldSongName, oldArtist),
                    database.setlistSongDao().delete(setlistSongs),
                    database.setlistSongDao().insert(newSetlistSongs),
                    database.songDao().insert(newSong)
            );
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable renameAndUpdateSong(String oldSongName, String oldArtist, Song newSong) {
        if (newSong.songName.trim().equals("") || newSong.artistName.trim().equals(""))
            return Completable.error(new BlankNameException());
        return database.songDao().getAll().flatMapCompletable(songs -> {
            if (songs.contains(newSong))
                return Completable.error(new DuplicateNameException());
            return database.setlistSongDao().findBySong(oldSongName, oldArtist).flatMapCompletable(setlistSongs -> {
                ArrayList<SetlistSong> newSetlistSongs = new ArrayList<>();
                for (SetlistSong setlistSong : setlistSongs) {
                    newSetlistSongs.add(new SetlistSong(newSong.songName,
                            newSong.artistName, setlistSong.setlistName));
                }
                return Completable.mergeArray(
                        database.songDao().delete(oldSongName, oldArtist),
                        database.setlistSongDao().delete(setlistSongs),
                        database.setlistSongDao().insert(newSetlistSongs),
                        database.songDao().insert(newSong)
                );
            });
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Completable renameSetlist(Setlist oldSetlist, Setlist newSetlist) {
        if (newSetlist.setlistName.trim().equals(""))
            return Completable.error(new BlankNameException());
        return database.setlistDao().getAll().flatMapCompletable(setlists -> {
            if (setlists.contains(newSetlist))
                return Completable.error(new DuplicateNameException());
            return database.setlistSongDao().findBySetlist(oldSetlist.setlistName).flatMapCompletable(setlistSongs -> {
                ArrayList<SetlistSong> newSetlistSongs = new ArrayList<>();
                for (SetlistSong setlistSong : setlistSongs) {
                    newSetlistSongs.add(new SetlistSong(setlistSong.songName,
                            setlistSong.artistName, newSetlist.setlistName));
                }
                return Completable.mergeArray(
                        database.setlistDao().delete(oldSetlist),
                        database.setlistSongDao().delete(setlistSongs),
                        database.setlistSongDao().insert(newSetlistSongs),
                        database.setlistDao().insert(newSetlist)
                );
            });
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
