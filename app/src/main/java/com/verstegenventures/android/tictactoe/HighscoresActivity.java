package com.verstegenventures.android.tictactoe;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.sql.SQLException;

/**
 * Created by jfv059 on 8/25/2015.
 */
public class HighscoresActivity extends Activity{
    String initials, score;
    SQLiteDatabase db;
    private ListView scores;
    private HighScoresCursorAdapter mHighScoresCursorAdapter;
    private scoresDbAdapter mScoresDbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        scores = (ListView) findViewById(R.id.highScoresView);
        scores.setDivider(null);

        mScoresDbAdapter = new scoresDbAdapter(this);
        try{
            mScoresDbAdapter.open();
        }catch(SQLException e){
            e.printStackTrace();
        }

        if(savedInstanceState == null){

        }

        Cursor cursor = mScoresDbAdapter.fetchAllHighScores();

        String[] fromInit = new String[]{
                scoresDbAdapter.COL_INITIALS
        };
        int[] toInit = new int[]{
                R.id.initials_text
        };

        String[] fromScores = new String[]{
                scoresDbAdapter.COL_SCORE
        };
        int[] toScores = new int[]{
                R.id.score_text
        };

        mHighScoresCursorAdapter = new HighScoresCursorAdapter(
                HighscoresActivity.this,
                R.layout.scores_row,
                cursor,
                fromInit,
                toInit,
                0);
        HighScoresCursorAdapter mHighScoresCursorAdapterScores = new HighScoresCursorAdapter(
                HighscoresActivity.this,
                R.layout.scores_row,
                cursor,
                fromScores,
                toScores,
                0
        );

        scores.setAdapter(mHighScoresCursorAdapterScores);
        scores.setAdapter(mHighScoresCursorAdapter);



    }



}
