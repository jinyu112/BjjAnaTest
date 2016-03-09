package com.analytics.bjj;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.analytics.bjj.db.TournDataSource;

public class ViewTourn extends ActionBarActivity {

    private Tourn tourn;
    private TournDataSource dataSource;


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tourn);

        dataSource = new TournDataSource(this);
        dataSource.open();

        Bundle data = getIntent().getExtras();
        tourn = data.getParcelable("tourn_obj");

        MyTextView tv;
        tv = (MyTextView) findViewById(R.id.view_pointsScored);
        tv.setText(String.valueOf(tourn.getPointsScored()));

        tv = (MyTextView) findViewById(R.id.view_pointsAllowed);
        tv.setText(String.valueOf(tourn.getPointsAllowed()));

        tv = (MyTextView) findViewById(R.id.view_sweepAttempted);
        tv.setText(String.valueOf(tourn.getSweepAttempted()));

        tv = (MyTextView) findViewById(R.id.view_sweepSuccessful);
        tv.setText(String.valueOf(tourn.getSweepSuccessful()));

        tv = (MyTextView) findViewById(R.id.view_passAtt);
        tv.setText(String.valueOf(tourn.getPassAttempted()));

        tv = (MyTextView) findViewById(R.id.view_passSuc);
        tv.setText(String.valueOf(tourn.getPassSuccessful()));

        tv = (MyTextView) findViewById(R.id.view_tdAtt);
        tv.setText(String.valueOf(tourn.getTdAttempted()));

        tv = (MyTextView) findViewById(R.id.view_tdSuc);
        tv.setText(String.valueOf(tourn.getTdSuccessful()));

        tv = (MyTextView) findViewById(R.id.view_matchTime);
        tv.setText(String.valueOf(tourn.getMatchTime()));

        tv = (MyTextView) findViewById(R.id.view_weightClass);

        if (tourn.getWeightClass()==0)
        {
            tv.setText("127 lbs and below");
        }
        else if (tourn.getWeightClass()==1) {
            tv.setText("127-141.5 lbs");
        }
        else if (tourn.getWeightClass()==2) {
            tv.setText("141.5-154.5 lbs");
        }
        else if (tourn.getWeightClass()==3) {
            tv.setText("154.5-168 lbs");
        }
        else if (tourn.getWeightClass()==4) {
            tv.setText("168-181.5 lbs");
        }
        else if (tourn.getWeightClass()==5) {
            tv.setText("181.5-195 lbs");
        }
        else if (tourn.getWeightClass()==6) {
            tv.setText("195-208 lbs");
        }
        else if (tourn.getWeightClass()==7) {
            tv.setText("208-222 lbs");
        }
        else if (tourn.getWeightClass()==8) {
            tv.setText("222+ lbs");
        }
        else if (tourn.getWeightClass()==9) {
            tv.setText("Open Light");
        }
        else if (tourn.getWeightClass()==10) {
            tv.setText("Open Heavy");
        }
        else tv.setText("127 lbs and below");

    }

    public void finishViewTourn(View view) {
        finish();
    }

    public void editTourn (View view) {
        Intent intent = new Intent(this,EditTourn.class);
        intent.putExtra("tourn_obj_edit",tourn);
        startActivity(intent);
        dataSource.close();
        finish();
    }

    public void deleteTourn(View view) {
        dataSource.removeFromTourns(tourn);
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
