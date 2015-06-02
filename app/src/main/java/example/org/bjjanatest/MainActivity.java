package example.org.bjjanatest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;
import java.util.List;

import example.org.bjjanatest.db.TournDataSource;
import example.org.bjjanatest.db.trainingDBOpenHelper;


public class MainActivity extends ActionBarActivity {
    //LOGCAT
    private static final String LOGTAG = "BJJTRAINING";

    //activity codes
    private static final int ADDTOURNEY_REQ_CODE = 1;

    //database related
    TournDataSource dataSource;

    //Drawer layout objects
    private FragmentNavigationDrawer dlDrawer;

    //Other objects
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize stuff
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawerLayout);
        // Setup drawer view
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.drawerList), toolbar,
                R.layout.drawer_nav_item, R.id.mainContent);
        // Add nav items
        dlDrawer.addNavItem("My Stats", "My Statistics", MainContentFragment.class);
        // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }
        dataSource = new TournDataSource(this);

//        getStats();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        if (dlDrawer.isDrawerOpen()) {
            // Uncomment to hide menu items
            // menu.findItem(R.id.mi_test).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Uncomment to inflate menu items to Action Bar
        // inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dlDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }

    public void goToTourneyActivity(View view){
        Intent intent = new Intent(this,AddTourney.class);
        startActivityForResult(intent,ADDTOURNEY_REQ_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getStats();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataSource.close();
    }

    public void getStats () {
        dataSource.open(); //opens connection to the datasource
        List<Tourn> tourns = dataSource.findAll();
        if (tourns.size()!=0) {
            Log.i(LOGTAG,"tournlen: " + tourns.size());

            tv = (TextView) findViewById(R.id.main_ptsScored);
            tv.setText(String.format("%d",dataSource.getTotalPts()));

            tv = (TextView) findViewById(R.id.main_ptsAllowed);
            tv.setText(String.format("%d",dataSource.getTotalPtsAllowed()));

            tv = (TextView) findViewById(R.id.main_tdPerc);
            tv.setText(String.format("%2.2f",dataSource.getTdSucPerc()));

            tv = (TextView) findViewById(R.id.main_passperc);
            tv.setText(String.format("%2.2f",dataSource.getPassSucPerc()));

            tv = (TextView) findViewById(R.id.main_swpPerc);
            tv.setText(String.format("%2.2f",dataSource.getSweepSucPerc()));

            tv = (TextView) findViewById(R.id.main_subPerc);
            tv.setText(String.format("%2.2f",dataSource.getSubSucPerc()));

            tv = (TextView) findViewById(R.id.main_avgMatchTime);
            tv.setText(String.format("%5.2f",dataSource.getAvgMatchTime()));

            tv = (TextView) findViewById(R.id.main_numTourn);
            tv.setText(String.format("%d",dataSource.getTournLen()));
        }
        else {
            Toast toast;
            toast=Toast.makeText(this, "No tournament data found", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}

