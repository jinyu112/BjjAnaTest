package example.org.bjjanatest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import example.org.bjjanatest.db.TournDataSource;


public class AddTourney extends ActionBarActivity {

    private static MyTextView tv;
    private static MyTextView tvatt;
    private static MyTextView tvsuc;
    private static MyEditText et;
    private static Button button_minus;
    private static Button button_plus;

    private static String[] weightSpinnerStrArray;
    private static String[] beltSpinnerStrArray;
    private static final int MAX_TOURN_DATABASE_ROWS = 49;
    private static TournDataSource dataSource; // should this be static??????

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
        tv = (MyTextView) findViewById(R.id.pointsScored);
        PlusOnClickListener ptsScoredPlusListener = new PlusOnClickListener(tv);
        button_plus.setOnClickListener(ptsScoredPlusListener);

        MinusOnClickListener ptsScoredMinusListener = new MinusOnClickListener(tv);
        button_minus.setOnClickListener(ptsScoredMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.ptsAllowedMinus);
        button_plus = (Button) findViewById(R.id.ptsAllowedPlus);
        tv = (MyTextView) findViewById(R.id.pointsAllowed);
        PlusOnClickListener ptsAllowedPlusListener = new PlusOnClickListener(tv);
        button_plus.setOnClickListener(ptsAllowedPlusListener);

        MinusOnClickListener ptsAllowedMinusListener = new MinusOnClickListener(tv);
        button_minus.setOnClickListener(ptsAllowedMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.swpAttMinus);
        button_plus = (Button) findViewById(R.id.swpsAttPlus);
        tvatt = (MyTextView) findViewById(R.id.sweepAttempted);
        PlusOnClickListener swpAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(swpAttPlusListener);

        MinusOnClickListener swpAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(swpAttMinusListener);

        button_minus = (Button) findViewById(R.id.swpSucMinus);
        button_plus = (Button) findViewById(R.id.swpsSucPlus);
        tvatt = (MyTextView) findViewById(R.id.sweepAttempted);
        tvsuc = (MyTextView) findViewById(R.id.sweepSuccessful);
        PlusOnClickListener swpSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(swpSucPlusListener);

        MinusOnClickListener swpSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(swpSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.passAttMinus);
        button_plus = (Button) findViewById(R.id.passAttPlus);
        tvatt = (MyTextView) findViewById(R.id.passAtt);
        PlusOnClickListener passAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(passAttPlusListener);

        MinusOnClickListener passAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(passAttMinusListener);

        button_minus = (Button) findViewById(R.id.passSucMinus);
        button_plus = (Button) findViewById(R.id.passSucPlus);
        tvatt = (MyTextView) findViewById(R.id.passAtt);
        tvsuc = (MyTextView) findViewById(R.id.passSuc);
        PlusOnClickListener passSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(passSucPlusListener);

        MinusOnClickListener passSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(passSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.tdAttMinus);
        button_plus = (Button) findViewById(R.id.tdAttPlus);
        tvatt = (MyTextView) findViewById(R.id.tdAtt);
        PlusOnClickListener tdAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(tdAttPlusListener);

        MinusOnClickListener tdAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(tdAttMinusListener);

        button_minus = (Button) findViewById(R.id.tdSucMinus);
        button_plus = (Button) findViewById(R.id.tdSucPlus);
        tvatt = (MyTextView) findViewById(R.id.tdAtt);
        tvsuc = (MyTextView) findViewById(R.id.tdSuc);
        PlusOnClickListener tdSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(tdSucPlusListener);

        MinusOnClickListener tdSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(tdSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.subAttMinus);
        button_plus = (Button) findViewById(R.id.subAttPlus);
        tvatt = (MyTextView) findViewById(R.id.subAtt);
        PlusOnClickListener subAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(subAttPlusListener);

        MinusOnClickListener subAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(subAttMinusListener);
        //////

        this.weightSpinnerStrArray = new String[] {"127 and below", "127-141.5 lbs", "141.5-154.5 lbs", "154.5-168 lbs", "168-181.5 lbs", "181.5-195 lbs", "195-208 lbs","208-222 lbs","222+ lbs","Open Light","Open Heavy"};

        this.beltSpinnerStrArray = new String[] {"White","Blue","Purple","Brown","Black","NoGi Novice", "NoGi Intermed.","NoGi Adv."};

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

