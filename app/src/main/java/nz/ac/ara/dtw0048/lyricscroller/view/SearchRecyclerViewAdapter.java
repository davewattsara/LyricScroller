package nz.ac.ara.dtw0048.lyricscroller.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResult;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {

    private final Context context;
    private final SearchResult[] searchResults;

    public SearchRecyclerViewAdapter (Context context, SearchResult[] searchResults) {
        this.context = context;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_search_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.songNameTextView.setText(searchResults[position].getSongName());
        holder.artistNameTextView.setText(searchResults[position].getArtistName());
    }

    @Override
    public int getItemCount() {
        return searchResults.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView songNameTextView, artistNameTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            songNameTextView = itemView.findViewById(R.id.searchResultSongName);
            artistNameTextView = itemView.findViewById(R.id.searchResultArtistName);
        }
    }
}
