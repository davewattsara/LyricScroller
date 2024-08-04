package nz.ac.ara.dtw0048.lyricscroller.controller;

import android.util.Log;

import nz.ac.ara.dtw0048.lyricscroller.model.LyricScroller;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResultListener;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResult;

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

    @Override
    public void onSearchResultsFound(SearchResult[] results) {
        if (searchResultListener != null) {
            searchResultListener.onSearchResultsFound(results);
        }
    }
}
