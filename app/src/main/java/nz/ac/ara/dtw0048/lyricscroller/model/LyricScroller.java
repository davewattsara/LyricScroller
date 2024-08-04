package nz.ac.ara.dtw0048.lyricscroller.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LyricScroller implements WebTask.WebTaskListener {

    private static final String HTTP_TYPE_SEARCH = "search";
    private static final String HTTP_TYPE_LYRIC = "lyric";

    private final SearchResultListener searchResultListener;

    public LyricScroller(SearchResultListener listener) {
        this.searchResultListener = listener;
    }

    public void getSearchResults(String query) {
        //String url = "https://api.genius.com/search?q=" + query;
        //new HttpTask(this, HTTP_TYPE_SEARCH).execute(url);
        new WebTask(this).execute(query);
    }

    //public void getLyrics(int id) {
    //    String url = "https://api.genius.com/songs/" + id;
    //    new HttpTask(this, HTTP_TYPE_LYRIC).execute(url);
    //}

    @Override
    public void onWebTaskResult(SearchResult result) {
        /*
        JSONObject json = result.getJsonObject();
        try {

            switch (result.getType()) {
                case HTTP_TYPE_SEARCH:
                    Log.i("JSON_RESULT_SEARCH", json.toString(2));
                    JSONArray hits = json.getJSONObject("response").getJSONArray("hits");
                    int length = hits.length();
                    SearchResult[] searchResults = new SearchResult[length];
                    for (int i = 0; i < length; i++) {
                        JSONObject thisHit = hits.getJSONObject(i).getJSONObject("result");
                        searchResults[i] = new SearchResult(
                                thisHit.getString("title"),
                                thisHit.getString("artist_names"),
                                thisHit.getInt("id")
                        );
                    }
                    searchResultListener.onSearchResultsFound(searchResults);
                    break;
                case HTTP_TYPE_LYRIC:
                    Log.i("JSON_RESULT_LYRIC", json.toString(2));
                    break;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
         */

        searchResultListener.onSearchResultsFound(result);
    }
}
