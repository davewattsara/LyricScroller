package nz.ac.ara.dtw0048.lyricscroller.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import nz.ac.ara.dtw0048.lyricscroller.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewSetlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewSetlistFragment extends Fragment {

    public CreateNewSetlistFragment() {
        // Required empty public constructor
    }

    public static CreateNewSetlistFragment newInstance() {
        return new CreateNewSetlistFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_setlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.createSetlistButton)
                .setOnClickListener((v) -> {
                    Bundle args = new Bundle();
                    EditText setlistNameEdit = view.findViewById(R.id.setlistNameEditText);
                    args.putString(ManageSetlistsFragment.ARG_NEW_SETLIST_NAME, setlistNameEdit.getText().toString());
                    Navigation.findNavController(view).navigate(
                            R.id.action_createNewSetlistFragment_to_manageSetlistsFragment,
                            args,
                            new NavOptions.Builder()
                                    .setPopUpTo(R.id.manageSetlistsFragment, true)
                                    .build()
                    );
                });
    }
}