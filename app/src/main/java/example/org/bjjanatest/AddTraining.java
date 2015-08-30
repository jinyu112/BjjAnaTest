package example.org.bjjanatest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import example.org.bjjanatest.db.TournDataSource;
import example.org.bjjanatest.db.TrainDataSource;

public class AddTraining extends ActionBarActivity {

    private static TextView tv;
    private static TextView tvatt;
    private static TextView tvsuc;
    private static EditText et;
    private static Button button_minus;
    private static Button button_plus;
    private static final String LOGTAG = "BJJTRAINING";
    private TrainDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        //database related
        dataSource = new TrainDataSource(this);
        dataSource.open();

//        set a generic plus and minus click listener object and use that for all the setOnClickListener methods??????

        button_minus = (Button) findViewById(R.id.ptsScoredMinus_train);
        button_plus = (Button) findViewById(R.id.ptsScoredPlus_train);
        tv = (TextView) findViewById(R.id.pointsScored_train);
        PlusOnClickListener ptsScoredPlusListener = new PlusOnClickListener(tv);
        button_plus.setOnClickListener(ptsScoredPlusListener);

        MinusOnClickListener ptsScoredMinusListener = new MinusOnClickListener(tv);
        button_minus.setOnClickListener(ptsScoredMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.ptsAllowedMinus_train);
        button_plus = (Button) findViewById(R.id.ptsAllowedPlus_train);
        tv = (TextView) findViewById(R.id.pointsAllowed_train);
        PlusOnClickListener ptsAllowedPlusListener = new PlusOnClickListener(tv);
        button_plus.setOnClickListener(ptsAllowedPlusListener);

        MinusOnClickListener ptsAllowedMinusListener = new MinusOnClickListener(tv);
        button_minus.setOnClickListener(ptsAllowedMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.swpAttMinus_train);
        button_plus = (Button) findViewById(R.id.swpsAttPlus_train);
        tvatt = (TextView) findViewById(R.id.sweepAttempted_train);
        PlusOnClickListener swpAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(swpAttPlusListener);

        MinusOnClickListener swpAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(swpAttMinusListener);

        button_minus = (Button) findViewById(R.id.swpSucMinus_train);
        button_plus = (Button) findViewById(R.id.swpsSucPlus_train);
        tvatt = (TextView) findViewById(R.id.sweepAttempted_train);
        tvsuc = (TextView) findViewById(R.id.sweepSuccessful_train);
        PlusOnClickListener swpSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(swpSucPlusListener);

        MinusOnClickListener swpSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(swpSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.passAttMinus_train);
        button_plus = (Button) findViewById(R.id.passAttPlus_train);
        tvatt = (TextView) findViewById(R.id.passAtt_train);
        PlusOnClickListener passAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(passAttPlusListener);

        MinusOnClickListener passAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(passAttMinusListener);

        button_minus = (Button) findViewById(R.id.passSucMinus_train);
        button_plus = (Button) findViewById(R.id.passSucPlus_train);
        tvatt = (TextView) findViewById(R.id.passAtt_train);
        tvsuc = (TextView) findViewById(R.id.passSuc_train);
        PlusOnClickListener passSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(passSucPlusListener);

        MinusOnClickListener passSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(passSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.tdAttMinus_train);
        button_plus = (Button) findViewById(R.id.tdAttPlus_train);
        tvatt = (TextView) findViewById(R.id.tdAtt_train);
        PlusOnClickListener tdAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(tdAttPlusListener);

        MinusOnClickListener tdAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(tdAttMinusListener);

        button_minus = (Button) findViewById(R.id.tdSucMinus_train);
        button_plus = (Button) findViewById(R.id.tdSucPlus_train);
        tvatt = (TextView) findViewById(R.id.tdAtt_train);
        tvsuc = (TextView) findViewById(R.id.tdSuc_train);
        PlusOnClickListener tdSucPlusListener = new PlusOnClickListener(tvatt,tvsuc);
        button_plus.setOnClickListener(tdSucPlusListener);

        MinusOnClickListener tdSucMinusListener = new MinusOnClickListener(tvatt,tvsuc);
        button_minus.setOnClickListener(tdSucMinusListener);
        //////

        button_minus = (Button) findViewById(R.id.subAttMinus_train);
        button_plus = (Button) findViewById(R.id.subAttPlus_train);
        tvatt = (TextView) findViewById(R.id.subAtt_train);
        PlusOnClickListener subAttPlusListener = new PlusOnClickListener(tvatt);
        button_plus.setOnClickListener(subAttPlusListener);

        MinusOnClickListener subAttMinusListener = new MinusOnClickListener(tvatt);
        button_minus.setOnClickListener(subAttMinusListener);

        button_minus = (Button) findViewById(R.id.subSucMinus_train);
        button_plus = (Button) findViewById(R.id.subSucPlus_train);
        tvatt = (TextView) findViewById(R.id.subAtt_train);
        tvsuc = (TextView) findViewById(R.id.subSuc_train);
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

    public void saveTraining(View view){
        Train train = new Train();
        tv = (TextView) findViewById(R.id.pointsScored_train);
        train.setPointsScored(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.pointsAllowed_train);
        train.setPointsAllowed(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.sweepAttempted_train);
        train.setSweepAttempted(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.sweepSuccessful_train);
        train.setSweepSuccessful(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.passAtt_train);
        train.setPassAttempted(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.passSuc_train);
        train.setPassSuccessful(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.tdAtt_train);
        train.setTdAttempted(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.tdSuc_train);
        train.setTdSuccessful(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.subAtt_train);
        train.setSubAttempted(Integer.parseInt(tv.getText().toString()));

        tv = (TextView) findViewById(R.id.subSuc_train);
        train.setSubAttempted(Integer.parseInt(tv.getText().toString()));

        //!!!!!!!!!!!!!!!move match time, name, belt, inputs to a popup alert before saving
        //!!!!!!!!!!!!!!also provide safeguard for empty fields
        et = (EditText) findViewById(R.id.matchTimeMinEV_train);
        double minutes = Double.parseDouble(et.getText().toString());

        et = (EditText) findViewById(R.id.matchTimeSecEV_train);
        double seconds = Double.parseDouble(et.getText().toString());
        if (seconds > 60.0) {
            seconds = 60.0;
        }
        double sec2min = seconds/60.0;

        train.setMatchTime(minutes+sec2min);

        et = (EditText) findViewById(R.id.trainNameEV);
        train.setTrainName(et.getText().toString());

        train = dataSource.create(train);
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
