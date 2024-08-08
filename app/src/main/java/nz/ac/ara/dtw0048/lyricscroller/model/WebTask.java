package nz.ac.ara.dtw0048.lyricscroller.model;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

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

    @Nullable
    private SearchResult doGoogleSearch(String query) {
        try {
            Document doc = Jsoup.connect("https://www.google.com/search?q=" + query + " lyrics").get();
            String lyrics = getLyrics(doc);
            String title = getTitle(doc);
            String artist = getArtist(doc);

            if (lyrics == null)
                return null;

            return new SearchResult(title, artist, lyrics);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private String getLyrics(Document doc) {
        StringBuilder songBuilder = new StringBuilder();
        Element songElement = doc.selectFirst("div[data-lyricid] > div");
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
        return songBuilder.toString();
    }

    @Nullable
    private String getTitle(Document doc) {
        return doc.selectFirst("div[data-attrid='title']").text();
    }

    @Nullable
    private String getArtist(Document doc) {
        String fullText = doc.selectFirst("div[data-attrid='subtitle'] > span").text();
        if (fullText == null)
            return  null;
        return fullText.substring(8);
    }

    @Override
    protected void onPostExecute(SearchResult result) {
        listener.onWebTaskResult(result);
    }
}
