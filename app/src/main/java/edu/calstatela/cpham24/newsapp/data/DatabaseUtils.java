package edu.calstatela.cpham24.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static edu.calstatela.cpham24.newsapp.data.Contract.TABLE_TODO.*;

/**
 * Created by mark on 7/18/17.
 */

// copied and adapted this class from Mark's NYTimesMostPopular example to work with my news app
public class DatabaseUtils {

    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_DATE + " DESC"
        );
        return cursor;
    }

    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> items) {

        db.beginTransaction();
        try {
            for (NewsItem i : items) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, i.title);
                cv.put(COLUMN_NAME_AUTHOR, i.author);
                cv.put(COLUMN_NAME_DESCRIPTION, i.description);
                cv.put(COLUMN_NAME_URL, i.url);
                cv.put(COLUMN_NAME_IMGURL, i.imgurl);
                cv.put(COLUMN_NAME_DATE, i.date);
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }

}
