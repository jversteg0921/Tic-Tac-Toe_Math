package com.verstegenventures.android.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by jfv059 on 8/18/2015.
 */
public class AboutActivity extends Activity {

    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);

        backBtn = (Button) findViewById(R.id.backToMenu);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AboutActivity.this, MenuActivity.class);
//                startActivity(intent);
                finish();
            }
        });

    }
}
