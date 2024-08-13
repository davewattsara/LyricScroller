package nz.ac.ara.dtw0048.lyricscroller.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        String newSongName = songNameEditText.getText().toString();
        String newArtistName = artistEditText.getText().toString();

    }
}