package com.analytics.bjj;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.analytics.bjj.db.TournDataSource;

public class EditTourn extends ActionBarActivity {

    private static MyTextView tv;
    private static MyTextView tvatt;
    private static MyTextView tvsuc;
    private static MyEditText et;
    private static Button button_minus;
    private static Button button_plus;
    private static CheckBox cb;

    private static TournDataSource dataSource; // should this be static??????
    private static Tourn tourn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tourn);

        //database related
        dataSource = new TournDataSource(this);
        dataSource.open();

        //Get data from the tourn object
        Bundle data = getIntent().getExtras();
        tourn = data.getParcelable("tourn_obj_edit");
        tv = (MyTextView) findViewById(R.id.pointsScored_edit);
        tv.setText(Integer.toString(tourn.getPointsScored()));

        tv = (MyTextView) findViewById(R.id.pointsAllowed_edit);
        tv.setText(Integer.toString(tourn.getPointsAllowed()));

        tv = (MyTextView) findViewById(R.id.sweepAttempted_edit);
        tv.setText(Integer.toString(tourn.getSweepAttempted()));

        tv = (MyTextView) findViewById(R.id.sweepSuccessful_edit);
        tv.setText(Integer.toString(tourn.getSweepSuccessful()));

        tv = (MyTextView) findViewById(R.id.passAtt_edit);
        tv.setText(Integer.toString(tourn.getPassAttempted()));

        tv = (MyTextView) findViewById(R.id.passSuc_edit);
        tv.setText(Integer.toString(tourn.getPassSuccessful()));

        tv = (MyTextView) findViewById(R.id.tdAtt_edit);
        tv.setText(Integer.toString(tourn.getTdAttempted()));

        tv = (MyTextView) findViewById(R.id.tdSuc_edit);
        tv.setText(Integer.toString(tourn.getTdSuccessful()));

        tv = (MyTextView) findViewById(R.id.subAtt_edit);
        tv.setText(Integer.toString(tourn.getSubAttempted()));

        if (tourn.getSubSuccessful()==1) {
            cb = (CheckBox) findViewById(R.id.subCheck_edit);
            cb.setChecked(true);
        }

        if (tourn.getWin()==1) {
            cb = (CheckBox) findViewById(R.id.winCheck_edit);
            cb.setChecked(true);
        }

        et = (MyEditText) findViewById(R.id.matchTimeMinEV_edit);
        int tempMin = (int) Math.floor(tourn.getMatchTime());
        int tempSeconds = (int) Math.round((tourn.getMatchTime() - tempMin)*60.0);
        et.setText(Integer.toString(tempMin));

        et = (MyEditText) findViewById(R.id.matchTimeSecEV_edit);
        if (tempSeconds<10) {
            et.setText("0" + Integer.toString(tempSeconds));
        } else et.setText(Integer.toString(tempSeconds));

