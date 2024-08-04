package nz.ac.ara.dtw0048.lyricscroller.model;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WebTask extends AsyncTask<String, Void, SearchResult> {

    public interface WebTaskListener {
        void onWebTaskResult(SearchResult result);
    }

    private final WebTaskListener listener;

    public WebTask (WebTaskListener listener) {
        this.listener = listener;
    }
    @Override
    protected SearchResult doInBackground(String... strings) {
        return doGoogleSearch(strings[0]);
    }

    private SearchResult doGoogleSearch(String query) {
        try {
            StringBuilder songBuilder = new StringBuilder();
            Document doc = Jsoup.connect("https://www.google.com/search?q=" + query + " lyrics").get();
            Element songElement = doc.selectFirst("div[data-lyricid] > div");//.child(1);
            if (songElement == null) {
                return null;
            }
            songElement = songElement.child(1);
            if (songElement == null) {
                return null;
            }
            for (int p = 0; p < songElement.childrenSize(); p++) {
                for (Element lineElement : songElement.child(p).select("span")) {
                    songBuilder.append(lineElement.text()).append("\n");
                }
                songBuilder.append("\n");
            }
            Log.i("WebTask", songBuilder.toString());
            return new SearchResult("", "", songBuilder.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(SearchResult result) {
        listener.onWebTaskResult(result);
    }
}
