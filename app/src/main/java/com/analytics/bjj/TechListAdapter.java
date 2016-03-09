package com.analytics.bjj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class TechListAdapter extends ArrayAdapter<Tech> {
    private List<Tech> techs;
    private Context context;

    public TechListAdapter(Context context, List<Tech> techs) {
        super(context, android.R.layout.simple_list_item_1,techs);
        this.techs=techs;
        this.context=context;

    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.tech_list_item, null);

        Tech tech = techs.get(position);

        MyTextView tv = (MyTextView) view.findViewById(R.id.tech_list_item_name);
        tv.setText(tech.getTechName());

        tv = (MyTextView) view.findViewById(R.id.tech_list_item_cat);
        ImageView iv = (ImageView) view.findViewById(R.id.tech_list_item_icon);


        if (tech.getTechType()==0) {
            tv.setText("Pass");
            iv.setImageResource(R.drawable.position_50);
        }
        else if (tech.getTechType()==1) {
            tv.setText("Submission");
            iv.setImageResource(R.drawable.submission__50);
        }
        else if (tech.getTechType()==2) {
            tv.setText("Sweep");
            iv.setImageResource(R.drawable.sweep_50);
        }
        else if (tech.getTechType()==3) {
            tv.setText("Takedown");
            iv.setImageResource(R.drawable.td_50);
        }
        else if (tech.getTechType()==4) {
            tv.setText("Defense");
            iv.setImageResource(R.drawable.reversal_50);
        }
        else if (tech.getTechType()==5) {
            tv.setText("Misc.");
            iv.setImageResource(R.drawable.defense_50);
        }
        else {
            tv.setText("Other");
        }

        return view;
    }
}
