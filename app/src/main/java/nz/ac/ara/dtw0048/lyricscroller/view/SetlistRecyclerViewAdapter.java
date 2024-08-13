package nz.ac.ara.dtw0048.lyricscroller.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.model.Setlist;
import nz.ac.ara.dtw0048.lyricscroller.model.SetlistOnClickListener;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;

public class SetlistRecyclerViewAdapter extends RecyclerView.Adapter<SetlistRecyclerViewAdapter.MyViewHolder> {

    private final Map<Setlist, List<Song>> setlistsAndSongs;
    private final Song songToAdd;
    private final List<Setlist> sortedSetlists;
    private final Context context;
    private final SetlistOnClickListener listener;

    public SetlistRecyclerViewAdapter(
            Context content,
            SetlistOnClickListener listener,
            Map<Setlist, List<Song>> setlistsAndSongs,
            Song songToAdd
    ) {
        this.setlistsAndSongs = setlistsAndSongs;
        this.songToAdd = songToAdd;
        this.sortedSetlists = new ArrayList<Setlist>(setlistsAndSongs.keySet());
        Collections.sort(this.sortedSetlists);
        this.context = content;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SetlistRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_setlist_item, parent, false);
        return new MyViewHolder(view, listener, songToAdd != null);
    }

    @Override
    public void onBindViewHolder(@NonNull SetlistRecyclerViewAdapter.MyViewHolder holder, int position) {
        Setlist setlist = sortedSetlists.get(position);
        boolean showButton = songToAdd != null;
        if (showButton) {
            List<Song> songs = setlistsAndSongs.get(setlist);
            if (songs != null) {
                showButton = !songs.contains(songToAdd);
            }
        }
        holder.setSetlist(setlist, showButton);
    }

    @Override
    public int getItemCount() {
        return sortedSetlists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView setlistNameTextView;
        private final Button button;
        private final SetlistOnClickListener listener;
        private Setlist setlist;

        public MyViewHolder(@NonNull View itemView, SetlistOnClickListener listener, boolean hasSongToAdd) {
            super(itemView);

            setlistNameTextView = itemView.findViewById(R.id.setlistNameTextView);
            button = itemView.findViewById(R.id.addToSetlistButton);
            this.listener = listener;
            if (hasSongToAdd) {
                button.setOnClickListener(this::onButtonClicked);
            }
            else {
                button.setVisibility(View.INVISIBLE);
                itemView.findViewById(R.id.setlistCardView).setOnClickListener(this::onButtonClicked);
            }
        }

        public void setSetlist(Setlist setlist, boolean showButton) {
            this.setlist = setlist;
            setlistNameTextView.setText(setlist.setlistName);
            button.setVisibility(showButton ? View.VISIBLE : View.INVISIBLE);
        }

        private void onButtonClicked(View v) {
            listener.onSetlistClicked(setlist);
        }
    }
}
