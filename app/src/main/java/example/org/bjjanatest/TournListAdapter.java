package example.org.bjjanatest;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TournListAdapter extends ArrayAdapter<Tourn> {
    private Context context; //should these be private variables?
    private static List<Tourn> tourns; //should this be private and static?
    private static final String LOGTAG = "BJJTRAINING";
    private static final String BELT_ARRAY[] =
            new String[] {"White","Blue","Purple","Brown","Black","NoGi Novice", "NoGi Intermed.","NoGi Adv."};

    public TournListAdapter(Context context, List<Tourn> tourns) {
        super(context, android.R.layout.simple_list_item_1,tourns);
        this.context = context;
        this.tourns = tourns;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.tourn_list_item, null);

        Tourn tourn = tourns.get(position);

        MyTextView tv = (MyTextView) view.findViewById(R.id.tourn_list_item_name);
        tv.setText(tourn.getTournName());

        ImageView iv = (ImageView) view.findViewById(R.id.tourn_list_item_belt);

        if (tourn.getBelt().equals(BELT_ARRAY[0])) {
            iv.setImageResource(R.drawable.white);
        }
        else if (tourn.getBelt().equals(BELT_ARRAY[1])) {
            iv.setImageResource(R.drawable.blue);
        }
        else if (tourn.getBelt().equals(BELT_ARRAY[2])) {
            iv.setImageResource(R.drawable.purple);
        }
        else if (tourn.getBelt().equals(BELT_ARRAY[3])) {
            iv.setImageResource(R.drawable.brown);
        }
        else if (tourn.getBelt().equals(BELT_ARRAY[4])) {
            iv.setImageResource(R.drawable.black);
        }
        else iv.setImageResource(R.drawable.nogi);

        tv = (MyTextView) view.findViewById(R.id.tourn_list_item_win);
        if (tourn.getWin() == 1) {
            tv.setText("WIN");
            tv.setTextColor(Color.GREEN);
        }
        else {
            tv.setText("LOSS");
            tv.setTextColor(Color.RED);
        }



        return view;
    }
}
