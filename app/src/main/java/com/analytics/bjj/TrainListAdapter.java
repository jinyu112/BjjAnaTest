package com.analytics.bjj;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class TrainListAdapter extends ArrayAdapter<Train> {
    private Context context; //should these be private variables?
    private static List<Train> trains; //should this be private and static?
    private static final String BELT_ARRAY[] =
            new String[] {"White","Blue","Purple","Brown","Black"};

    public TrainListAdapter(Context context, List<Train> trains) {
        super(context, android.R.layout.simple_list_item_1,trains);
        this.context = context;
        this.trains = trains;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.train_list_item, null);

        Train train = trains.get(position);

        MyTextView tv = (MyTextView) view.findViewById(R.id.train_list_item_name);
        tv.setText(train.getTrainName());

        ImageView iv = (ImageView) view.findViewById(R.id.train_list_item_belt);

        if (train.getBelt().equals(BELT_ARRAY[0])) {
            iv.setImageResource(R.drawable.white);
        }
        else if (train.getBelt().equals(BELT_ARRAY[1])) {
            iv.setImageResource(R.drawable.blue);
        }
        else if (train.getBelt().equals(BELT_ARRAY[2])) {
            iv.setImageResource(R.drawable.purple);
        }
        else if (train.getBelt().equals(BELT_ARRAY[3])) {
            iv.setImageResource(R.drawable.brown);
        }
        else if (train.getBelt().equals(BELT_ARRAY[4])) {
            iv.setImageResource(R.drawable.black);
        }
        else iv.setImageResource(R.drawable.nogi);


        iv = (ImageView) view.findViewById(R.id.train_list_item_oppbelt);


        if (train.getOppBelt().equals(BELT_ARRAY[0])) {
            iv.setImageResource(R.drawable.white);
        }
        else if (train.getOppBelt().equals(BELT_ARRAY[1])) {
            iv.setImageResource(R.drawable.blue);
        }
        else if (train.getOppBelt().equals(BELT_ARRAY[2])) {
            iv.setImageResource(R.drawable.purple);
        }
        else if (train.getOppBelt().equals(BELT_ARRAY[3])) {
            iv.setImageResource(R.drawable.brown);
        }
        else if (train.getOppBelt().equals(BELT_ARRAY[4])) {
            iv.setImageResource(R.drawable.black);
        }
        else iv.setImageResource(R.drawable.nogi);

        return view;
    }
}
