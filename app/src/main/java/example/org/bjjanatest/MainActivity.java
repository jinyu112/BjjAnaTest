package example.org.bjjanatest;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Html;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.SQLData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    // Alert Dialog Manager
    static AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    static SessionManagement session;

    // Button Logout
    Button btnLogin;

    static PostStats postStatsObj;

    private static final String filename = "myTimeData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize stuff
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.WHITE);
        setSupportActionBar(toolbar);

        // Find our drawer view
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawerLayout);
        // Setup drawer view
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.drawerList), toolbar,
                R.layout.drawer_nav_item, R.id.mainContent);

        // Add nav items
        dlDrawer.addNavItem("My Stats", "My Statistics", MainContentFragment.class);
        dlDrawer.addNavItem("Tournaments", "Tournaments", TournListFragment.class);
        dlDrawer.addNavItem("Drills", "Drills Rep Tracker", DrillListFragment.class);
        dlDrawer.addNavItem("Techniques", "Technique List", TechListFragment.class);
        dlDrawer.addNavItem("Weight Tracker", "Weight Tracker", AddWeightFragment.class);
        dlDrawer.addNavItem("Time Tracker", "Time Tracker", AddTimeFragment.class);
        dlDrawer.addNavItem("About", "About", AboutFragment.class);

        // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }
        dataSource = new TournDataSource(this);
        dataSource.open(); //opens connection to the datasource
        List<Tourn> tourns = dataSource.findAll();
        AppRater.app_launched(this);


        // Session class instance (for email sign up)
        session = new SessionManagement(getApplicationContext());

        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);

        // Button login
        btnLogin = (Button) findViewById(R.id.btnLogin);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // email
        String email = user.get(SessionManagement.KEY_EMAIL);

        //training time data
        String timeDataForDisplay[] = new String[]{"0", "0", "0", "0"};
        int minDateInt=19000101;
        int maxDateInt=minDateInt;
        int numDaysBetween=0;
        double weeks = 0.0;
        double avgTimePerWeek=0.0;
        double totalTimeTrained=0.0;
        if (fileExistance(filename)) {
            timeDataForDisplay = readTimeDataInternal();
            minDateInt = 19000101;
            maxDateInt = minDateInt;
            try {
                minDateInt = Integer.parseInt(timeDataForDisplay[1]);
                maxDateInt = Integer.parseInt(timeDataForDisplay[2]);
            } catch (NumberFormatException ex) {
                System.err.println("Caught NumberFormatException in mainactivity: "
                        + ex.getMessage());
            }
            numDaysBetween = determineDaysBetween(maxDateInt, minDateInt);
            if (numDaysBetween < 7) {
                weeks = 1;
            } else weeks = (double) numDaysBetween / 7.0;
            avgTimePerWeek = 0.0;
            totalTimeTrained=0.0;
            try {
                avgTimePerWeek = (double) Double.parseDouble(timeDataForDisplay[3]) / weeks;
                totalTimeTrained = (double) Double.parseDouble(timeDataForDisplay[3]);
            } catch (NumberFormatException ex) {
                System.err.println("Caught NumberFormatException in AddTimeFragment fragment: "
                        + ex.getMessage());
            }

            avgTimePerWeek = Math.round(avgTimePerWeek * 10) / 10.0;
            totalTimeTrained = Math.round(totalTimeTrained*10)/10.0;
        }

        if (email!=null) { //email was not successfully posted to database
            postStatsObj = new PostStats(email,session);
            postStatsObj.setTournLen(dataSource.getTournLen());
            postStatsObj.setTotalPts(dataSource.getTotalPts());
            postStatsObj.setTotalPtsAllowed(dataSource.getTotalPtsAllowed());
            postStatsObj.setSubSucPerc(dataSource.getSubSucPerc());
            postStatsObj.setTdSucPerc(dataSource.getTdSucPerc());
            postStatsObj.setSweepSucPerc(dataSource.getSweepSucPerc());
            postStatsObj.setPassSucPerc(dataSource.getPassSucPerc());
            postStatsObj.setTotalTime(totalTimeTrained);
            postStatsObj.setAvgTimePerWeek(avgTimePerWeek);

            postStatsObj.execute();
        }

        dataSource.close();



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

    @Override
    protected void onResume() {

        super.onResume();
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



    public String[] readTimeDataInternal() {
        String timeDataArrayRetrieved[] = new String[]{"0", "0", "0", "0"};
        try {
            FileInputStream fin = this.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fin);
            timeDataArrayRetrieved = (String[]) ois.readObject();
            ois.close();
            fin.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return timeDataArrayRetrieved;
    }

    public boolean fileExistance(String fname){
        File file = this.getFileStreamPath(fname);
        return file.exists();
    }

    //This function calculates the number of days between two string dates
    public int determineDaysBetween(int currDate, int prevDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDateTemp=Integer.toString(currDate);
        String prevDateTemp=Integer.toString(prevDate);
        int numDaysBetween=0;
        try {
            Date currDateObj = myFormat.parse(currentDateTemp);
            Date minDateObj = myFormat.parse(prevDateTemp);
            long diff = currDateObj.getTime() - minDateObj.getTime();
            numDaysBetween = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            numDaysBetween=-1;
        }
        return numDaysBetween;
    }

}

