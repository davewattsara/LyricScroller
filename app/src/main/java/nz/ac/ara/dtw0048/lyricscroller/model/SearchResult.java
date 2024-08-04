package nz.ac.ara.dtw0048.lyricscroller.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SearchResult implements Parcelable {
    private final String songName;
    private final String artistName;
    private final String lyrics;

    public SearchResult (String songName, String artistName, String lyrics) {
        this.songName = songName;
        this.artistName = artistName;
        this.lyrics = lyrics;
    }

    private SearchResult(Parcel parcel) {
        songName = parcel.readString();
        artistName = parcel.readString();
        lyrics = parcel.readString();
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getLyrics() {
        return lyrics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(artistName);
        dest.writeString(lyrics);
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel source) {
            return new SearchResult(source);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };
}
