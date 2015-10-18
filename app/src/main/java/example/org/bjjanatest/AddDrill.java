package example.org.bjjanatest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import example.org.bjjanatest.db.DrillDataSource;

public class AddDrill extends ActionBarActivity {
    private DrillDataSource dataSource;
    private static CheckBox cb;
    private static MyEditText ev;

    public AddDrill() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drill);
        //database related
        dataSource = new DrillDataSource(this);
        dataSource.open();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_drill, menu);
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

    public void saveDrill(View view){
        Drill drill = new Drill();

        ev = (MyEditText) findViewById(R.id.drillNameEV);
        drill.setDrillName(ev.getText().toString());

        // Add reps to total
        ev = (MyEditText) findViewById(R.id.drillRepTotalEV);
        String repTotalStr = "0";
        int addedReps = 0;
        repTotalStr = ev.getText().toString();
        repTotalStr = repTotalStr.replaceAll("[^\\d]", "");
        if (repTotalStr.equals("")) {
            repTotalStr = "0";
        }
        try {
            addedReps = Integer.parseInt(repTotalStr);
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in EditDrill fragment: "
                    +  ex.getMessage());
        }
        drill.setDrillRepTotal(addedReps);

        dataSource.create(drill);
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