    public void saveTournament(View view) {
        final Tourn tourn = new Tourn();
        boolean sensibleData = true;
        try {
            tv = (MyTextView) findViewById(R.id.pointsScored);
            tourn.setPointsScored(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.pointsAllowed);
            tourn.setPointsAllowed(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.sweepAttempted);
            tourn.setSweepAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.sweepSuccessful);
            tourn.setSweepSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.passAtt);
            tourn.setPassAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.passSuc);
            tourn.setPassSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.tdAtt);
            tourn.setTdAttempted(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.tdSuc);
            tourn.setTdSuccessful(Integer.parseInt(tv.getText().toString()));

            tv = (MyTextView) findViewById(R.id.subAtt);
            tourn.setSubAttempted(Integer.parseInt(tv.getText().toString()));
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTourney Activity (metric input): "
                    + ex.getMessage());
        }

        CheckBox cbWin = (CheckBox) findViewById(R.id.winCheck);
        CheckBox cbSub = (CheckBox) findViewById(R.id.subCheck);

        // if the submission checkbox is checked
        if (cbSub.isChecked()) {
            tourn.setSubSuccessful(1);
            cbWin.setChecked(true);
        } else {
            tourn.setSubSuccessful(0);
        }

        // if the wind checkbox is checked
        if (cbWin.isChecked()) {
            tourn.setWin(1);
        } else {
            tourn.setWin(0);
        }

        //Determine match outcome by points
        if (tourn.getPointsAllowed() < tourn.getPointsScored()) {
            tourn.setWin(1);
        } else if (tourn.getPointsAllowed() == tourn.getPointsScored()) {
            if (cbWin.isChecked()) tourn.setWin(1);
            else tourn.setWin(0);
        } else {
            tourn.setWin(0);
        }

        //Set the match time
        et = (MyEditText) findViewById(R.id.matchTimeMinEV);
        double minutes = 0.0;
        try {
            minutes = Double.parseDouble(et.getText().toString());
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTourney Activity (minutes): "
                    + ex.getMessage());
        }

        et = (MyEditText) findViewById(R.id.matchTimeSecEV);

        double seconds = 0.0;
        try {
            seconds = Double.parseDouble(et.getText().toString());
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTourney Activity (seconds): "
                    + ex.getMessage());
        }
        if (seconds > 60.0) {
            seconds = 60.0;
        }
        double sec2min = seconds / 60.0;

        tourn.setMatchTime(minutes + sec2min);

        //setting up alert dialog
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.alert_dialog_tourn, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set alert_dialog_tourn.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText tournNameET = (EditText) promptsView.findViewById(R.id.alertDialog_TournName); //these must be final to go into the alert dialog

        final Spinner weightClass_Spinner = (Spinner) promptsView.findViewById(R.id.alertDialog_WeightClass);

        final Spinner belt_Spinner = (Spinner) promptsView.findViewById(R.id.alertDialog_Belt);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, weightSpinnerStrArray);
        weightClass_Spinner.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, beltSpinnerStrArray);
        belt_Spinner.setAdapter(adapter);

        //Perform tournament data checks
        if (tourn.getPassAttempted() < tourn.getPassSuccessful()) {
            sensibleData = false;
            Toast.makeText(this, "Not enough pass attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (tourn.getTdAttempted() < tourn.getTdSuccessful()) {
            sensibleData = false;
            Toast.makeText(this, "Not enough TD attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (tourn.getSweepAttempted() < tourn.getSweepSuccessful()) {
            sensibleData = false;
            Toast.makeText(this, "Not enough sweep attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (tourn.getSubAttempted() < tourn.getSubSuccessful()) {
            sensibleData = false;
            Toast.makeText(this, "Not enough submission attempts.",
                    Toast.LENGTH_SHORT).show();
        }
        if (tourn.getPointsScored() == 0 && tourn.getWin() == 1 && tourn.getSubSuccessful() != 1) {
            sensibleData = false;
            Toast.makeText(this, "Incorrect input data.",
                    Toast.LENGTH_SHORT).show();
        }


        // set alert dialog message
        if (dataSource.getTournLen() <= MAX_TOURN_DATABASE_ROWS) {
            if (sensibleData) {
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        //Set the tournament match name
                                        String tempTournName = tournNameET.getText().toString();
                                        if (tempTournName != null && !tempTournName.isEmpty()) {
                                            tourn.setTournName(tempTournName);
                                        } else tourn.setTournName("Match Name");

                                        //Set the tournament match weight class
                                        int weightClassPos = 0;
                                        weightClassPos = weightClass_Spinner.getSelectedItemPosition();
                                        tourn.setWeightClass(weightClass_Spinner.getSelectedItemPosition());
                                        if (weightClassPos < weightSpinnerStrArray.length && weightClassPos >= 0) {
                                            tourn.setWeightClass(weightClassPos);
                                        } else tourn.setWeightClass(0);

                                        //Set the tournament match belt class
                                        int beltPos = 0;
                                        beltPos = belt_Spinner.getSelectedItemPosition();
                                        if (beltPos < beltSpinnerStrArray.length && beltPos >= 0) {
                                            tourn.setBelt(beltSpinnerStrArray[beltPos]);
                                        } else {
                                            tourn.setBelt("White");
                                        }

                                        dataSource.create(tourn);
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
            Toast.makeText(this, "Tournament match limit reached.",
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
