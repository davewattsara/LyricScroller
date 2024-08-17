package nz.ac.ara.dtw0048.lyricscroller.model;

import androidx.room.ColumnInfo;
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
            " AND song.artist_name = setlistsong.artist_name" +
            " ORDER BY setlist_name, song_name")
    Single<Map<Setlist, List<Song>>> setlistsAndSongs();

    @Query("SELECT * FROM setlistsong" +
            " WHERE song_name = :songName AND artist_name = :artistName")
    Single<List<SetlistSong>> findBySong(String songName, String artistName);

    @Query("SELECT * FROM setlistsong" +
            " WHERE setlist_name = :setlistName")
    Single<List<SetlistSong>> findBySetlist(String setlistName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(SetlistSong... setlistSongs);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(List<SetlistSong> setlistSongs);

    @Delete
    Completable delete(SetlistSong... setlistSongs);
    @Delete
    Completable delete(List<SetlistSong> setlistSongs);

    @Query("DELETE FROM setlistsong WHERE song_name = :songName AND artist_name = :artistName")
    Completable deleteSong(String songName, String artistName);
}
