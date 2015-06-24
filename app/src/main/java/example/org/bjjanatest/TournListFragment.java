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

public class TournListFragment extends android.support.v4.app.ListFragment {

    private List<Tourn> tourns;  // should this be static??
    //database related
    TournDataSource dataSource; //should this be a private variable?
    private static final String LOGTAG = "BJJTRAINING";

    public TournListFragment (){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new TournDataSource(getActivity());
        dataSource.open();
        Log.i(LOGTAG, "Tourn list frag onCreate called");
        refreshDisplay();
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_tourney, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tourn_add:
                Intent intent = new Intent(getActivity(),AddTourney.class);
                startActivity(intent);
                Log.i(LOGTAG,"adding a new tourney from the tourn list frag");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        Intent intent = new Intent(getActivity(),ViewTourn.class);
        Tourn tourn;
        tourn = tourns.get(pos);
        intent.putExtra("tourn_obj",tourn);
        startActivity(intent);
    }

    public void refreshDisplay() {
        ArrayAdapter<Tourn> adapter = new TournListAdapter(getActivity(),getTourns());
        setListAdapter(adapter);
    }

    public List<Tourn> getTourns() {
        tourns = dataSource.findAll();
        return tourns;
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
