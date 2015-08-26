package com.verstegenventures.android.tictactoe;

/**
 * Created by jfv059 on 8/26/2015.
 */
public class HighScore {
    private int mId;
    private String mInitials;
    private int mScore;

    public HighScore(int id, String initials, int score){
        mId = id;
        mInitials = initials;
        mScore = score;
    }

    public int getId(){return mId;}

    public void setId(int id){mId = id;}

    public int getScore(){return mScore;}

    public void setScore(int score){mScore = score;}

    public String getInitials(){return mInitials;}

    public void setInitials(String initials){mInitials = initials;}
}
