package com.verstegenventures.android.tictactoe;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by jfv059 on 8/26/2015.
 */
public class HighScoresCursorAdapter extends SimpleCursorAdapter{

    public HighScoresCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags){
        super(context, layout, c, from, to, flags);
    }

    //to use a view holder, must override the following two methods and define a viewholder class
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        super.bindView(view, context, cursor);

//        ViewHolder holder = (ViewHolder) view.getTag();
//        if(holder == null){
//            holder = new ViewHolder();
//            holder.listTab = (TextView) view.findViewById(R.id.row_tab);
//            view.setTag(holder);
//        }


    }

    static class ViewHolder{
        //store the view
        TextView listTab;
    }
}
