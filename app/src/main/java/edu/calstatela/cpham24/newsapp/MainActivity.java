package edu.calstatela.cpham24.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import edu.calstatela.cpham24.newsapp.utilities.NetworkUtils;
import edu.calstatela.cpham24.newsapp.utilities.NewsJsonUtils;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    //private TextView mNewsResultTextView;
    private ProgressBar mProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressIndicator = (ProgressBar) findViewById(R.id.progress_indicator);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_news);
        //mNewsResultTextView = (TextView) findViewById(R.id.news_result_text);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mNewsAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mNewsAdapter);

        loadCurrentNewsSource();
    }

    /**
     * Method that automatically loads the default news source (as defined in NetworkUtils)
     *
     * @param none
     * @return none
     */
    private void loadCurrentNewsSource() {
        // TODO (1) Replace this with the choice from a drop down list
        String newsSource = NetworkUtils.DEFAULT_NEWS_SOURCE;
        mProgressIndicator.setVisibility(ProgressBar.VISIBLE);
        new LoadLatestNewsTask().execute(newsSource);
    }

    /**
     * Subclass that extends AsyncTask to query network in the background
     *
     */
    public class LoadLatestNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {
        @Override
        protected ArrayList<NewsItem> doInBackground(String... params) {

            /* If there's no source, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String source = params[0];
            URL newsQueryUrl = NetworkUtils.buildUrl(source);

            try {
                String jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsQueryUrl);
                ArrayList<NewsItem> parsedJsonNewsData = NewsJsonUtils.getNewsStringsFromJson(MainActivity.this, jsonNewsResponse);

                return parsedJsonNewsData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> newsData) {
            if (newsData != null) {
                mProgressIndicator.setVisibility(ProgressBar.INVISIBLE);
                mNewsAdapter.setNewsData(newsData);
                //mNewsResultTextView.setText(newsData);
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
