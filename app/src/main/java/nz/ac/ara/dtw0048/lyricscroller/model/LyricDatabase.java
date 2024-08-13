package nz.ac.ara.dtw0048.lyricscroller.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Song.class, Setlist.class, SetlistSong.class}, version = 1, exportSchema = false)
public abstract class LyricDatabase extends RoomDatabase {
    public abstract SongDao songDao();
    public abstract SetlistDao setlistDao();
    public abstract SetlistSongDao setlistSongDao();
}
