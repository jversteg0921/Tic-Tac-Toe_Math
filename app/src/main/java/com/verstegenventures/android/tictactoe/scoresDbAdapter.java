package com.verstegenventures.android.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by jfv059 on 8/25/2015.
 */
public class scoresDbAdapter {
    //column names
    public static final String COL_ID = "_id";
    public static final String COL_INITIALS = "playerInitials";
    public static final String COL_SCORE = "score";

    //corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_INITIALS = INDEX_ID + 1;
    public static final int INDEX_SCORE = INDEX_ID + 2;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public static final String DATABASE_NAME = "highscores";
    private static final String TABLE_NAME = "scoretable";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    //SQL statement used to create the database
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_INITIALS + " TEXT, " +
            COL_SCORE + " INTEGER);";

    public scoresDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //CREATE
    //id will be created automatically
    public void createHighScore(String initials, int score) {
        ContentValues values = new ContentValues();
        values.put(COL_INITIALS, initials);
        values.put(COL_SCORE, score);
        mDb.insert(TABLE_NAME, null, values);
    }

    //Overloaded to take a highscore
    public long createHighScore(HighScore highScore) {
        ContentValues values = new ContentValues();
        values.put(COL_INITIALS, highScore.getInitials());
        values.put(COL_SCORE, highScore.getScore());

        //Inserting row
        return mDb.insert(TABLE_NAME, null, values);
    }

    //Read Highscores
    public HighScore fetchHighScoreById(int id) {
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{
                        COL_INITIALS, COL_SCORE}, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new HighScore(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_INITIALS),
                cursor.getInt(INDEX_SCORE));
    }

    public Cursor fetchAllHighScores() {

        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{
                        COL_ID, COL_INITIALS, COL_SCORE},
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Delete
    public void deleteHighScoreById(int nId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }

    //Delete all
    public void deleteAllHighScores() {
        mDb.delete(TABLE_NAME, null, null);
    }


    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //not used
        }

    }
}
