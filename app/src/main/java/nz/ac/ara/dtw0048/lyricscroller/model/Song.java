package nz.ac.ara.dtw0048.lyricscroller.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"song_name", "artist_name"})
public class Song implements Parcelable {

    private static final float DEFAULT_SCROLL_SPEED = 0.5f;

    @ColumnInfo(name = "song_name")
    @NonNull
    public String songName;

    @ColumnInfo(name = "artist_name")
    @NonNull
    public String artistName;

    @ColumnInfo(name = "lyrics")
    public String lyrics;

    @ColumnInfo(name = "scroll_speed")
    public float scrollSpeed;

    public Song (@NonNull String songName, @NonNull String artistName, String lyrics) {
        this.songName = songName;
        this.artistName = artistName;
        this.lyrics = lyrics;
        this.scrollSpeed = DEFAULT_SCROLL_SPEED;
    }

    private Song(Parcel parcel) {
        String songName = parcel.readString();
        this.songName = songName == null ? "" : songName;
        String artistName = parcel.readString();
        this.artistName = artistName == null ? "" : artistName;
        lyrics = parcel.readString();
        scrollSpeed = parcel.readFloat();
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
        dest.writeFloat(scrollSpeed);
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int hashCode() {
        return (songName + artistName).hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Song))
            return false;
        Song s = (Song)obj;
        return s.songName.equals(songName) && s.artistName.equals(artistName);
    }
}
