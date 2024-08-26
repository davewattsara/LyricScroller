package nz.ac.ara.dtw0048.lyricscroller.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class SongDao {
    @Query("SELECT * FROM Song")
    public abstract Single<List<Song>> getAll();

    @Query("SELECT * FROM Song WHERE artist_name LIKE :artist")
    public abstract Single<List<Song>> findByArtist(String artist);

    @Query("SELECT * FROM Song WHERE artist_name = :artist AND song_name = :songName LIMIT 1")
    public abstract Single<List<Song>> findSong(String songName, String artist);

    @Query("SELECT * FROM setlist" +
            " INNER JOIN setlistsong ON setlistsong.setlist_name = setlist.setlist_name" +
            " WHERE setlistsong.song_name = :songName AND setlistsong.artist_name = :artistName")
    public abstract Single<List<Setlist>> findSetlists(String songName, String artistName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Completable insert(Song song);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable update(Song song);

    @Query("DELETE FROM song WHERE song_name = :songName AND artist_name = :artistName")
    public abstract Completable delete(String songName, String artistName);

    @Query("DELETE FROM song WHERE NOT EXISTS (" +
            " SELECT song_name, artist_name FROM setlistsong" +
            " WHERE setlistsong.song_name = song.song_name" +
            " AND setlistsong.artist_name = song.artist_name)")
    public abstract Completable deleteSongsNotInAnySetlist();
}
