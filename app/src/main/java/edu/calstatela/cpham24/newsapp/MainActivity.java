package edu.calstatela.cpham24.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import edu.calstatela.cpham24.newsapp.data.Contract;
import edu.calstatela.cpham24.newsapp.data.DBHelper;
import edu.calstatela.cpham24.newsapp.data.DatabaseUtils;
import edu.calstatela.cpham24.newsapp.data.NewsItem;
import edu.calstatela.cpham24.newsapp.utilities.NetworkUtils;
import edu.calstatela.cpham24.newsapp.utilities.NewsJsonUtils;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ProgressBar mProgressIndicator;
    private static final int NEWSLOADER = 1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressIndicator = (ProgressBar) findViewById(R.id.progress_indicator);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        // saving the current context to get easier references
        context = this;

        // copied this from Mark's NYTimesMostPopular example to handle first time opening the app
        // plus modified it a bit to match my own app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        if (isFirst) {
            // if it's the first run (meaning there is nothing in the database)
            // then load from the network
            loadCurrentNewsSource();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(context);
    }

    // Method that automatically loads the default news source (as defined in NetworkUtils)
    private void loadCurrentNewsSource() {
        // creating callback inline because onCreateLoader was called along with onStart when
        // MainActivity comes back online, and that's not the right behavior
        LoaderManager lm = getSupportLoaderManager();
        lm.restartLoader(NEWSLOADER, null, new LoaderManager.LoaderCallbacks<Void>() {
            // overriding onCreateLoader to start the AsyncTaskLoader
            @Override
            public Loader<Void> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<Void>(context) {
                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        mProgressIndicator.setVisibility(View.VISIBLE);
                        Log.d(TAG, "starting async task to load articles in background");
                    }

                    @Override
                    public Void loadInBackground() {
                        RefreshTask.refreshArticles(context);
                        return null;
                    }
                };
            }

            // overriding onLoadFinished to load from db into view
            @Override
            public void onLoadFinished(Loader<Void> loader, Void data) {
                db = new DBHelper(MainActivity.this).getReadableDatabase();
                cursor = DatabaseUtils.getAll(db);
                updateView(cursor);
                mProgressIndicator.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onLoaderReset(Loader<Void> loader) {
                // doesn't really do anything but is required for the interface
            }
        }).forceLoad();
    }

    // Method that updates the recycler view whenever data is fetched from the database or over network
    private void updateView(Cursor cursor) {
        mNewsAdapter = new NewsAdapter(cursor,
                new NewsAdapter.ItemClickListener(){
                    @Override
                    public void onItemClick(Cursor cursor, int clickedItemIndex) {
                        cursor.moveToPosition(clickedItemIndex);
                        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_URL));
                        Log.d(TAG, String.format("opening Url %s", url));

                        // I took the below code from StackOverflow
                        Intent viewURLIntent = new Intent(Intent.ACTION_VIEW);
                        viewURLIntent.setData(Uri.parse(url));
                        startActivity(viewURLIntent);
                    }
                });
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }

    // overriding onStart to load from db every time the app starts/resumes
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "revived, restoring connections");
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        updateView(cursor);
    }

    // overriding onStop to "clean up" every time the app is shut down
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "died, severing connections");
        db.close();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_refresh) {
            loadCurrentNewsSource();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
