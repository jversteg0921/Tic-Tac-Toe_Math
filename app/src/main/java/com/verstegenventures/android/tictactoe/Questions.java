package com.verstegenventures.android.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jfv059 on 8/17/2015.
 */
public class Questions extends Activity {

    TextView probText;
    TextView countDown;
    Button submitBtn;
    EditText ansText;
    private int ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.prompt);




        probText = (TextView) findViewById(R.id.problemText);
        countDown = (TextView) findViewById(R.id.countdownTimer);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        ansText = (EditText) findViewById(R.id.answerText);
        ansText.setBackgroundColor(getResources().getColor(R.color.questionBlue));
        ansText.setTextColor(Color.BLACK);

        new CountDownTimer(11000, 1000){
            public void onTick(long millisUntilFinished){
                countDown.setText("" + millisUntilFinished / 1000);

                if(millisUntilFinished < 4000){
                    countDown.setBackgroundColor(Color.YELLOW);
                }
            }

            public void onFinish(){
                countDown.setBackgroundColor(Color.RED);
                ansText.setTextColor(getResources().getColor(R.color.questionBlue));
                ansText.setText("1000000");
                submitBtn.performClick();
            }
        }.start();

        ansText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);





        Intent intent = getIntent();
        String problemText = intent.getStringExtra("STRING_PROB");
        probText.setText(problemText);
        final int answer = intent.getIntExtra("STRING_ANS", -1);
        ans = answer;







        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int testAns = -1;
                try{
                    testAns = Integer.parseInt(ansText.getText().toString());
                }catch(Exception e) {

                    e.printStackTrace();
                }
                if(testAns == -1){
                    Toast.makeText(Questions.this, "You must answer the question", Toast.LENGTH_LONG).show();
                }else {
                    if(ansText.getText().toString() == "1000000"){
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("data", false);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                    else {
                        if (Integer.parseInt(ansText.getText().toString()) == ans) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("data", true);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("data", false);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        }
                    }
                }
            }
        });



    }
}
