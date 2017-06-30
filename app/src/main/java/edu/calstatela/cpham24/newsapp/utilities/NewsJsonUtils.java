package edu.calstatela.cpham24.newsapp.utilities;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by bill on 6/29/17.
 */

// mirrored from Sunshine example and adapted to NewsAPI's response
public final class NewsJsonUtils {
    public static String[] getNewsStringsFromJson(Context context, String newsJsonStr)
            throws JSONException {

        /* News information. Each source's news articles are contained in the "articles" array */
        final String NEWSAPI_ARTICLES = "articles";

        /* Parameters of each news article item */
        final String NEWSAPI_AUTHOR = "author";
        final String NEWSAPI_TITLE = "title";
        final String NEWSAPI_DESC = "description";
        final String NEWSAPI_URL = "url";
        final String NEWSAPI_IMGURL = "urlToImage";
        final String NEWSAPI_DATE = "publishedAt";

        // if this is not "ok" then an error occurred
        final String NEWSAPI_STATUS = "status";

        /* String array to hold each day's weather String */
        String[] parsedNewsData = null;

        JSONObject newsJson = new JSONObject(newsJsonStr);

        /* Check status message */
        if (newsJson.has(NEWSAPI_STATUS)) {
            String status = newsJson.getString(NEWSAPI_STATUS);
            if (!status.contentEquals("ok"))
                return null;
        }

        JSONArray articleArray = newsJson.getJSONArray(NEWSAPI_ARTICLES);

        parsedNewsData = new String[articleArray.length()];

        for (int i = 0; i < articleArray.length(); i++) {
            JSONObject article = articleArray.getJSONObject(i);

            String author = article.getString(NEWSAPI_AUTHOR);
            String title = article.getString(NEWSAPI_TITLE);
            String date = article.getString(NEWSAPI_DATE);
            String url = article.getString(NEWSAPI_URL);

            parsedNewsData[i] = title + "\n\n" + author + "\n\n" + date;
        }

        return parsedNewsData;
    }
}
