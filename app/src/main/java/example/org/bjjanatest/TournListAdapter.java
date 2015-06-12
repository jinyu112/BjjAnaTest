package example.org.bjjanatest;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TournListAdapter extends ArrayAdapter<Tourn> {
    Context context; //should these be private variables?
    private static List<Tourn> tourns; //should this be private and static?
    private static final String LOGTAG = "BJJTRAINING";

    public TournListAdapter(Context context, List<Tourn> tourns) {
        super(context, android.R.layout.simple_list_item_1,tourns);
        this.context = context;
        this.tourns = tourns;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.tourn_list_item,null);

        Tourn tourn = tourns.get(position);


        TextView tv = (TextView) view.findViewById(R.id.tourn_list_item_id);
        tv.setText(String.valueOf(tourn.getId()));


        tv = (TextView) view.findViewById(R.id.tourn_list_item_name);
        tv.setText(tourn.getBelt());

        return view;
    }
}
