package com.verstegenventures.android.tictactoe;

/**
 * Created by jfv059 on 8/10/2015.
 */
public class GameBoard {

    //Declare the global variables
    private String[][] theBoard = new String[3][3];

    //Initialize the Game board filled with empty strings
    GameBoard(){
        //Initiate the game with blanks
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                theBoard[i][j] = "";
            }
        }
    }

    //Clears the game board by looping through each row and column nad puts
    //the empty string in each position
    public void clear(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                theBoard[i][j] = "";
            }
        }
    }

    //getter that returns the virtual board
    public String[][] getTheBoard(){
        return theBoard;
    }

    //Checks to make sure a mark doesn't exist on the square
    //before adding the mark.
    public void placeMark(int xloc, int yloc, String mark){
        if(theBoard[xloc][yloc] == "") theBoard[xloc][yloc] = mark;
    }

    //Determines if there is a winner or not.
    //Checks each diagonal then loops through the rows / columns
    public boolean isWinner() {
        //Check Diagonals
        if (theBoard[0][0] == theBoard[1][1] && theBoard[0][0] == theBoard[2][2] && theBoard[0][0] != "")
            return true;
        if (theBoard[2][0] == theBoard[1][1] && theBoard[2][0] == theBoard[0][2] && theBoard[2][0] != "")
            return true;

        for (int i = 0; i < 3; i++) {
            //Checks Rows
            if (theBoard[i][0] == theBoard[i][1] && theBoard[i][1] == theBoard[i][2] && theBoard[i][0] != "")
                return true;

            //Check Columns
            if (theBoard[0][i] == theBoard[1][i] && theBoard[1][i] == theBoard[2][i] && theBoard[0][i] != "")
                return true;
        }

        return false;

    }


}