//        set a generic plus and minus click listener object and use that for all the setOnClickListener methods??????
        button_minus = (Button) findViewById(R.id.ptsScoredMinus_edit);
        button_plus = (Button) findViewById(R.id.ptsScoredPlus_edit);
        tv = (MyTextView) findViewById(R.id.pointsScored_edit);
        PlusOnClickListener ptsScoredPlusListener = new PlusOnClickListener(tv);
        button_plus.setOnClickListener(ptsScoredPlusListener);

        MinusOnClickListener ptsScoredMinusListener = new MinusOnClickListener(tv);
        button_minus.setOnClickListener(ptsScoredMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.ptsAllowedMinus_edit);
        button_plus = (Button) findViewById(R.id.ptsAllowedPlus_edit);
        tv = (MyTextView) findViewById(R.id.pointsAllowed_edit);
        PlusOnClickListener ptsAllowedPlusListener = new PlusOnClickListener(tv);
        button_plus.setOnClickListener(ptsAllowedPlusListener);

        MinusOnClickListener ptsAllowedMinusListener = new MinusOnClickListener(tv);
        button_minus.setOnClickListener(ptsAllowedMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.swpAttMinus_edit);
        button_plus = (Button) findViewById(R.id.swpsAttPlus_edit);
        tvatt = (MyTextView) findViewById(R.id.sweepAttempted_edit);
        PlusOnClickListener swpAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(swpAttPlusListener);

        MinusOnClickListener swpAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(swpAttMinusListener);

        button_minus = (Button) findViewById(R.id.swpSucMinus_edit);
        button_plus = (Button) findViewById(R.id.swpsSucPlus_edit);
        tvatt = (MyTextView) findViewById(R.id.sweepAttempted_edit);
        tvsuc = (MyTextView) findViewById(R.id.sweepSuccessful_edit);
        PlusOnClickListener swpSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(swpSucPlusListener);

        MinusOnClickListener swpSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(swpSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.passAttMinus_edit);
        button_plus = (Button) findViewById(R.id.passAttPlus_edit);
        tvatt = (MyTextView) findViewById(R.id.passAtt_edit);
        PlusOnClickListener passAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(passAttPlusListener);

        MinusOnClickListener passAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(passAttMinusListener);

        button_minus = (Button) findViewById(R.id.passSucMinus_edit);
        button_plus = (Button) findViewById(R.id.passSucPlus_edit);
        tvatt = (MyTextView) findViewById(R.id.passAtt_edit);
        tvsuc = (MyTextView) findViewById(R.id.passSuc_edit);
        PlusOnClickListener passSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(passSucPlusListener);

        MinusOnClickListener passSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(passSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.tdAttMinus_edit);
        button_plus = (Button) findViewById(R.id.tdAttPlus_edit);
        tvatt = (MyTextView) findViewById(R.id.tdAtt_edit);
        PlusOnClickListener tdAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(tdAttPlusListener);

        MinusOnClickListener tdAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(tdAttMinusListener);

        button_minus = (Button) findViewById(R.id.tdSucMinus_edit);
        button_plus = (Button) findViewById(R.id.tdSucPlus_edit);
        tvatt = (MyTextView) findViewById(R.id.tdAtt_edit);
        tvsuc = (MyTextView) findViewById(R.id.tdSuc_edit);
        PlusOnClickListener tdSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(tdSucPlusListener);

        MinusOnClickListener tdSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(tdSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.subAttMinus_edit);
        button_plus = (Button) findViewById(R.id.subAttPlus_edit);
        tvatt = (MyTextView) findViewById(R.id.subAtt_edit);
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

    public void saveTournament_edit(View view){
        boolean sensibleData = true;
        try {
            tv = (MyTextView) findViewById(R.id.pointsScored_edit);
            tourn.setPointsScored(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.pointsAllowed_edit);
            tourn.setPointsAllowed(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.sweepAttempted_edit);
            tourn.setSweepAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.sweepSuccessful_edit);
            tourn.setSweepSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.passAtt_edit);
            tourn.setPassAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.passSuc_edit);
            tourn.setPassSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.tdAtt_edit);
            tourn.setTdAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.tdSuc_edit);
            tourn.setTdSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.subAtt_edit);
            tourn.setSubAttempted(Integer.parseInt(tv.getText().toString()));
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in EditTourney Activity (metric input): "
                    +  ex.getMessage());
        }

        CheckBox cbWin = (CheckBox) findViewById(R.id.winCheck_edit);
        CheckBox cbSub = (CheckBox) findViewById(R.id.subCheck_edit);

        // if the submission checkbox is checked
        if (cbSub.isChecked()) {
            tourn.setSubSuccessful(1);
            cbWin.setChecked(true);
        }
        else {
            tourn.setSubSuccessful(0);
        }

        // if the wind checkbox is checked
        if (cbWin.isChecked()) {
            tourn.setWin(1);
        }
        else {
            tourn.setWin(0);
        }

        //Determine match outcome by points
        if (tourn.getPointsAllowed() < tourn.getPointsScored()) {
            tourn.setWin(1);
        }
        else if ((tourn.getPointsAllowed() >= tourn.getPointsScored()) && cbSub.isChecked() ) {
            tourn.setWin(1);
        }
        else if (tourn.getPointsAllowed() == tourn.getPointsScored()) {
            if (cbWin.isChecked()) tourn.setWin(1);
            else tourn.setWin(0);
        }
        else {
            tourn.setWin(0);
        }

        //Set the match time
        et = (MyEditText) findViewById(R.id.matchTimeMinEV_edit);
        double minutes = 0.0;
        try {
            minutes = Double.parseDouble(et.getText().toString());
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTourney Activity (minutes): "
                    +  ex.getMessage());
        }

        et = (MyEditText) findViewById(R.id.matchTimeSecEV_edit);

        double seconds = 0.0;
        try {
            seconds = Double.parseDouble(et.getText().toString());
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTourney Activity (seconds): "
                    +  ex.getMessage());
        }
        if (seconds > 60.0) {
            seconds = 60.0;
        }
        double sec2min = seconds/60.0;
        double ttime = Math.round((minutes+sec2min)*100)/100.0;

        tourn.setMatchTime(ttime);

        //Perform tournament data checks
        if (tourn.getPassAttempted()<tourn.getPassSuccessful()) {
            sensibleData=false;
            Toast.makeText(this, "Not enough pass attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (tourn.getTdAttempted()<tourn.getTdSuccessful()) {
            sensibleData=false;
            Toast.makeText(this, "Not enough TD attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (tourn.getSweepAttempted()<tourn.getSweepSuccessful()) {
            sensibleData=false;
            Toast.makeText(this, "Not enough sweep attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (tourn.getSubAttempted()<tourn.getSubSuccessful()) {
            sensibleData=false;
            Toast.makeText(this, "Not enough submission attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (tourn.getPointsScored()==0 && tourn.getWin()==1 && tourn.getSubSuccessful()!=1) {
            sensibleData=false;
            Toast.makeText(this, "Incorrect input data.",
                    Toast.LENGTH_SHORT).show();
        }

        if (sensibleData) {
            dataSource.update(tourn);
            finish();
        }


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
