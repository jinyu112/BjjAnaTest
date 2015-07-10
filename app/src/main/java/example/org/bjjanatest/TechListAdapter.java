package example.org.bjjanatest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

        TextView tv = (TextView) view.findViewById(R.id.tech_list_item_name);
        tv.setText(tech.getTechName());

        tv = (TextView) view.findViewById(R.id.tech_list_item_cat);
        if (tech.getTechType()==0) {
            tv.setText("Position");
        }
        else if (tech.getTechType()==1) {
            tv.setText("Submission");
        }
        else if (tech.getTechType()==2) {
            tv.setText("Sweep");
        }
        else if (tech.getTechType()==3) {
            tv.setText("Takedown");
        }
        else if (tech.getTechType()==4) {
            tv.setText("Reversal");
        }
        else if (tech.getTechType()==5) {
            tv.setText("Defense");
        }
        else {
            tv.setText("Other");
        }

        return view;
    }
}
