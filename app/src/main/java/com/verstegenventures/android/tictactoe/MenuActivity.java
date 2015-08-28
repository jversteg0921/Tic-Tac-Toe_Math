package com.verstegenventures.android.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        playBtn = (Button) findViewById(R.id.playButton);
        aboutBtn = (Button) findViewById(R.id.aboutButton);
        exitBtn = (Button) findViewById(R.id.exit);
        highScores = (Button) findViewById(R.id.highScores);





        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Enter Initials:");
                //set up the input
                final EditText input = new EditText(MenuActivity.this);
                //Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

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

                AlertDialog dialog = builder.create();
                dialog.setIcon(android.R.drawable.ic_menu_edit);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();

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
                Intent highScoresIntent = new Intent(MenuActivity.this, HighscoresActivity.class);
                startActivity(highScoresIntent);
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
