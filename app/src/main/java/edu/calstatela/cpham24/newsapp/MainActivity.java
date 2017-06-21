package edu.calstatela.cpham24.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.LinkedHashMap;

import edu.calstatela.cpham24.newsapp.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    private TextView mNewsResultTextView;
    private ProgressBar mProgressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsResultTextView = (TextView) findViewById(R.id.news_result_text);
        mProgressIndicator = (ProgressBar) findViewById(R.id.progress_indicator);
        loadCurrentNewsSource();
    }

    private void loadCurrentNewsSource() {
        // TODO (1) Replace this with the choice from a drop down list
        String newsSource = NetworkUtils.DEFAULT_NEWS_SOURCE;
        mProgressIndicator.setVisibility(ProgressBar.VISIBLE);
        new LoadLatestNewsTask().execute(newsSource);
    }

    public class LoadLatestNewsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            /* If there's no source, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String source = params[0];
            URL newsQueryUrl = NetworkUtils.buildUrl(source);

            try {
                String jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsQueryUrl);

                //String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return jsonNewsResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String newsData) {
            if (newsData != null) {
                mProgressIndicator.setVisibility(ProgressBar.INVISIBLE);
                mNewsResultTextView.setText(newsData);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            loadCurrentNewsSource();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
