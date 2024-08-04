package nz.ac.ara.dtw0048.lyricscroller.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LyricFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LyricFragment extends Fragment {

    public static final String ARG_SEARCH_RESULT = "search_result";

    private SearchResult searchResult;
    private final Controller controller = Controller.getInstance();

    public LyricFragment() {
        // Required empty public constructor
    }

    public static LyricFragment newInstance(SearchResult searchResult) {
        LyricFragment fragment = new LyricFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SEARCH_RESULT, searchResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchResult = getArguments().getParcelable(ARG_SEARCH_RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lyric, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.lyricTextView);
        if (searchResult == null)
            tv.setText("No lyrics were found matching your search.");
        else
            tv.setText(searchResult.getLyrics());
    }
}