package nz.ac.ara.dtw0048.lyricscroller.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResult;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {

    private final SearchResult[] searchResults;
    private final SearchResultsFragment fragment;

    public SearchRecyclerViewAdapter (SearchResultsFragment fragment, SearchResult[] searchResults) {
        this.fragment = fragment;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(fragment.getContext());
        View view = inflater.inflate(R.layout.fragment_search_item, parent, false);
        return new MyViewHolder(view, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.songNameTextView.setText(searchResults[position].getSongName());
        holder.artistNameTextView.setText(searchResults[position].getArtistName());
        holder.searchResult = searchResults[position];
    }

    @Override
    public int getItemCount() {
        return searchResults.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView songNameTextView, artistNameTextView;
        private final SearchResultsFragment fragment;
        private SearchResult searchResult;

        public MyViewHolder(@NonNull View itemView, SearchResultsFragment fragment) {
            super(itemView);

            this.fragment = fragment;
            songNameTextView = itemView.findViewById(R.id.searchResultSongName);
            artistNameTextView = itemView.findViewById(R.id.searchResultArtistName);
            itemView.findViewById(R.id.searchResultCardView).setOnClickListener(this::onClicked);
        }

        private void onClicked(View view) {
            fragment.onResultClicked(searchResult);
        }
    }
}
