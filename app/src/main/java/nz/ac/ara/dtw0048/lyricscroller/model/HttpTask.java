package nz.ac.ara.dtw0048.lyricscroller.model;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpTask extends AsyncTask<String, Void, HttpTask.HttpTaskResult> {

    public static class HttpTaskResult {
        private final JSONObject _jsonObject;
        private final String _type;
        public HttpTaskResult(JSONObject jsonObject, String type) {
            _jsonObject = jsonObject;
            _type = type;
        }

        public JSONObject getJsonObject() {
            return _jsonObject;
        }

        public String getType() {
            return _type;
        }
    }

    public interface HttpTaskListener {
        void onHttpTaskResult(HttpTaskResult result);
    }

    private final HttpTaskListener listener;
    private final String type;

    public HttpTask (HttpTaskListener listener, String type) {
        this.listener = listener;
        this.type = type;
    }
    @Override
    protected HttpTaskResult doInBackground(String... strings) {
        HttpTaskResult result = null;
        try {
            String accessToken = "4yhllesxOOmDRjXoUtRT-YbkGZNywNA06H_CiDX0edm7_b2WBkExLiA3QoiDnxNQ";
            URL url = new URL(strings[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
            urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            urlConnection.disconnect();
            result = new HttpTaskResult(new JSONObject(total.toString()), type);
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute (HttpTaskResult result) {
        listener.onHttpTaskResult(result);
    }
}