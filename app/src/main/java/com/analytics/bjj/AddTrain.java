package com.analytics.bjj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.analytics.bjj.db.TrainDataSource;


public class AddTrain extends ActionBarActivity {

    private static MyTextView tv;
    private static MyTextView tvatt;
    private static MyTextView tvsuc;
    private static MyEditText et;
    private static Button button_minus;
    private static Button button_plus;

    private static String[] oppBeltSpinnerStrArray;
    private static String[] beltSpinnerStrArray;
    private static final int MAX_TRAIN_DATABASE_ROWS = 49;
    private static TrainDataSource dataSource; // should this be static??????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_train);

        //database related
        dataSource = new TrainDataSource(this);
        dataSource.open();


        button_minus = (Button) findViewById(R.id.swpAttMinus_train);
        button_plus = (Button) findViewById(R.id.swpsAttPlus_train);
        tvatt = (MyTextView) findViewById(R.id.sweepAttempted_train);
        PlusOnClickListener swpAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(swpAttPlusListener);

        MinusOnClickListener swpAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(swpAttMinusListener);

        button_minus = (Button) findViewById(R.id.swpSucMinus_train);
        button_plus = (Button) findViewById(R.id.swpsSucPlus_train);
        tvatt = (MyTextView) findViewById(R.id.sweepAttempted_train);
        tvsuc = (MyTextView) findViewById(R.id.sweepSuccessful_train);
        PlusOnClickListener swpSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(swpSucPlusListener);

        MinusOnClickListener swpSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(swpSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.passAttMinus_train);
        button_plus = (Button) findViewById(R.id.passAttPlus_train);
        tvatt = (MyTextView) findViewById(R.id.passAtt_train);
        PlusOnClickListener passAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(passAttPlusListener);

        MinusOnClickListener passAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(passAttMinusListener);

        button_minus = (Button) findViewById(R.id.passSucMinus_train);
        button_plus = (Button) findViewById(R.id.passSucPlus_train);
        tvatt = (MyTextView) findViewById(R.id.passAtt_train);
        tvsuc = (MyTextView) findViewById(R.id.passSuc_train);
        PlusOnClickListener passSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(passSucPlusListener);

        MinusOnClickListener passSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(passSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.tdAttMinus_train);
        button_plus = (Button) findViewById(R.id.tdAttPlus_train);
        tvatt = (MyTextView) findViewById(R.id.tdAtt_train);
        PlusOnClickListener tdAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(tdAttPlusListener);

        MinusOnClickListener tdAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(tdAttMinusListener);

        button_minus = (Button) findViewById(R.id.tdSucMinus_train);
        button_plus = (Button) findViewById(R.id.tdSucPlus_train);
        tvatt = (MyTextView) findViewById(R.id.tdAtt_train);
        tvsuc = (MyTextView) findViewById(R.id.tdSuc_train);
        PlusOnClickListener tdSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(tdSucPlusListener);

        MinusOnClickListener tdSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(tdSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.subAttMinus_train);
        button_plus = (Button) findViewById(R.id.subAttPlus_train);
        tvatt = (MyTextView) findViewById(R.id.subAtt_train);
        PlusOnClickListener subAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(subAttPlusListener);

        MinusOnClickListener subAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(subAttMinusListener);
        //////


        button_minus = (Button) findViewById(R.id.subSucMinus_train);
        button_plus = (Button) findViewById(R.id.subSucPlus_train);
        tvatt = (MyTextView) findViewById(R.id.subAtt_train);
        tvsuc = (MyTextView) findViewById(R.id.subSuc_train);
        PlusOnClickListener subSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(subSucPlusListener);

        MinusOnClickListener subSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(subSucMinusListener);
        //////


        this.beltSpinnerStrArray = new String[] {"White","Blue","Purple","Brown","Black"};

        this.oppBeltSpinnerStrArray = new String[] {"White","Blue","Purple","Brown","Black"};

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

