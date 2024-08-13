package nz.ac.ara.dtw0048.lyricscroller.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

public class SearchWebTask implements SingleOnSubscribe<Song> {

    private final String query;
    public SearchWebTask(String query) {
        this.query = query;
    }

    @Override
    public void subscribe(@NonNull SingleEmitter<Song> emitter) throws Throwable {
        Song song = doGoogleSearch(query);
        emitter.onSuccess(song);
    }

    private Song doGoogleSearch(String query) throws IOException {
        Document doc = Jsoup.connect("https://www.google.com/search?q=" + query + " lyrics").get();
        String lyrics = getLyrics(doc);
        String title = getTitle(doc);
        String artist = getArtist(doc);

        return new Song(title, artist, lyrics);
    }

    private String getLyrics(Document doc) throws IOException {
        StringBuilder songBuilder = new StringBuilder();
        Element songElement = doc.selectFirst("div[data-lyricid] > div");
        if (songElement == null)
            throw new IOException("Lyrics not found.");
        songElement = songElement.child(1);
        if (songElement == null)
            throw new IOException("Lyrics not found.");
        for (int p = 0; p < songElement.childrenSize(); p++) {
            for (Element lineElement : songElement.child(p).select("span")) {
                songBuilder.append(lineElement.text()).append("\n");
            }
            songBuilder.append("\n");
        }
        return songBuilder.toString();
    }

    private String getTitle(Document doc) throws IOException {
        //return doc.selectFirst("div[data-attrid='title']").text();
        Element titleElement = doc.selectFirst("div[data-attrid='title']");
        if (titleElement == null)
            throw new IOException("Title not found.");
        String result = titleElement.text();
        if (result == null || result.length() == 0)
            throw new IOException("Title not found.");
        return result;
    }

    private String getArtist(Document doc) throws IOException {
        Element artistElement = doc.selectFirst("div[data-attrid='subtitle'] > span");
        if (artistElement == null)
            throw new IOException("Artist not found.");
        String fullText = artistElement.text();
        if (fullText == null || fullText.length() <= 8)
            throw new IOException("Artist not found.");
        return fullText.substring(8);
    }
}
