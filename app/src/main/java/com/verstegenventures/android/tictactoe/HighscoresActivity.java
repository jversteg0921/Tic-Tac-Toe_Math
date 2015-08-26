package com.verstegenventures.android.tictactoe;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.sql.SQLException;

/**
 * Created by jfv059 on 8/25/2015.
 */
public class HighscoresActivity extends Activity{
    private ListView scores;
    private HighScoresCursorAdapter mHighScoresCursorAdapter;
    private ScoresDbAdapter mScoresDbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        scores = (ListView) findViewById(R.id.highScoresView);
        scores.setDivider(null);

        mScoresDbAdapter = new ScoresDbAdapter(this);
        try{
            mScoresDbAdapter.open();
        }catch(SQLException e){
            e.printStackTrace();
        }

        Cursor cursor = mScoresDbAdapter.fetchAllHighScores();
        String[] from = new String[]{
                ScoresDbAdapter.COL_INITIALS
        };
        int[] to = new int[]{
                R.id.initials_text
        };

        mHighScoresCursorAdapter = new HighScoresCursorAdapter(
                HighscoresActivity.this,
                R.layout.scores_row,
                cursor,
                from,
                to,
                0
        );


        scores.setAdapter(mHighScoresCursorAdapter);




    }



}
