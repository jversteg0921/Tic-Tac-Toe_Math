package com.verstegenventures.android.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(1000, 1000){
            public void onTick(long millisUntilFinished){
                //do nothing
            }
            public void onFinish(){
                //Use This code eventually once the menu is built
                Intent menuIntent = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(menuIntent);
                finish();

            }
        }.start();


    }

}
