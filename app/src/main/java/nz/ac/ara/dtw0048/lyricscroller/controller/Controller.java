package nz.ac.ara.dtw0048.lyricscroller.controller;

import android.content.Context;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import nz.ac.ara.dtw0048.lyricscroller.model.LyricScroller;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResultListener;
import nz.ac.ara.dtw0048.lyricscroller.model.Setlist;
import nz.ac.ara.dtw0048.lyricscroller.model.SetlistSong;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;

public class Controller implements SearchResultListener {

    private static volatile Controller INSTANCE = null;
    private SearchResultListener searchResultListener;
    private final LyricScroller lyricScroller;

    private Controller () {
        lyricScroller = new LyricScroller(this);
        searchResultListener = null;
    }

    public static Controller getInstance() {
        if (INSTANCE == null) {
            synchronized (Controller.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Controller();
                }
            }
        }
        return INSTANCE;
    }

    public void setSearchResultListener(SearchResultListener listener) {
        searchResultListener = listener;
    }

    public void searchForLyrics(String query) {
        lyricScroller.getSearchResults(query);
    }



    public void openDatabase(Context context) {
        lyricScroller.openDatabase(context);
    }

    public void closeDatabase() {
        lyricScroller.closeDatabase();
    }
    public Completable addSong(Song song) {
        return lyricScroller.addSong(song);
    }

    public Single<Song> findSong(String songName, String artist) {
        return lyricScroller.findSong(songName, artist);
    }

    public Completable updateSong(Song song) {
        return lyricScroller.updateSong(song);
    }

    public Completable renameAndUpdateSong(String oldSongName, String oldArtist, Song song) {
        return lyricScroller.renameAndUpdateSong(oldSongName, oldArtist, song);
    }

    public Completable renameSetlist(Setlist oldSetlist, Setlist newSetlist) {
        return lyricScroller.renameSetlist(oldSetlist, newSetlist);
    }

    public Single<List<Song>> findByArtist(String artist) {
        return lyricScroller.findByArtist(artist);
    }

    public Completable addSetlist(String setlistName) {
        return lyricScroller.addSetlist(setlistName);
    }

    public Completable addSetlistSong(SetlistSong setlistSong) {
        return lyricScroller.addSetlistSong(setlistSong);
    }

    public Completable deleteSetlistSong(SetlistSong setlistSong) {
        return lyricScroller.deleteSetlistSong(setlistSong);
    }

    public Single<List<Song>> getSetlistSongs(String setlistName) {
        return lyricScroller.getSetlistSongs(setlistName);
    }

    public Single<Map<Setlist, List<Song>>> getSetlistsAndSongs() {
        return lyricScroller.getSetlistsAndSongs();
    }

    @Override
    public void onSearchResultsFound(Song result) {
        if (searchResultListener != null) {
            searchResultListener.onSearchResultsFound(result);
        }
    }
}
