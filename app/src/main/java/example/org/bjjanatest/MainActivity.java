package example.org.bjjanatest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.SQLData;
import java.util.List;

import example.org.bjjanatest.db.TournDataSource;
import example.org.bjjanatest.db.trainingDBOpenHelper;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    //LOGCAT
    private static final String LOGTAG = "BJJTRAINING";

    //activity codes
    private static final int ADDTOURNEY_REQ_CODE = 1;

    //database related
    TournDataSource dataSource;

    //Drawer layout objects
    private DrawerLayout drawerLayout;
    private ListView lv;
    private String[] drawerStrings;
    private ActionBarDrawerToggle drawerListener;

    //Other objects
    TextView tv;
    Fragment frag;
    FragmentTransaction fragTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize stuff
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        lv = (ListView) findViewById(R.id.drawerList);
        drawerStrings=getResources().getStringArray(R.array.drawerBar);
        lv.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,drawerStrings)); //the adapter handles the data behind the list
        lv.setOnItemClickListener(this);


        drawerListener = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
        public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true); //required to show nav button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //required to show nav button

        dataSource = new TournDataSource(this);
        getStats();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void goToTourneyActivity(View view){
        Intent intent = new Intent(this,AddTourney.class);
        startActivityForResult(intent,ADDTOURNEY_REQ_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStats();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState(); //syncs the nav icon with opening/closing drawer
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"Item " + position + " was selected.",Toast.LENGTH_SHORT).show();
        lv.setItemChecked(position,true);
        selectItem(position);
    }

    public void selectItem(int position) {
        setTitle(drawerStrings[position]);
        if (position == 2) {
            frag = new AddTourneyFragment();
            fragTrans = getFragmentManager().beginTransaction().add(R.layout.fragment_add_tourney,frag);
            fragTrans.commit();
        }

    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
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

