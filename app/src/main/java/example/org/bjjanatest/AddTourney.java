package example.org.bjjanatest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import example.org.bjjanatest.db.TournDataSource;


public class AddTourney extends ActionBarActivity {

    private static TextView tv;
    private static TextView tvatt;
    private static TextView tvsuc;
    private static EditText et;
    private static Button button_minus;
    private static Button button_plus;
    private static final String LOGTAG = "BJJTRAINING";
    private TournDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tourney);

        //database related
        dataSource = new TournDataSource(this);
        dataSource.open();

//        set a generic plus and minus click listener object and use that for all the setOnClickListener methods??????

        button_minus = (Button) findViewById(R.id.ptsScoredMinus);
        button_plus = (Button) findViewById(R.id.ptsScoredPlus);
        tv = (TextView) findViewById(R.id.pointsScored);
        PlusOnClickListener ptsScoredPlusListener = new PlusOnClickListener(tv);
        button_plus.setOnClickListener(ptsScoredPlusListener);

        MinusOnClickListener ptsScoredMinusListener = new MinusOnClickListener(tv);
        button_minus.setOnClickListener(ptsScoredMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.ptsAllowedMinus);
        button_plus = (Button) findViewById(R.id.ptsAllowedPlus);
        tv = (TextView) findViewById(R.id.pointsAllowed);
        PlusOnClickListener ptsAllowedPlusListener = new PlusOnClickListener(tv);
        button_plus.setOnClickListener(ptsAllowedPlusListener);

        MinusOnClickListener ptsAllowedMinusListener = new MinusOnClickListener(tv);
        button_minus.setOnClickListener(ptsAllowedMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.swpAttMinus);
        button_plus = (Button) findViewById(R.id.swpsAttPlus);
        tvatt = (TextView) findViewById(R.id.sweepAttempted);
        PlusOnClickListener swpAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(swpAttPlusListener);

        MinusOnClickListener swpAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(swpAttMinusListener);

        button_minus = (Button) findViewById(R.id.swpSucMinus);
        button_plus = (Button) findViewById(R.id.swpsSucPlus);
        tvatt = (TextView) findViewById(R.id.sweepAttempted);
        tvsuc = (TextView) findViewById(R.id.sweepSuccessful);
        PlusOnClickListener swpSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(swpSucPlusListener);

        MinusOnClickListener swpSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(swpSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.passAttMinus);
        button_plus = (Button) findViewById(R.id.passAttPlus);
        tvatt = (TextView) findViewById(R.id.passAtt);
        PlusOnClickListener passAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(passAttPlusListener);

        MinusOnClickListener passAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(passAttMinusListener);

        button_minus = (Button) findViewById(R.id.passSucMinus);
        button_plus = (Button) findViewById(R.id.passSucPlus);
        tvatt = (TextView) findViewById(R.id.passAtt);
        tvsuc = (TextView) findViewById(R.id.passSuc);
        PlusOnClickListener passSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(passSucPlusListener);

        MinusOnClickListener passSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(passSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.tdAttMinus);
        button_plus = (Button) findViewById(R.id.tdAttPlus);
        tvatt = (TextView) findViewById(R.id.tdAtt);
        PlusOnClickListener tdAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(tdAttPlusListener);

        MinusOnClickListener tdAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(tdAttMinusListener);

        button_minus = (Button) findViewById(R.id.tdSucMinus);
        button_plus = (Button) findViewById(R.id.tdSucPlus);
        tvatt = (TextView) findViewById(R.id.tdAtt);
        tvsuc = (TextView) findViewById(R.id.tdSuc);
        PlusOnClickListener tdSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(tdSucPlusListener);

        MinusOnClickListener tdSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(tdSucMinusListener);
        ////// back take and mount

        button_minus = (Button) findViewById(R.id.backTakeMinus);
        button_plus = (Button) findViewById(R.id.backTakePlus);
        tvsuc = (TextView) findViewById(R.id.numBackTakes);
        PlusOnClickListener backTakePlusListener = new PlusOnClickListener(tvsuc);
        button_plus.setOnClickListener(backTakePlusListener);

        MinusOnClickListener backTakeMinusListener = new MinusOnClickListener(tvsuc);
        button_minus.setOnClickListener(backTakeMinusListener);

        button_minus = (Button) findViewById(R.id.mountMinus);
        button_plus = (Button) findViewById(R.id.mountPlus);
        tvsuc = (TextView) findViewById(R.id.numMount);
        PlusOnClickListener mountPlusListener = new PlusOnClickListener(tvsuc);
        button_plus.setOnClickListener(mountPlusListener);

        MinusOnClickListener mountMinusListener = new MinusOnClickListener(tvsuc);
        button_minus.setOnClickListener(mountMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.subAttMinus);
        button_plus = (Button) findViewById(R.id.subAttPlus);
        tvatt = (TextView) findViewById(R.id.subAtt);
        PlusOnClickListener subAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(subAttPlusListener);

        MinusOnClickListener subAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(subAttMinusListener);

        //////



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_tourney, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveTournament(View view){
        Tourn tourn = new Tourn();
        tv = (TextView) findViewById(R.id.pointsScored);
        tourn.setPointsScored(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.pointsAllowed);
        tourn.setPointsAllowed(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.sweepAttempted);
        tourn.setSweepAttempted(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.sweepSuccessful);
        tourn.setSweepSuccessful(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.passAtt);
        tourn.setPassAttempted(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.passSuc);
        tourn.setPassSuccessful(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.tdAtt);
        tourn.setTdAttempted(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.tdSuc);
        tourn.setTdSuccessful(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.numBackTakes);
        tourn.setNumBackTakes(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.numMount);
        tourn.setNumMounts(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.subAtt);
        tourn.setSubAttempted(Integer.parseInt(tv.getText().toString()));

        CheckBox cb = (CheckBox) findViewById(R.id.winCheck);
        if (cb.isChecked()) {
            tourn.setWin(1);
        }
        else {
            tourn.setWin(0);
        }

        cb = (CheckBox) findViewById(R.id.subCheck);
        if (cb.isChecked()) {
            tourn.setSubSuccessful(1);
            tourn.setWin(1);
        }
        else {
            tourn.setSubSuccessful(0);
        }

        if (tourn.getPointsAllowed() < tourn.getPointsScored()) {
            tourn.setWin(1);
        }
        else if (tourn.getPointsAllowed() > tourn.getPointsScored()) {
            tourn.setWin(0);
        }



        //!!!!!!!!!!!!!!!move match time, name, belt, date inputs to a popup alert before saving
        //!!!!!!!!!!!!!!also provide safeguard for empty fields
        et = (EditText) findViewById(R.id.matchTimeMinEV);
        double minutes = Double.parseDouble(et.getText().toString());

        et = (EditText) findViewById(R.id.matchTimeSecEV);
        double seconds = Double.parseDouble(et.getText().toString());
        if (seconds > 60.0) {
            seconds = 60.0;
        }
        double sec2min = seconds/60.0;

        tourn.setMatchTime(minutes+sec2min);

        et = (EditText) findViewById(R.id.tournNameEV);
        tourn.setTournName(et.getText().toString());

        tourn = dataSource.create(tourn);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open(); //opens connection to the datasource
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
}
