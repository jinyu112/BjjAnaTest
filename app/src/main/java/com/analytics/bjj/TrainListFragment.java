package com.analytics.bjj;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.analytics.bjj.db.TournDataSource;
import com.analytics.bjj.db.TrainDataSource;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

public class TrainListFragment extends android.support.v4.app.ListFragment {

    private List<Train> trains;  // should this be static??
    //database related
    TrainDataSource dataSource; //should this be a private variable?

    public TrainListFragment(){
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
        menu.getItem(0).setIcon(R.drawable.plus_50);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.train_add:
                Intent intent = new Intent(getActivity(),AddTrain.class);
                startActivity(intent);
                //Log.i(LOGTAG,"adding a new tourney from the tourn list frag");
                break;
            case R.id.train_del:
                dataSource.removeAllTrains();
                refreshDisplay();
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

        //ga
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        final Tracker tracker = application.getDefaultTracker();
        if(tracker != null){
            tracker.setScreenName(getClass().getSimpleName());
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }

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
