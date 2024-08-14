package nz.ac.ara.dtw0048.lyricscroller.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.Setlist;
import nz.ac.ara.dtw0048.lyricscroller.model.SetlistSong;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LyricFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LyricFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String ARG_SONG = "song";

    private static final double DELTA_TIME = 0.01;
    private static final double MIN_SONG_DURATION = 30.0;
    private static final double MAX_SONG_DURATION = 600.0;
    private static final float MIN_TEXT_SIZE = 10.0f;
    private static final float MAX_TEXT_SIZE = 40.0f;

    private Song song;
    private ScrollView scrollView;
    private double scrollPosition = 0.0;
    private double scrollDuration = MIN_SONG_DURATION;
    private Handler scrollHandler;
    private TextView titleTextView;
    private TextView artistTextView;
    private TextView lyricsTextView;
    private boolean isScrolling = false;
    private boolean isUpdatingScroll = false;
    private NavController navController;


    private final Runnable processScroll = new Runnable() {
        @Override
        public void run() {
            int maxScroll = scrollView.getMaxScrollAmount();
            double dy = maxScroll * DELTA_TIME / scrollDuration;
            scrollPosition = Math.min(scrollPosition + dy, maxScroll);
            scrollView.setScrollY((int)Math.round(scrollPosition));
            isUpdatingScroll = true;
            if (isScrolling && scrollPosition < maxScroll) {
                scrollHandler.postDelayed(this, Math.round(1000.0 * DELTA_TIME));
            }
            else {
                isScrolling = false;
            }
        }
    };


    public LyricFragment() {
        // Required empty public constructor
    }

    public static LyricFragment newInstance(Song searchResult) {
        LyricFragment fragment = new LyricFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SONG, searchResult);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_lyric, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        scrollView = view.findViewById(R.id.lyricScrollView);
        scrollView.setOnScrollChangeListener(this::onScrollChanged);

        scrollHandler = new Handler();
        view.findViewById(R.id.playButton).setOnClickListener(this::onPlayClicked);
        view.findViewById(R.id.editButton).setOnClickListener(this::onEditClicked);
        Slider scrollSpeedSlider = view.findViewById(R.id.scrollSpeedSlider);
        setScrollDuration(scrollSpeedSlider.getValue());
        scrollSpeedSlider.addOnChangeListener((slider, value, fromUser) -> setScrollDuration(value));

        Slider fontSizeSlider = view.findViewById(R.id.fontSizeSlider);
        fontSizeSlider.addOnChangeListener((slider, value, fromUser) -> setTextSize(value));

        lyricsTextView = view.findViewById(R.id.lyricTextView);
        titleTextView = view.findViewById(R.id.songTitleTextView);
        artistTextView = view.findViewById(R.id.artistTextView);
        if (song == null)
            lyricsTextView.setText(getString(R.string.no_lyrics));
        else {
            lyricsTextView.setText(song.lyrics);
            titleTextView.setText(song.songName);
            artistTextView.setText(song.artistName);
        }
        setTextSize(fontSizeSlider.getValue());
        AdapterView.OnItemSelectedListener listener = this;

        Controller.getInstance().getSetlistsAndSongs().subscribe(new SingleObserver<Map<Setlist, List<Song>>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Map<Setlist, List<Song>> setlistsAndSongs) {
                List<Setlist> setlistsWithoutSong = new ArrayList<Setlist>(setlistsAndSongs.keySet());
                for (int i = 0; i < setlistsWithoutSong.size(); i++) {
                    List<Song> songs = setlistsAndSongs.get(setlistsWithoutSong.get(i));
                    if (songs != null && songs.contains(song)) {
                        setlistsWithoutSong.remove(i);
                        i--;
                    }
                }
                setlistsWithoutSong.add(0, new Setlist("Add to setlist..."));
                Spinner spinner = view.findViewById(R.id.setlistSpinner);
                spinner.setAdapter(new SetlistSpinnerAdapter(getContext(), setlistsWithoutSong));
                spinner.setOnItemSelectedListener(listener);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }
        });
    }

    private void onScrollChanged(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isUpdatingScroll) {
            isUpdatingScroll = false;
        }
        else {
            isScrolling = false;
        }
    }

    private void setScrollDuration(float sliderValue) {
        scrollDuration = MIN_SONG_DURATION + (MAX_SONG_DURATION - MIN_SONG_DURATION) * (1 - sliderValue);
    }

    private void setTextSize(float sliderValue) {
        float size = MIN_TEXT_SIZE + (MAX_TEXT_SIZE - MIN_TEXT_SIZE) * sliderValue;
        titleTextView.setTextSize(size);
        artistTextView.setTextSize(size);
        lyricsTextView.setTextSize(size);
    }

    private void onPlayClicked(View v) {
        scrollPosition = 0;
        isScrolling = true;
        scrollHandler.postDelayed(processScroll, Math.round(1000.0 * DELTA_TIME));
    }

    private void onEditClicked(View v) {
        Bundle args = new Bundle();
        args.putParcelable(EditSongFragment.ARG_SONG, song);
        navController.navigate(R.id.action_lyricFragment_to_editSongFragment, args);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            Controller controller = Controller.getInstance();
            Setlist setlist = (Setlist) parent.getItemAtPosition(position);
            SetlistSong setlistSong = new SetlistSong(song.songName, song.artistName, setlist.setlistName);
            controller.addSong(song)
                    .andThen(controller.addSetlistSong(setlistSong))
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            String toastText = "Song added to playlist " + setlist.setlistName;
                            Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}