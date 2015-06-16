package example.org.bjjanatest;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class ViewTourn extends ActionBarActivity {

    private Tourn tourn;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tourn);

        Bundle data = getIntent().getExtras();
        tourn = data.getParcelable("tourn_obj");

        TextView tv;
        tv = (TextView) findViewById(R.id.view_pointsScored);
        tv.setText(String.valueOf(tourn.getPointsScored()));

        tv = (TextView) findViewById(R.id.view_pointsAllowed);
        tv.setText(String.valueOf(tourn.getPointsAllowed()));

        tv = (TextView) findViewById(R.id.view_sweepAttempted);
        tv.setText(String.valueOf(tourn.getSweepAttempted()));

        tv = (TextView) findViewById(R.id.view_sweepSuccessful);
        tv.setText(String.valueOf(tourn.getSweepSuccessful()));

        tv = (TextView) findViewById(R.id.view_passAtt);
        tv.setText(String.valueOf(tourn.getPassAttempted()));

        tv = (TextView) findViewById(R.id.view_passSuc);
        tv.setText(String.valueOf(tourn.getPassSuccessful()));

        tv = (TextView) findViewById(R.id.view_tdAtt);
        tv.setText(String.valueOf(tourn.getTdAttempted()));

        tv = (TextView) findViewById(R.id.view_tdSuc);
        tv.setText(String.valueOf(tourn.getTdSuccessful()));

        tv = (TextView) findViewById(R.id.view_matchTime);
        tv.setText(String.valueOf(tourn.getMatchTime()));
    }

    public void finishViewTourn(View view) {
        finish();
    }
}
