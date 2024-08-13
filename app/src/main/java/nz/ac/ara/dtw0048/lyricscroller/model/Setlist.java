package nz.ac.ara.dtw0048.lyricscroller.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Setlist implements Comparable<Setlist> {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "setlist_name")
    public String setlistName;

    public Setlist(@NonNull String setlistName) {
        this.setlistName = setlistName;
    }

    @Override
    public int hashCode() {
        return setlistName.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Setlist))
            return false;
        Setlist s = (Setlist) obj;
        return s.setlistName.equals(setlistName);
    }

    @Override
    public int compareTo(Setlist o) {
        return setlistName.compareTo(o.setlistName);
    }
}
