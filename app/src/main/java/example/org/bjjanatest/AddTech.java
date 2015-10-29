package example.org.bjjanatest;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import example.org.bjjanatest.db.TechDataSource;

public class AddTech extends ActionBarActivity{

    private String[] arraySpinner;
    private static Spinner spin;
    private TechDataSource dataSource;
    private static MyEditText ev;
    private static final int MAX_TECH_DATABASE_ROWS = 49;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tech);
        //database related
        dataSource = new TechDataSource(this);
        dataSource.open();

        this.arraySpinner = new String[] {"Position", "Submission", "Sweep", "Takedown", "Reversal", "Defense"};

        spin = (Spinner) findViewById(R.id.techType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spin.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_tech, menu);
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

    public void saveTechnique(View view){
        Tech tech = new Tech();

        ev = (MyEditText) findViewById(R.id.techNameEV);
        tech.setTechName(ev.getText().toString());

        ev = (MyEditText) findViewById(R.id.techNoteEV);
        tech.setTechNote(ev.getText().toString());

        spin = (Spinner) findViewById(R.id.techType);
        int i = spin.getSelectedItemPosition();

        tech.setTechType(i);

        if (dataSource.getTechLen() <= MAX_TECH_DATABASE_ROWS) { //limit the number of saved techniques
            tech = dataSource.create(tech);
            dataSource.close();
            finish();
        }
        else {
            Toast.makeText(this, "Technique limit reached.",
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
