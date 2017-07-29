package edu.calstatela.cpham24.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by mark on 7/4/17.
 */

// copied and adapted this class from To Do assignment for use with news app (changed parameters)
public class Contract {

    public static class TABLE_TODO implements BaseColumns{
        public static final String TABLE_NAME = "newsitems";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_IMGURL = "imgurl";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
