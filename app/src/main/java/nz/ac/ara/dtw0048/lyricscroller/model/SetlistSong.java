package nz.ac.ara.dtw0048.lyricscroller.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        primaryKeys = {"song_name", "artist_name", "setlist_name"}
)
public class SetlistSong {
    @ColumnInfo(name = "song_name")
    @NonNull
    public String songName;

    @ColumnInfo(name = "artist_name")
    @NonNull
    public String artistName;

    @ColumnInfo(name = "setlist_name")
    @NonNull
    public String setlistName;

    public SetlistSong(@NonNull String songName, @NonNull String artistName, @NonNull String setlistName) {
        this.songName = songName;
        this.setlistName = setlistName;
        this.artistName = artistName;
    }
}
