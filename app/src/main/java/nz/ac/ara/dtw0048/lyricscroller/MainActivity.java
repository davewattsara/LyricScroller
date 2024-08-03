package nz.ac.ara.dtw0048.lyricscroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the ui callbacks
        findViewById(R.id.getContentButton).setOnClickListener(v -> {
            getContentClicked();
        });
    }

    private void getContentClicked() {
        try {
            //URL url = new URL("https://www.google.com/search?q=light+my+fire+lyrics");
            URL url = new URL("http://android.com");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            urlConnection.disconnect();
            ((TextView)findViewById(R.id.text)).setText(total);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}