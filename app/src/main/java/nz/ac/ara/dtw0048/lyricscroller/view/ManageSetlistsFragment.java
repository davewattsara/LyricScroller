package nz.ac.ara.dtw0048.lyricscroller.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.Setlist;
import nz.ac.ara.dtw0048.lyricscroller.model.SetlistOnClickListener;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageSetlistsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageSetlistsFragment extends Fragment implements SetlistOnClickListener {

    public static final String ARG_NEW_SETLIST_NAME = "new_setlist_name";

    private String newSetlistName;

    public ManageSetlistsFragment() {
        // Required empty public constructor
    }
    public static ManageSetlistsFragment newInstance(String newSetlistName) {
        ManageSetlistsFragment fragment = new ManageSetlistsFragment();
        if (newSetlistName != null) {
            Bundle args = new Bundle();
            args.putString(ARG_NEW_SETLIST_NAME, newSetlistName);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newSetlistName = getArguments().getString(ARG_NEW_SETLIST_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_setlists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Controller controller = Controller.getInstance();
        SetlistOnClickListener listener = this;
        SingleObserver<Map<Setlist, List<Song>>> observer = new SingleObserver<Map<Setlist, List<Song>>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Map<Setlist, List<Song>> setlistsAndSongs) {
                RecyclerView recyclerView = view.findViewById(R.id.manageSetlistRecyclerView);
                Context context = getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new SetlistRecyclerViewAdapter(context, listener, setlistsAndSongs, null));
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }
        };

        if (newSetlistName != null) {
            Log.i("ManageSetlistsFragment", "Adding new setlist: " + newSetlistName);
            controller.addSetlist(newSetlistName)
                    .andThen(controller.getSetlistsAndSongs())
                    .subscribe(observer);
        }
        else {
            Log.i("ManageSetlistsFragment", "Starting without adding setlist");
            controller.getSetlistsAndSongs().subscribe(observer);
        }

        view.findViewById(R.id.createSetlistButton).setOnClickListener((v) -> {
            Navigation.findNavController(view).navigate(R.id.action_manageSetlistsFragment_to_createNewSetlistFragment);
        });
    }

    @Override
    public void onSetlistClicked(Setlist setlist) {

    }
}