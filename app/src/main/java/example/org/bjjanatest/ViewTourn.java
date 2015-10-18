package example.org.bjjanatest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import example.org.bjjanatest.db.TournDataSource;

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
