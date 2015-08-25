package com.verstegenventures.android.tictactoe;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;

/**
 * Created by jfv059 on 8/25/2015.
 */
public class HighscoresActivity extends Activity{
    ListView mListView;
    CursorAdapter mCursorAdapter;
    String initials, score;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        db = new DatabaseHelper(this).getReadableDatabase();


    }



}
