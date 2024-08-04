package nz.ac.ara.dtw0048.lyricscroller.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment {

    public static final String ARG_SEARCH_RESULTS = "search_results";

    private SearchResult[] searchResults;
    private View view;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    public static SearchResultsFragment newInstance(SearchResult[] searchResults) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(ARG_SEARCH_RESULTS, searchResults);
        fragment.setArguments(args);
        return fragment;
    }

    public void onResultClicked(SearchResult result) {
        Bundle args = new Bundle();
        args.putParcelable("search_result", result);
        //Navigation.findNavController(view).navigate(
        //        R.id.action_searchResultsFragment_to_lyricFragment, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchResults = (SearchResult[])getArguments().getParcelableArray(ARG_SEARCH_RESULTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        SearchRecyclerViewAdapter adapter = new SearchRecyclerViewAdapter(this, searchResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        this.view = view;

        return view;
    }
}