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
public interface SetlistDao {
    @Query("SELECT * FROM setlist")
    Single<List<Setlist>> getAll();

    @Query("SELECT * FROM setlist WHERE setlist.setlist_name LIKE :name LIMIT 1")
    Single<Setlist> findByName(String name);

    @Query("SELECT * FROM song" +
            " INNER JOIN setlistsong ON song.song_name = setlistsong.song_name" +
            " AND song.artist_name = setlistsong.artist_name" +
            " WHERE setlistsong.setlist_name LIKE :setlistName" )
    Single<List<Song>> findSongs(String setlistName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(Setlist setlist);

    @Delete
    Completable delete(Setlist setlist);
}
