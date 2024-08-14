package nz.ac.ara.dtw0048.lyricscroller.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;


public class EditSongFragment extends Fragment {

    public static final String ARG_SONG = "song";
    private EditText songNameEditText;
    private EditText artistEditText;
    private EditText lyricsEditText;
    private NavController navController;

    private Song song = null;

    public EditSongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            song = getArguments().getParcelable(ARG_SONG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        songNameEditText = view.findViewById(R.id.songNameEditText);
        artistEditText = view.findViewById(R.id.artistNameEditText);
        lyricsEditText = view.findViewById(R.id.lyricsEditText);

        if (song != null) {
            songNameEditText.setText(song.songName);
            artistEditText.setText(song.artistName);
            lyricsEditText.setText(song.lyrics);
        }

        view.findViewById(R.id.cancelEditButton).setOnClickListener(this::onCancelClicked);
        view.findViewById(R.id.saveEditButton).setOnClickListener(this::onSaveClicked);
    }

    private void onCancelClicked(View v) {
        navController.popBackStack();
    }

    private void onSaveClicked(View v) {
        Song newSong = new Song(
                songNameEditText.getText().toString(),
                artistEditText.getText().toString(),
                lyricsEditText.getText().toString()
        );
        Controller controller = Controller.getInstance();

        CompletableObserver observer = new CompletableObserver() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {}
            @Override
            public void onComplete() {
                Bundle args = new Bundle();
                args.putParcelable(LyricFragment.ARG_SONG, newSong);
                navController.navigate(
                        R.id.action_editSongFragment_to_lyricFragment,
                        args,
                        new NavOptions.Builder()
                                .setPopUpTo(R.id.lyricFragment, true)
                                .build()
                );
            }
            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                e.printStackTrace();
            }
        };

        if (song != null) {
            if (newSong.songName.equals(song.songName) && newSong.artistName.equals(song.artistName)) {
                controller.updateSong(newSong).subscribe(observer);
            }
            else {
                controller.renameAndUpdateSong(song.songName, song.artistName, newSong)
                        .subscribe(observer);
            }
        }
    }
}