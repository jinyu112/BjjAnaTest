package example.org.bjjanatest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import example.org.bjjanatest.db.DrillDataSource;

public class DrillListFragment extends ListFragment{

    private static DrillDataSource dataSource;
    private List<Drill> drills;
    private static final String LOGTAG = "BJJTRAINING";


    public DrillListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new DrillDataSource(getActivity());
        dataSource.open();
        setHasOptionsMenu(true);
        refreshDisplay();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_drill, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drill_add:
                Intent intent = new Intent(getActivity(),AddDrill.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        Intent intent = new Intent(getActivity(),EditDrill.class);
        Drill drill;
        drill = drills.get(pos);
        intent.putExtra("drill_obj_edit",drill); //this name should be the same as the getparcelable name
        startActivity(intent);
    }

    public void refreshDisplay() {
        ArrayAdapter<Tourn> adapter = new DrillListAdapter(getActivity(),getDrills(),dataSource.getMaxReps());
        setListAdapter(adapter);
    }

    public List<Drill> getDrills() {
        drills = dataSource.findAll();
        return drills;
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
