package example.org.bjjanatest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import junit.framework.Test;

import example.org.bjjanatest.db.TechDataSource;


public class ViewTech extends ActionBarActivity {

    private Tech tech;
    private TechDataSource dataSource;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tech);
        dataSource = new TechDataSource(this);
        dataSource.open();

        Bundle data = getIntent().getExtras();
        tech = data.getParcelable("tech_obj");

        TextView tv = (TextView) findViewById(R.id.view_techName);
        tv.setText(tech.getTechName());

        tv = (TextView) findViewById(R.id.view_techType);
        int techType = tech.getTechType();
        if (techType==0) {
            tv.setText("Position");
        }
        else if (techType==1) {
            tv.setText("Submission");
        }
        else if (techType==2) {
            tv.setText("Sweep");
        }
        else if (techType==3) {
            tv.setText("Takedown");
        }
        else if (techType==4) {
            tv.setText("Reversal");
        }
        else {
            tv.setText("Defense");
        }

        tv = (TextView) findViewById(R.id.view_techNotes);
        tv.setText(tech.getTechNote());
    }

    public void finishViewTech(View view) {
        finish();
    }

    public void editTech (View view) {
        Intent intent = new Intent(this,EditTech.class);
        intent.putExtra("tech_obj_edit",tech);
        startActivity(intent);
        dataSource.close();
        finish();
    }

    public void deleteTech(View view) {
        dataSource.removeFromTechs(tech);
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
