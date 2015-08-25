package com.verstegenventures.android.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jfv059 on 8/17/2015.
 */
public class MenuActivity extends Activity{

    Button playBtn;
    Button aboutBtn;
    Button highScores;
    Button exitBtn;
    private String m_initials = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        playBtn = (Button) findViewById(R.id.playButton);
        aboutBtn = (Button) findViewById(R.id.aboutButton);
        exitBtn = (Button) findViewById(R.id.exit);
        highScores = (Button) findViewById(R.id.highScores);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Enter Initials");
                //set up the input
                final EditText input = new EditText(MenuActivity.this);
                //Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                //set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString() != "" | input.getText().toString() != null) {
                            m_initials = input.getText().toString();
                            Intent mainIntent = new Intent(MenuActivity.this, MainActivity.class);
                            mainIntent.putExtra("INIT", m_initials);
                            startActivity(mainIntent);
                            finish();
                        } else
                            (Toast.makeText(MenuActivity.this, "Please enter initials!", Toast.LENGTH_LONG)).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(aboutIntent);

            }
        });

        highScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to High Scores Activity
                //Intent highScoresIntent = new Intent(MenuActivity.this, HighScoresActivity.class);
                //startActivity(highScoresIntent);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
