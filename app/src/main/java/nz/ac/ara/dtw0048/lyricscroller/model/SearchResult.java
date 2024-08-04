package nz.ac.ara.dtw0048.lyricscroller.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SearchResult implements Parcelable {
    private final String songName;
    private final String artistName;
    private final int id;

    public SearchResult (String songName, String artistName, int id) {
        this.songName = songName;
        this.artistName = artistName;
        this.id = id;
    }

    private SearchResult(Parcel parcel) {
        songName = parcel.readString();
        artistName = parcel.readString();
        id = parcel.readInt();
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(artistName);
        dest.writeInt(id);
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
