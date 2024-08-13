package nz.ac.ara.dtw0048.lyricscroller.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.Setlist;
import nz.ac.ara.dtw0048.lyricscroller.model.SetlistOnClickListener;
import nz.ac.ara.dtw0048.lyricscroller.model.SetlistSong;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements SetlistOnClickListener {

    private NavController navController;
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        view.findViewById(R.id.searchButton).setOnClickListener((v) -> {
            navController.navigate(R.id.action_mainFragment_to_searchFragment);
        });

        view.findViewById(R.id.manageSetlistsButton).setOnClickListener((v) -> {
            navController.navigate(R.id.action_mainFragment_to_manageSetlistsFragment);
        });

        Controller controller = Controller.getInstance();
        SetlistOnClickListener listener = this;
        controller.getSetlistsAndSongs().subscribe(new SingleObserver<Map<Setlist, List<Song>>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Map<Setlist, List<Song>> setlistListMap) {
                RecyclerView recyclerView = view.findViewById(R.id.mainRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new SetlistRecyclerViewAdapter(
                        getContext(),
                        listener,
                        setlistListMap,
                        null
                ));
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }
        });
    }

    @Override
    public void onSetlistClicked(Setlist setlist) {
        Bundle args = new Bundle();
        args.putString(SetlistSongsFragment.ARG_SETLIST_NAME, setlist.setlistName);
        navController.navigate(R.id.action_mainFragment_to_setlistSongsFragment, args);
    }
}