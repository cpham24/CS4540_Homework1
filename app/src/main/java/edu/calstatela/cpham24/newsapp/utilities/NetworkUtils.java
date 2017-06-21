package edu.calstatela.cpham24.newsapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by bill on 6/19/17.
 */

public final class NetworkUtils {
    // tag for logging
    private static final String TAG = NetworkUtils.class.getSimpleName();

    // api key retrieved from news API website
    private static final String API_KEY = "ec6ec1b05e654ed6bbe7b211447fa233";

    // the url to the news api
    private static final String NEWS_API_URL = "https://newsapi.org/v1/articles";

    // the default news source from the assignment
    public static final String DEFAULT_NEWS_SOURCE = "the-next-web";

    // the method by which to sort query results
    private static final String DEFAULT_NEWS_SORT_METHOD = "latest";

    // parameter names for news api
    final static String PARAM_SOURCE = "source";
    final static String PARAM_SORT = "sortBy";
    final static String PARAM_API_KEY = "apiKey";

    /**
     * Builds the URL used to query the results from News API server
     *
     * @param newsSource The source of news we want to query.
     * @return The URL to use to query the news api server
     */
    public static URL buildUrl(String newsSource) {
        Uri builtUri = Uri.parse(NEWS_API_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, newsSource)
                .appendQueryParameter(PARAM_SORT, DEFAULT_NEWS_SORT_METHOD)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
