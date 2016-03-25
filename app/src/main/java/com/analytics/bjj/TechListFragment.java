package com.analytics.bjj;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import com.analytics.bjj.db.TechDataSource;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class TechListFragment extends android.support.v4.app.ListFragment {
    private List<Tech> techs;

    TechDataSource dataSource; //should this be a private variable?
    private static final String LOGTAG = "BJJTRAINING";

    public TechListFragment () {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new TechDataSource(getActivity());
        dataSource.open();
        refreshDisplay();
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_tech, menu);
        menu.getItem(0).setIcon(R.drawable.plus_50);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tech_add:
                Intent intent = new Intent(getActivity(),AddTech.class);
                startActivity(intent);
                //Log.i(LOGTAG,"adding a new tech from the tech list frag");
                break;
            case R.id.tech_del:
                dataSource.removeAllTechs();
                refreshDisplay();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        Intent intent = new Intent(getActivity(),ViewTech.class);
        Tech tech;
        tech = techs.get(pos);
        intent.putExtra("tech_obj",tech);
        startActivity(intent);
    }

    public void refreshDisplay() {
        ArrayAdapter<Tech> adapter = new TechListAdapter(getActivity(),getTechs());
        setListAdapter(adapter);
    }

    public List<Tech> getTechs() {
        techs = dataSource.findAll();
        return techs;
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
