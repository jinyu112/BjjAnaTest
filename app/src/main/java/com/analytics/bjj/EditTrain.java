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
import com.analytics.bjj.db.TrainDataSource;

public class EditTrain extends ActionBarActivity {

    private static MyTextView tv;
    private static MyTextView tvatt;
    private static MyTextView tvsuc;
    private static MyEditText et;
    private static Button button_minus;
    private static Button button_plus;

    private static TrainDataSource dataSource; // should this be static??????
    private static Train train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_train);

        //database related
        dataSource = new TrainDataSource(this);
        dataSource.open();

        //Get data from the tourn object
        Bundle data = getIntent().getExtras();
        train = data.getParcelable("train_obj_edit");

        tv = (MyTextView) findViewById(R.id.sweepAttempted_edit_train);
        tv.setText(Integer.toString(train.getSweepAttempted()));

        tv = (MyTextView) findViewById(R.id.sweepSuccessful_edit_train);
        tv.setText(Integer.toString(train.getSweepSuccessful()));

        tv = (MyTextView) findViewById(R.id.passAtt_edit_train);
        tv.setText(Integer.toString(train.getPassAttempted()));

        tv = (MyTextView) findViewById(R.id.passSuc_edit_train);
        tv.setText(Integer.toString(train.getPassSuccessful()));

        tv = (MyTextView) findViewById(R.id.tdAtt_edit_train);
        tv.setText(Integer.toString(train.getTdAttempted()));

        tv = (MyTextView) findViewById(R.id.tdSuc_edit_train);
        tv.setText(Integer.toString(train.getTdSuccessful()));

        tv = (MyTextView) findViewById(R.id.subAtt_edit_train);
        tv.setText(Integer.toString(train.getSubAttempted()));

        et = (MyEditText) findViewById(R.id.matchTimeMinEV_edit_train);
        int tempMin = (int) Math.floor(train.getMatchTime());
        int tempSeconds = (int) Math.round((train.getMatchTime() - tempMin)*60.0);
        et.setText(Integer.toString(tempMin));

        et = (MyEditText) findViewById(R.id.matchTimeSecEV_edit_train);
        if (tempSeconds<10) {
            et.setText("0" + Integer.toString(tempSeconds));
        } else et.setText(Integer.toString(tempSeconds));

//        set a generic plus and minus click listener object and use that for all the setOnClickListener methods??????

        button_minus = (Button) findViewById(R.id.swpAttMinus_edit_train);
        button_plus = (Button) findViewById(R.id.swpsAttPlus_edit_train);
        tvatt = (MyTextView) findViewById(R.id.sweepAttempted_edit_train);
        PlusOnClickListener swpAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(swpAttPlusListener);

        MinusOnClickListener swpAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(swpAttMinusListener);

        button_minus = (Button) findViewById(R.id.swpSucMinus_edit_train);
        button_plus = (Button) findViewById(R.id.swpsSucPlus_edit_train);
        tvatt = (MyTextView) findViewById(R.id.sweepAttempted_edit_train);
        tvsuc = (MyTextView) findViewById(R.id.sweepSuccessful_edit_train);
        PlusOnClickListener swpSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(swpSucPlusListener);

        MinusOnClickListener swpSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(swpSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.passAttMinus_edit_train);
        button_plus = (Button) findViewById(R.id.passAttPlus_edit_train);
        tvatt = (MyTextView) findViewById(R.id.passAtt_edit_train);
        PlusOnClickListener passAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(passAttPlusListener);

        MinusOnClickListener passAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(passAttMinusListener);

        button_minus = (Button) findViewById(R.id.passSucMinus_edit_train);
        button_plus = (Button) findViewById(R.id.passSucPlus_edit_train);
        tvatt = (MyTextView) findViewById(R.id.passAtt_edit_train);
        tvsuc = (MyTextView) findViewById(R.id.passSuc_edit_train);
        PlusOnClickListener passSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(passSucPlusListener);

        MinusOnClickListener passSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(passSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.tdAttMinus_edit_train);
        button_plus = (Button) findViewById(R.id.tdAttPlus_edit_train);
        tvatt = (MyTextView) findViewById(R.id.tdAtt_edit_train);
        PlusOnClickListener tdAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(tdAttPlusListener);

        MinusOnClickListener tdAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(tdAttMinusListener);

        button_minus = (Button) findViewById(R.id.tdSucMinus_edit_train);
        button_plus = (Button) findViewById(R.id.tdSucPlus_edit_train);
        tvatt = (MyTextView) findViewById(R.id.tdAtt_edit_train);
        tvsuc = (MyTextView) findViewById(R.id.tdSuc_edit_train);
        PlusOnClickListener tdSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(tdSucPlusListener);

        MinusOnClickListener tdSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(tdSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.subAttMinus_edit_train);
        button_plus = (Button) findViewById(R.id.subAttPlus_edit_train);
        tvatt = (MyTextView) findViewById(R.id.subAtt_edit_train);
        PlusOnClickListener subAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(subAttPlusListener);

        MinusOnClickListener subAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(subAttMinusListener);
        //////



        button_minus = (Button) findViewById(R.id.subSucMinus_edit_train);
        button_plus = (Button) findViewById(R.id.subSucPlus_edit_train);
        tvatt = (MyTextView) findViewById(R.id.subAtt_edit_train);
        tvsuc = (MyTextView) findViewById(R.id.subSuc_edit_train);
        PlusOnClickListener subSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(subSucPlusListener);

        MinusOnClickListener subSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(subSucMinusListener);
        //////

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_training, menu);
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

    public void saveTrain_edit(View view){
        boolean sensibleData = true;
        try {

            tv = (MyTextView) findViewById(R.id.sweepAttempted_edit_train);
            train.setSweepAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.sweepSuccessful_edit_train);
            train.setSweepSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.passAtt_edit_train);
            train.setPassAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.passSuc_edit_train);
            train.setPassSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.tdAtt_edit_train);
            train.setTdAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.tdSuc_edit_train);
            train.setTdSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.subAtt_edit_train);
            train.setSubAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.subSuc_edit_train);
            train.setSubSuccessful(Integer.parseInt(tv.getText().toString()));
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in EditTrain Activity (metric input): "
                    +  ex.getMessage());
        }

        //Set the match time
        et = (MyEditText) findViewById(R.id.matchTimeMinEV_edit_train);
        double minutes = 0.0;
        try {
            minutes = Double.parseDouble(et.getText().toString());
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTrain Activity (minutes): "
                    +  ex.getMessage());
        }

        et = (MyEditText) findViewById(R.id.matchTimeSecEV_edit_train);

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

        train.setMatchTime(ttime);

        //Perform tournament data checks
        if (train.getPassAttempted()<train.getPassSuccessful()) {
            sensibleData=false;
            Toast.makeText(this, "Not enough pass attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (train.getTdAttempted()<train.getTdSuccessful()) {
            sensibleData=false;
            Toast.makeText(this, "Not enough TD attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (train.getSweepAttempted()<train.getSweepSuccessful()) {
            sensibleData=false;
            Toast.makeText(this, "Not enough sweep attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (train.getSubAttempted()<train.getSubSuccessful()) {
            sensibleData=false;
            Toast.makeText(this, "Not enough submission attempts.",
                    Toast.LENGTH_SHORT).show();
        }

        if (sensibleData) {
            dataSource.update(train);
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
