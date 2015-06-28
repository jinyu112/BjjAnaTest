package example.org.bjjanatest;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import example.org.bjjanatest.db.TrainDataSource;

public class ViewTrain extends ActionBarActivity {

    private Train train;
    private TrainDataSource dataSource;
    private static final String LOGTAG ="BJJTRAINING";

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_train);

        dataSource = new TrainDataSource(this);
        dataSource.open();

        Bundle data = getIntent().getExtras();
        train = data.getParcelable("train_obj");

        TextView tv;
        tv = (TextView) findViewById(R.id.view_pointsScored_train);
        tv.setText(String.valueOf(train.getPointsScored()));

        tv = (TextView) findViewById(R.id.view_pointsAllowed_train);
        tv.setText(String.valueOf(train.getPointsAllowed()));

        tv = (TextView) findViewById(R.id.view_sweepAttempted_train);
        tv.setText(String.valueOf(train.getSweepAttempted()));

        tv = (TextView) findViewById(R.id.view_sweepSuccessful_train);
        tv.setText(String.valueOf(train.getSweepSuccessful()));

        tv = (TextView) findViewById(R.id.view_passAtt_train);
        tv.setText(String.valueOf(train.getPassAttempted()));

        tv = (TextView) findViewById(R.id.view_passSuc_train);
        tv.setText(String.valueOf(train.getPassSuccessful()));

        tv = (TextView) findViewById(R.id.view_tdAtt_train);
        tv.setText(String.valueOf(train.getTdAttempted()));

        tv = (TextView) findViewById(R.id.view_tdSuc_train);
        tv.setText(String.valueOf(train.getTdSuccessful()));

        tv = (TextView) findViewById(R.id.view_numBackTakes_train);
        tv.setText(String.valueOf(train.getNumBackTakes()));

        tv = (TextView) findViewById(R.id.view_numMounts_train);
        tv.setText(String.valueOf(train.getNumMounts()));

        tv = (TextView) findViewById(R.id.view_matchTime_train);
        tv.setText(String.valueOf(train.getMatchTime()));
    }

    public void finishViewTourn(View view) {
        finish();
    }

    public void deleteTourn(View view) {
        dataSource.removeFromTrains(train);
        dataSource.close();
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


