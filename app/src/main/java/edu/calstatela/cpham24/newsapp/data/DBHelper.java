package edu.calstatela.cpham24.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mark on 7/4/17.
 */

// adapted this class to work with the news app (changed initialization method to create a specialized DB)
public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // changed the structure of the query in order to include the checkbox state
        String queryString = "CREATE TABLE " + Contract.TABLE_TODO.TABLE_NAME + " ("+
                Contract.TABLE_TODO._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_TODO.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_AUTHOR + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_URL + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_IMGURL + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_DATE + " DATE); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    // uninstalling the app before running drops the table anyway so this is somewhat redundant for testing
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 //       db.execSQL("DROP TABLE " + Contract.TABLE_TODO.TABLE_NAME + " IF EXISTS;");
    }
}