    public void saveTrain(View view) {
        final Train train = new Train();
        boolean sensibleData = true;
        try {

            tv = (MyTextView) findViewById(R.id.sweepAttempted_train);
            train.setSweepAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.sweepSuccessful_train);
            train.setSweepSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.passAtt_train);
            train.setPassAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.passSuc_train);
            train.setPassSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.tdAtt_train);
            train.setTdAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.tdSuc_train);
            train.setTdSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.subAtt_train);
            train.setSubAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.subSuc_train);
            train.setSubSuccessful(Integer.parseInt(tv.getText().toString()));

        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTrainy Activity (metric input): "
                    + ex.getMessage());
        }



        //Set the match time
        et = (MyEditText) findViewById(R.id.matchTimeMinEV_train);
        double minutes = 0.0;
        String tempStr="0";
        try {
            tempStr = et.getText().toString();
            if (tempStr.equals("") || tempStr.equals(".") || tempStr.equals(",")) {
                tempStr = "0";
            }
            minutes = Double.parseDouble(tempStr);
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTrain Activity (minutes): "
                    + ex.getMessage());
        }

        et = (MyEditText) findViewById(R.id.matchTimeSecEV_train);

        double seconds = 0.0;
        tempStr="0";
        try {
            tempStr = et.getText().toString();
            if (tempStr.equals("") || tempStr.equals(".") || tempStr.equals(",")) {
                tempStr = "0";
            }
            seconds = Double.parseDouble(tempStr);
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTrain Activity (seconds): "
                    + ex.getMessage());
        }
        if (seconds > 60.0) {
            seconds = 60.0;
        }
        double sec2min = seconds / 60.0;
        double ttime = Math.round((minutes+sec2min)*100)/100.0;
        train.setMatchTime(ttime);

        //setting up alert dialog
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.alert_dialog_train, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set alert_dialog_train.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText trainNameET = (EditText) promptsView.findViewById(R.id.alertDialog_TrainName); //these must be final to go into the alert dialog

        final Spinner oppBelt_Spinner = (Spinner) promptsView.findViewById(R.id.alertDialog_OppBelt_train);

        final Spinner belt_Spinner = (Spinner) promptsView.findViewById(R.id.alertDialog_Belt_train);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, oppBeltSpinnerStrArray);
        oppBelt_Spinner.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, beltSpinnerStrArray);
        belt_Spinner.setAdapter(adapter);

        //Perform training data checks
        if (train.getPassAttempted() < train.getPassSuccessful()) {
            sensibleData = false;
            Toast.makeText(this, "Not enough pass attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (train.getTdAttempted() < train.getTdSuccessful()) {
            sensibleData = false;
            Toast.makeText(this, "Not enough TD attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (train.getSweepAttempted() < train.getSweepSuccessful()) {
            sensibleData = false;
            Toast.makeText(this, "Not enough sweep attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (train.getSubAttempted() < train.getSubSuccessful()) {
            sensibleData = false;
            Toast.makeText(this, "Not enough submission attempts.",
                    Toast.LENGTH_SHORT).show();
        }


        // set alert dialog message
        if (dataSource.getTrainLen() <= MAX_TRAIN_DATABASE_ROWS) {
            if (sensibleData) {
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //Set the sparring session name
                                        String tempTrainName = trainNameET.getText().toString();
                                        if (tempTrainName != null && !tempTrainName.isEmpty()) {
                                            train.setTrainName(tempTrainName);
                                        } else train.setTrainName("Sparring Session");

                                        //Set the sparring session belt class
                                        int beltPos = 0;
                                        beltPos = belt_Spinner.getSelectedItemPosition();
                                        if (beltPos < beltSpinnerStrArray.length && beltPos >= 0) {
                                            train.setBelt(beltSpinnerStrArray[beltPos]);
                                        } else {
                                            train.setBelt("White");
                                        }

                                        //Set the sparring session belt class
                                        int oppBeltPos = 0;
                                        oppBeltPos = oppBelt_Spinner.getSelectedItemPosition();
                                        if (oppBeltPos < oppBeltSpinnerStrArray.length && oppBeltPos >= 0) {
                                            train.setOppBelt(oppBeltSpinnerStrArray[oppBeltPos]);
                                        } else {
                                            train.setOppBelt("White");
                                        }


                                        dataSource.create(train);
                                        finish();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }

        } else {
            Toast.makeText(this, "Sparring session limit reached.",
                    Toast.LENGTH_SHORT).show();
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
