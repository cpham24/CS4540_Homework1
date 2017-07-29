CS4540 Homework Assignment #4

Completed:
1. Finished Homework 2.
2. Replaced AsyncTask with AsyncTaskLoader in MainActivity
3. Created a contract, subclassed SQLiteOpenHelper, and storing data in a SQLite db
4. RecyclerView's adapter now modified to load from database
5. Implemented Firebase's JobDispatcher to schedule refreshing news every 60 seconds
6. MainActivity now checks using Preferences if it's the 1st launch to load from network into db first
7. Overrided onStart and now app starts by loading what's in db into RecyclerView
8. Implemented Picasso to load thumbnails for each news item