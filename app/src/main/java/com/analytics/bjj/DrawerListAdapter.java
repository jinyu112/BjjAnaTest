package com.analytics.bjj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DrawerListAdapter extends ArrayAdapter {

    private Context context;
    private static ArrayList<String> alistString;
    private static ArrayList<ImageView> alistIV;
    public DrawerListAdapter(Context context, int resource, ArrayList<String> in1, ArrayList<ImageView> in2) {
        super(context, resource);
        this.alistString=in1;
        this.alistIV=in2;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.drawer_nav_item, null);


       TextView tv = (TextView) view.findViewById(R.id.text1);
        if (position==0) {
            tv.setText("My Stats");
        }
        else if (position==1) {
            tv.setText("Tournaments");
        }
        else if (position==2) {
            tv.setText("Drills");
        }
        else if (position==3) {
            tv.setText("Techniques");
        }
        else if (position==4) {
            tv.setText("Weight Tracker");
        }
        else if (position==5) {
            tv.setText("Time Tracker");
        }
        else tv.setText("About");


        ImageView iv = (ImageView) view.findViewById(R.id.nav_drawer_pic);
        if (position == 0) {
            iv.setImageResource(R.drawable.statistics_32);
        }
        else if (position == 1) {
            iv.setImageResource(R.drawable.medal_2_32);
        }
        else if (position == 2) {
            iv.setImageResource(R.drawable.drill_32);
        }
        else if (position == 3) {
            iv.setImageResource(R.drawable.technique_32);
        }
        else if (position == 4) {
            iv.setImageResource(R.drawable.scales_32);
        }
        else if (position == 5) {
            iv.setImageResource(R.drawable.clock_32);
        }
        else iv.setImageResource(R.drawable.about_32);


        return view;
    }

}
