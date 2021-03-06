package com.verstegenventures.android.tictactoe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Declare the global variables for the game space
    private GameBoard board = null;
    private int moveCount = 0, xloc = 0, yloc = 0;
    private String mark = "X", aiMark = "O";
    private boolean isOver = false;
    private AI ai = null;
    private Random rnd;
    int x;
    int y;
    TextView resultText;
    TextView questionText;
    TextView initialsDisplay;
    TextView scoreDisplay;
    boolean correct = false;
    private int ans;
    private View v;
    private int questionsRight = 0;
    private int score = 0;
    private scoresDbAdapter mScoresDbAdapter;
    private String initials;
    final ArrayList selectedItems = new ArrayList();
    final CharSequence[] items = {"+", "-", "*", "/"};


    //Create the initial game space
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Look at the right XML layout and remove the action bar
        setContentView(R.layout.activity_main);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            initials = b.getString("INIT");
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the Functions:");
        builder.setIcon(android.R.drawable.checkbox_on_background);
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            //If user checked the item, add it to selected list
                            selectedItems.add(items[indexSelected]);
                        } else if (selectedItems.contains(items[indexSelected])) {
                            selectedItems.remove(items[indexSelected]);
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedItems.isEmpty()) {
                            Toast.makeText(MainActivity.this, "You must select at least one function", Toast.LENGTH_LONG).show();
                            builder.show();


                        }



                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.show();

        initialsDisplay = (TextView) findViewById(R.id.initials_display);
        initialsDisplay.setText(initials);

        scoreDisplay = (TextView) findViewById(R.id.score_display);
        scoreDisplay.setText(score + "");


        //Set up a new board and AI and assign the initial variables
        board = new GameBoard();
        ai = new AI(aiMark);
        rnd = new Random();
        resultText = (TextView) findViewById(R.id.answerText);
        questionText = (TextView) findViewById(R.id.problemText);

        mScoresDbAdapter = new scoresDbAdapter(this);
        try{
            mScoresDbAdapter.open();
        }catch(SQLException e ){
            e.printStackTrace();
        }



    }

    public boolean checkAnswer(int answer){
        if(answer == ans){
            return true;
        }
        else
            return false;

    }

    //Action when reset is clicked which clears the screen and the virtual game board
    public void resetClick(View v){
        clear();
        score=0;
        scoreDisplay.setText(score + "");
        if(aiMark.equals("X")) getAIMove(board);
    }

    //Action for when a cell is clicked. Determines which cell has been clicked and
    //passes that information on to the virtual game board.
    public void cellClick(View v){
        this.v = v;
        //Get the id of the clicked object and assign it to a TextView variable
        TextView cell = (TextView) findViewById(v.getId());
        //Check the content and make sure the cell is empty and that the game isn't over
        String content = (String) cell.getText();
        if(content.equals("") && !isOver) {

            int function = rnd.nextInt(selectedItems.size());
            if(selectedItems.get(function) == "+"){
                x = rnd.nextInt(49) + 1;
                y = rnd.nextInt(49) + 1;
            }
            else if(selectedItems.get(function) == "-"){
                x = rnd.nextInt(49) + 1;
                y = rnd.nextInt(49) + 1;
                while(x < y){
                    y = rnd.nextInt(49) + 1;
                }
            }
            else if(selectedItems.get(function) == "*"){
                x = rnd.nextInt(11) + 1;
                y = rnd.nextInt(11) + 1;
                while(y == 1){
                    y = rnd.nextInt(9) + 1;
                }
            }
            else{
                x = rnd.nextInt(49) + 1;
                y = rnd.nextInt(11) + 1;
                while(y == 1){
                    y = rnd.nextInt(11) + 1;
                }
            }

            if(selectedItems.get(function) == "/") {
                while ((x % y) != 0) {
                    y = rnd.nextInt(11) + 1;
                }
            }

            if (selectedItems.get(function) == "+") {
                ans = x + y;
            } else if (selectedItems.get(function) == "-") {
                ans = x - y;
            } else if (selectedItems.get(function) == "*") {
                ans = x * y;
            } else {
                ans = x / y;
            }

            Intent intent = new Intent(this,Questions.class);
            String strProb = x + " " + selectedItems.get(function) + " " + y;
            intent.putExtra("STRING_PROB", strProb);
            intent.putExtra("STRING_ANS",ans);

           startActivityForResult(intent, 1);


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            if((Boolean) data.getExtras().get("data")) {
                //Get the id of the clicked object and assign it to a TextView variable
                TextView cell = (TextView) findViewById(v.getId());
                //Check the content and make sure the cell is empty and that the game isn't over
                String content = (String) cell.getText();

                //Find the X Y location values of the particular cell that was clicked
                switch (cell.getId()) {

                    case R.id.cell11:
                        xloc = 0;
                        yloc = 0;
                        break;
                    case R.id.cell12:
                        xloc = 0;
                        yloc = 1;
                        break;
                    case R.id.cell13:
                        xloc = 0;
                        yloc = 2;
                        break;
                    case R.id.cell21:
                        xloc = 1;
                        yloc = 0;
                        break;
                    case R.id.cell22:
                        xloc = 1;
                        yloc = 1;
                        break;
                    case R.id.cell23:
                        xloc = 1;
                        yloc = 2;
                        break;
                    case R.id.cell31:
                        xloc = 2;
                        yloc = 0;
                        break;
                    case R.id.cell32:
                        xloc = 2;
                        yloc = 1;
                        break;
                    case R.id.cell33:
                        xloc = 2;
                        yloc = 2;
                        break;
                }

                //Place the player's mark on the specific X Y location on both the virtual
                //and displayed boards
                board.placeMark(xloc, yloc, mark);
                cell.setText(mark);

                //Increment move Count because a move was just made
                moveCount++;
                score += 10;
                scoreDisplay.setText(score + "");

                //Check to see if the game is over
                isOver = checkEnd(mark);

                //if the game isn't over, get the AI's move
                if (!isOver) {
                    questionsRight++;
                    if (questionsRight == 3) {
                        Toast.makeText(this, "3 questions in a row, have another go!", Toast.LENGTH_LONG).show();
                        score += 20;
                        scoreDisplay.setText(score + "");
                        questionsRight = 0;
                    } else if (questionsRight > 3) {
                        questionsRight = 0;
                    } else if (questionsRight < 3) {
                        getAIMove(board);
                    }
                }

            }else{
                questionsRight=0;
                getAIMove(board);
            }

        }
        else{
            getAIMove(board);
        }
    }

    public void restartOrQuit(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.continueQuit)
                .setMessage("Would you like continue or quit?")
                .setPositiveButton(R.string.continueYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Start a new game
                        clear();

                    }
                })
                .setNegativeButton(R.string.quitYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       //call createHighScores
                        mScoresDbAdapter.createHighScore(initials, score);

                        finish();

                        Intent intent = new Intent(MainActivity.this, HighscoresActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    //Even for when the user changes between going first and going second
    public void onRadioButtonClicked(View view){
        //Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        //Check which radio button was clicked
        switch(view.getId()){

            //If the user wants to be X just clear the board and wair for their move
            case R.id.radio_X:
                if(checked)
                    mark = "X"; aiMark = "O"; clear();
                break;

            //If the user wants to be O just clear the board and get the AI's opening move
            case R.id.radio_O:
                if(checked)
                    mark = "O"; aiMark = "X"; clear(); getAIMove(board);
                break;
        }
    }

    //Checks to see if the game has ended provided with the last player to make a move
    private boolean checkEnd(String player){
        //checks the virtual board for a winner if there's a winner announce it with provided player
        if(board.isWinner()){
            announce(true, player);
            questionsRight=0;
            if(player == this.mark){
                score+=50;
                scoreDisplay.setText(score + "");
            }else score-=20;
            scoreDisplay.setText(score + "");
            return true;
        }

        //Check to see if we've reached our move total meaning it's a draw
        else if(moveCount >= 9){
            announce(false, player);
            questionsRight=0;
            return true;
        }

        //if neither win or draw then the game is still afoot!
        return false;
    }

    //Announce the winner, given a boolean for whether it was a win or a draw
    //and given the last player to make a mark
    private void announce(boolean endState, String player){
        //Check if it's a win or a draw. If it's a win, amend player with wins!
        //If it's a lose, replace player with "It's a draw"
        if(endState)
            player = player + " wins!";

        else
            player = "It's a draw!";

        //Get the application Context and setup the Toast notification
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, player, Toast.LENGTH_LONG);
        toast.show();

        restartOrQuit();

    }

    //Clears the game board
    private void clear(){
        //Get the id list of all the textview cells
        int[] idList = {R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell31, R.id.cell32, R.id.cell33 };
        TextView cell;
        //For each cell clear the text with an empty string
        for(int item : idList){
            cell = (TextView) findViewById(item);
            cell.setText("");
        }

        //Reset the game state and clear the virtual board
        isOver = false;
        questionsRight = 0;
        moveCount = 0;
        board.clear();
    }


    //Gets the AI's next move given the current state of the board
    private void getAIMove(GameBoard board){
        //Send the board to the AI for it to determine and return the move in an array {x,y}
        int[] move = ai.move(board, aiMark);
        TextView cell = null;
        //Determine the right cell to use by id first go to the right row, then the right column
        switch (move[0]){
            case 0:

                switch (move[1]) {
                    case 0:

                        cell = (TextView) findViewById(R.id.cell11); break;

                    case 1:

                        cell = (TextView) findViewById(R.id.cell12); break;

                    case 2:

                        cell = (TextView) findViewById(R.id.cell13); break;

                }
                break;

            case 1:

                switch (move[1]) {
                    case 0:

                        cell = (TextView) findViewById(R.id.cell21); break;

                    case 1:

                        cell = (TextView) findViewById(R.id.cell22); break;

                    case 2:

                        cell = (TextView) findViewById(R.id.cell23); break;

                }
                break;

            case 2:

                switch (move[1]) {
                    case 0:

                        cell = (TextView) findViewById(R.id.cell31); break;

                    case 1:

                        cell = (TextView) findViewById(R.id.cell32); break;

                    case 2:

                        cell = (TextView) findViewById(R.id.cell33); break;

                }
                break;

        }

        //Make sure there's nothing already in the cell
        //then place the mark with the ai's mark, increment the move count
        //and check to see if the game's over
        if(cell != null && cell.getText() == ""){
            board.placeMark(move[0],move[1], aiMark);
            cell.setText(aiMark);
            moveCount++;
            isOver = checkEnd(aiMark);
        }

    }


}
