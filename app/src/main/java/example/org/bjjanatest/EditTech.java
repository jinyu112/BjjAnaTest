package example.org.bjjanatest;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import example.org.bjjanatest.db.TechDataSource;
import example.org.bjjanatest.db.trainingDBOpenHelper;

public class EditTech extends ActionBarActivity {


    private TechDataSource dataSource;
    private static Tech tech;
    private String[] arraySpinner;
    private static Spinner spin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tech);

        dataSource = new TechDataSource(this);
        dataSource.open();

        Bundle data = getIntent().getExtras();
        tech = data.getParcelable("tech_obj_edit");


        EditText ev = (EditText) findViewById(R.id.edit_techNameEV);
        ev.setText(tech.getTechName());


        this.arraySpinner = new String[] {"Pass", "Submission", "Sweep", "Takedown", "Defense", "Misc."};
        spin = (Spinner) findViewById(R.id.edit_techType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spin.setAdapter(adapter);
        spin.setSelection(tech.getTechType());

        ev = (EditText) findViewById(R.id.edit_techNoteEV);
        ev.setText(tech.getTechNote());


        //need to add youtube url

    }

    public void finishEditTechnique(View view) {
        Tech techNew = new Tech();
        techNew.setId(tech.getId());
        spin = (Spinner) findViewById(R.id.edit_techType);
        int i = spin.getSelectedItemPosition();
        techNew.setTechType(i);

        MyEditText ev = (MyEditText) findViewById(R.id.edit_techNameEV);
        techNew.setTechName(ev.getText().toString());

        EditText et = (EditText) findViewById(R.id.edit_techNoteEV);
        techNew.setTechNote(et.getText().toString());


        techNew.setTechVidURL(tech.getTechVidURL()); //need to add youtube url

        dataSource.update(techNew);
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
