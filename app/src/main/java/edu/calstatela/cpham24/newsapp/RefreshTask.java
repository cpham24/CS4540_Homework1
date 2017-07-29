/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.calstatela.cpham24.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import edu.calstatela.cpham24.newsapp.data.DBHelper;
import edu.calstatela.cpham24.newsapp.data.DatabaseUtils;
import edu.calstatela.cpham24.newsapp.data.NewsItem;
import edu.calstatela.cpham24.newsapp.utilities.NetworkUtils;
import edu.calstatela.cpham24.newsapp.utilities.NewsJsonUtils;

// copied from Mark's NYTimesMostPopular example and adapted to fit my news app
public class RefreshTask {
    public static final String TAG = "RefreshTask";


    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildUrl(NetworkUtils.DEFAULT_NEWS_SOURCE);

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NewsJsonUtils.getNewsStringsFromJson(context, json);
            DatabaseUtils.bulkInsert(db, result);
            // added this log statement to show that this task has finished running
            // and the database has been updated with new data from network
            Log.d(TAG, "loaded from network and updated db");
        } catch (Exception e) { // catch all exceptions
            e.printStackTrace();
        }

        db.close();
    }
}