package example.org.bjjanatest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TrainListAdapter  extends ArrayAdapter<Train> {
    Context context; //should these be private variables?
    private static List<Train> trains; //should this be private and static?
    private static final String LOGTAG = "BJJTRAINING";

    public TrainListAdapter(Context context, List<Train> trains) {
        super(context, android.R.layout.simple_list_item_1,trains);
        this.context = context;
        this.trains = trains;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.training_list_item, null);

        Train train = trains.get(position);

        TextView tv = (TextView) view.findViewById(R.id.train_list_item_name);
        tv.setText(train.getTrainName());

        tv = (TextView) view.findViewById(R.id.train_list_item_belt);
        tv.setText(train.getBelt());

        tv = (TextView) view.findViewById(R.id.train_list_item_matchTime);
        tv.setText(String.format("%5.2f",train.getMatchTime()));

        return view;
    }
}
