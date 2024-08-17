package nz.ac.ara.dtw0048.lyricscroller.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import nz.ac.ara.dtw0048.lyricscroller.R;

public class RenameDialogFragment extends DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_OLD_NAME = "old_name";
    private static final String ARG_REQUEST_KEY = "request_key";
    public static final String ARG_NEW_NAME = "new_name";
    private String requestKey = null;

    public static RenameDialogFragment newInstance(String title, String oldName, String requestKey) {
        RenameDialogFragment result = new RenameDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_OLD_NAME, oldName);
        args.putString(ARG_REQUEST_KEY, requestKey);
        result.setArguments(args);
        return result;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.dialog_fragment_rename, null);

        if (args != null) {
            String title = args.getString(ARG_TITLE);
            String oldName = args.getString(ARG_OLD_NAME);
            requestKey = args.getString(ARG_REQUEST_KEY);
            if (title != null) {
                TextView titleTextView = view.findViewById(R.id.renameTextView);
                titleTextView.setText(title);
            }
            if (oldName != null) {
                EditText editText = view.findViewById(R.id.renameEditText);
                editText.setText(oldName);
            }
        }

        builder.setView(view)
                .setPositiveButton("OK", (dialog, which) -> {
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        if (requestKey != null) {
                            EditText editText = view.findViewById(R.id.renameEditText);
                            Bundle resultArgs = new Bundle();
                            resultArgs.putString(ARG_NEW_NAME, editText.getText().toString());
                            activity.getSupportFragmentManager()
                                    .setFragmentResult(requestKey, resultArgs);
                        }
                    }
                }).setNegativeButton("Cancel", (dialog, which) -> {

                });
        return builder.create();
    }

}
