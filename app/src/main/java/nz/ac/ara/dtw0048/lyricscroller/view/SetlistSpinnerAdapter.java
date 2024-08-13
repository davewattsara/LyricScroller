package nz.ac.ara.dtw0048.lyricscroller.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.model.Setlist;

public class SetlistSpinnerAdapter extends ArrayAdapter<Setlist> {

    public SetlistSpinnerAdapter(Context context, List<Setlist> setlists) {
        super(context, 0, setlists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @NonNull
    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.dropdown_item, parent, false);
        }
        TextView tv = convertView.findViewById(R.id.dropdownTextView);
        Setlist setlist = getItem(position);
        if (setlist != null) {
            tv.setText(setlist.setlistName);
        }
        return convertView;
    }
}
