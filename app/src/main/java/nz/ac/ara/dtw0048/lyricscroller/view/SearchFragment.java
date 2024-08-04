package nz.ac.ara.dtw0048.lyricscroller.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResult;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResultListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchResultListener {

    private Controller controller;
    private View view;
    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = Controller.getInstance();
        controller.setSearchResultListener(this);
        this.view = view;

        view.findViewById(R.id.searchButton2).setOnClickListener((v) -> {
            EditText queryEdit = view.findViewById(R.id.searchQueryEditText);
            controller.searchForLyrics(queryEdit.getText().toString());
        });
    }

    @Override
    public void onSearchResultsFound(SearchResult result) {
        Bundle args = new Bundle();
        args.putParcelable(LyricFragment.ARG_SEARCH_RESULT, result);
        Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_lyricFragment, args);
    }
}