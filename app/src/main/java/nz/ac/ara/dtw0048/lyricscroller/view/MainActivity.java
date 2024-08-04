package nz.ac.ara.dtw0048.lyricscroller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResultListener;
import nz.ac.ara.dtw0048.lyricscroller.model.SearchResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}