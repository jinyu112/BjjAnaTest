package example.org.bjjanatest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import example.org.bjjanatest.db.TechDataSource;


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
        Log.i(LOGTAG, "Tech list frag onCreate called");
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
                Log.i(LOGTAG,"adding a new tech from the tech list frag");
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
