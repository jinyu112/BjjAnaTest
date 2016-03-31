package com.analytics.bjj;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.analytics.bjj.db.TournDataSource;
import com.analytics.bjj.db.TrainDataSource;

public class ViewTrain extends ActionBarActivity {

    private Train train;
    private TrainDataSource dataSource;


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_train);

        dataSource = new TrainDataSource(this);
        dataSource.open();

        Bundle data = getIntent().getExtras();
        train = data.getParcelable("train_obj");

        MyTextView tv;

        tv = (MyTextView) findViewById(R.id.view_sweepAttempted_train);
        tv.setText(String.valueOf(train.getSweepAttempted()));

        tv = (MyTextView) findViewById(R.id.view_sweepSuccessful_train);
        tv.setText(String.valueOf(train.getSweepSuccessful()));

        tv = (MyTextView) findViewById(R.id.view_passAtt_train);
        tv.setText(String.valueOf(train.getPassAttempted()));

        tv = (MyTextView) findViewById(R.id.view_passSuc_train);
        tv.setText(String.valueOf(train.getPassSuccessful()));

        tv = (MyTextView) findViewById(R.id.view_tdAtt_train);
        tv.setText(String.valueOf(train.getTdAttempted()));

        tv = (MyTextView) findViewById(R.id.view_tdSuc_train);
        tv.setText(String.valueOf(train.getTdSuccessful()));

        tv = (MyTextView) findViewById(R.id.view_matchTime_train);
        tv.setText(String.valueOf(train.getMatchTime()));

    }

    public void finishViewTrain(View view) {
        finish();
    }

    public void editTrain (View view) {
        Intent intent = new Intent(this,EditTrain.class);
        intent.putExtra("train_obj_edit",train);
        startActivity(intent);
        dataSource.close();
        finish();
    }

    public void deleteTrain(View view) {
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
