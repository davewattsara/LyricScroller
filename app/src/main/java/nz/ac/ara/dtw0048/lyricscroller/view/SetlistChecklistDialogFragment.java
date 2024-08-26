package nz.ac.ara.dtw0048.lyricscroller.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nz.ac.ara.dtw0048.lyricscroller.model.Setlist;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;

public class SetlistChecklistDialogFragment extends DialogFragment {

    private static final String ARG_ALL_SETLISTS = "all_setlists";
    private static final String ARG_CHECKED_SETLISTS = "checked_setlists";
    public static final String REQUEST_KEY_OK = "SetlistChecklistDialogFragment_request_ok";
    public static final String REQUEST_KEY_MANAGE_SETLISTS = "SetlistChecklistDialogFragment_request_manage_setlists";
    private Setlist[] allSetlists;
    private boolean[] checkedSetlists;

    public static SetlistChecklistDialogFragment newInstance(Map<Setlist, List<Song>> setlistsAndSongs, Song song) {
        SetlistChecklistDialogFragment result = new SetlistChecklistDialogFragment();
        Bundle args = new Bundle();
        Setlist[] allSetlists = new Setlist[setlistsAndSongs.size()];
        allSetlists = setlistsAndSongs.keySet().toArray(allSetlists);
        args.putParcelableArray(ARG_ALL_SETLISTS, allSetlists);
        boolean[] checkedSetlists = new boolean[allSetlists.length];
        for (int i = 0; i < allSetlists.length; i++) {
            List<Song> songsInSetlist = setlistsAndSongs.get(allSetlists[i]);
            checkedSetlists[i] = songsInSetlist != null && songsInSetlist.contains(song);
        }
        args.putBooleanArray(ARG_CHECKED_SETLISTS, checkedSetlists);
        result.setArguments(args);
        return result;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            allSetlists = (Setlist[])args.getParcelableArray(ARG_ALL_SETLISTS);
            checkedSetlists = args.getBooleanArray(ARG_CHECKED_SETLISTS);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add to setlists...")
                .setMultiChoiceItems(allSetlists, checkedSetlists, (dialog, index, isChecked) -> {
                    checkedSetlists[index] = isChecked;
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        ArrayList<Setlist> result = new ArrayList<>();
                        for (int i = 0; i < allSetlists.length; i++) {
                            if (checkedSetlists[i]) {
                                result.add(allSetlists[i]);
                            }
                        }
                        //listener.onSetlistChecklistOkClicked(result);
                        Bundle resultArgs = new Bundle();
                        resultArgs.putParcelableArrayList(LyricFragment.ARG_CHECKED_SETLISTS, result);
                        activity.getSupportFragmentManager()
                                .setFragmentResult(REQUEST_KEY_OK, resultArgs);
                    }
                })
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Manage Setlists", ((dialog, which) -> {
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        activity.getSupportFragmentManager()
                                .setFragmentResult(REQUEST_KEY_MANAGE_SETLISTS, new Bundle());
                    }
                }));
        return builder.create();
    }
}
