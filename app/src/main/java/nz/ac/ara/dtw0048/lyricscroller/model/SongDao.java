package nz.ac.ara.dtw0048.lyricscroller.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SongDao {
    @Query("SELECT * FROM Song")
    Single<List<Song>> getAll();

    @Query("SELECT * FROM Song WHERE artist_name LIKE :artist")
    Single<List<Song>> findByArtist(String artist);

    @Query("SELECT * FROM Song WHERE artist_name LIKE :artist AND song_name LIKE :title LIMIT 1")
    Single<Song> findSong(String artist, String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(Song song);

    @Delete
    Completable delete(Song song);

}
