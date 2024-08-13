package nz.ac.ara.dtw0048.lyricscroller.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.model.SetlistOnClickListener;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;
import nz.ac.ara.dtw0048.lyricscroller.model.SongDao;
import nz.ac.ara.dtw0048.lyricscroller.model.SongOnClickListener;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.MyViewHolder> {

    private final Context context;
    private final List<Song> songs;
    private final SongOnClickListener listener;

    public SongRecyclerViewAdapter (Context context, SongOnClickListener listener, List<Song> songs) {
        this.context = context;
        this.listener = listener;
        this.songs = songs;
    }

    @NonNull
    @Override
    public SongRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.song_item, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.setSong(songs.get(position));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView songNameTextView, artistTextView;
        private final SongOnClickListener listener;
        private Song song;
        public MyViewHolder(@NonNull View itemView, SongOnClickListener listener) {
            super(itemView);
            songNameTextView = itemView.findViewById(R.id.songItemNameTextView);
            artistTextView = itemView.findViewById(R.id.songItemArtistTextView);
            this.listener = listener;
            itemView.findViewById(R.id.songCardView).setOnClickListener((v) -> listener.onSongClicked(song));
        }

        public void setSong(Song song) {
            this.song = song;
            songNameTextView.setText(song.songName);
            artistTextView.setText(song.artistName);
        }
    }
}
