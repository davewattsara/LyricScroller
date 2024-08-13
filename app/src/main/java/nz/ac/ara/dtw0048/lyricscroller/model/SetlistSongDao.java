package nz.ac.ara.dtw0048.lyricscroller.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SetlistSongDao {
    @Query("SELECT * FROM setlist" +
            " LEFT JOIN setlistsong ON setlistsong.setlist_name = setlist.setlist_name" +
            " LEFT JOIN song ON song.song_name = setlistsong.song_name " +
            " AND song.artist_name = setlistsong.artist_name")
    Single<Map<Setlist, List<Song>>> setlistsWithSongs();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(SetlistSong setlistSong);

    @Delete
    Completable delete(SetlistSong setlistSong);
}
