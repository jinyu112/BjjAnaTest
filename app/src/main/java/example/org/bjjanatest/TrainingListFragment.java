package example.org.bjjanatest;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import example.org.bjjanatest.db.TournDataSource;
import example.org.bjjanatest.db.TrainDataSource;

public class TrainingListFragment extends  android.support.v4.app.ListFragment{

    private List<Train> trains;  // should this be static??
    //database related
    TrainDataSource dataSource; //should this be a private variable?
    private static final String LOGTAG = "BJJTRAINING";

    public TrainingListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new TrainDataSource(getActivity());
        dataSource.open();
        refreshDisplay();
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_training, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.train_add:
                Intent intent = new Intent(getActivity(),AddTraining.class);
                startActivity(intent);
                Log.i(LOGTAG,"adding a new training session from the train list frag");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        Intent intent = new Intent(getActivity(),ViewTrain.class);
        Train train;
        train = trains.get(pos);
        intent.putExtra("train_obj",train);
        startActivity(intent);
    }

    public void refreshDisplay() {
        ArrayAdapter<Train> adapter = new TrainListAdapter(getActivity(),getTrains());
        setListAdapter(adapter);
    }

    public List<Train> getTrains() {
        trains = dataSource.findAll();
        return trains;
    }


    @Override
    public void onResume() {
        super.onResume();
        dataSource.open(); //opens connection to the datasource
        refreshDisplay();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataSource.close();
    }
}
