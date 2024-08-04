package nz.ac.ara.dtw0048.lyricscroller.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LyricScroller implements HttpTask.HttpTaskListener {

    private final SearchResultListener listener;

    public LyricScroller(SearchResultListener listener) {
        this.listener = listener;
    }

    public void getSearchResults(String query) {
        String url = "https://api.genius.com/search?q=" + query;
        new HttpTask(this, "search").execute(url);
    }

    @Override
    public void onHttpTaskResult(HttpTask.HttpTaskResult result) {
        JSONObject json = result.getJsonObject();
        try {

            switch (result.getType()) {
                case "search":
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
                    listener.onSearchResultsFound(searchResults);
                    break;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
