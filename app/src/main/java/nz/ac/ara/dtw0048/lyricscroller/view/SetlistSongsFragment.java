package nz.ac.ara.dtw0048.lyricscroller.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;
import nz.ac.ara.dtw0048.lyricscroller.model.SongOnClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetlistSongsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetlistSongsFragment extends Fragment implements SongOnClickListener {

    public static final String ARG_SETLIST_NAME = "setlist_name";

    private String setlistName;
    private NavController navController;

    public SetlistSongsFragment() {
        // Required empty public constructor
    }

    public static SetlistSongsFragment newInstance(String setlistName) {
        SetlistSongsFragment fragment = new SetlistSongsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SETLIST_NAME, setlistName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setlistName = getArguments().getString(ARG_SETLIST_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setlist_songs, container, false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        SongOnClickListener listener = this;
        Controller.getInstance().getSetlistSongs(setlistName).subscribe(new SingleObserver<List<Song>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<Song> songs) {
                Context context = getContext();
                RecyclerView rv = view.findViewById(R.id.setlistSongsRecyclerView);
                rv.setLayoutManager(new LinearLayoutManager(context));
                rv.setAdapter(new SongRecyclerViewAdapter(context, listener, songs));
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    @Override
    public void onSongClicked(Song song) {
        Bundle args = new Bundle();
        args.putParcelable(LyricFragment.ARG_SONG, song);
        navController.navigate(R.id.action_setlistSongsFragment_to_lyricFragment, args);
    }
}