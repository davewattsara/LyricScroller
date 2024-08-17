package nz.ac.ara.dtw0048.lyricscroller.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Setlist implements Comparable<Setlist>, CharSequence, Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "setlist_name")
    public String setlistName;

    public Setlist(@NonNull String setlistName) {
        this.setlistName = setlistName;
    }

    private Setlist(Parcel parcel) {
        String name = parcel.readString();
        this.setlistName = name == null ? "" : name;
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

    @Override
    public int length() {
        return setlistName.length();
    }

    @Override
    public char charAt(int index) {
        return setlistName.charAt(index);
    }

    @NonNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return setlistName.subSequence(start, end);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(setlistName);
    }

    public static final Creator<Setlist> CREATOR = new Creator<Setlist>() {
        @Override
        public Setlist createFromParcel(Parcel source) {
            return new Setlist(source);
        }

        @Override
        public Setlist[] newArray(int size) {
            return new Setlist[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return setlistName;
    }
}
