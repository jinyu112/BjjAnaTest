package example.org.bjjanatest;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;

import example.org.bjjanatest.db.DrillDataSource;

public class EditDrill extends ActionBarActivity{
    private static DrillDataSource dataSource;
    private static Drill drill;

    public EditDrill() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drill);

        //opne database connection
        dataSource = new DrillDataSource(this);
        dataSource.open();

        //the bundling of the data is to have a convenient way of passing the object to the next activity
        Bundle data = getIntent().getExtras();
        drill = data.getParcelable("drill_obj_edit");

        //displaying the total reps
        MyTextView tv = (MyTextView) findViewById(R.id.drillRepTotalTV_Edit);
        tv.setText(Integer.toString(drill.getDrillRepTotal()));

        //dispaying the drill name
        MyEditText ev = (MyEditText) findViewById(R.id.drillNameEV_Edit);
        ev.setText(drill.getDrillName());

    }

    public void finishEditDrill(View view) {
        //initialize some things
        Drill drillNew = new Drill();
        MyEditText ev = (MyEditText) findViewById(R.id.drillRepTotalEV_Edit);
        int addedReps = 0;

        //set id of new editted drill to old one for updating
        drillNew.setId(drill.getId());

        // Add reps to total
        String repTotalStr = "0";
        repTotalStr = ev.getText().toString();
        repTotalStr = repTotalStr.replaceAll("[^\\d]", ""); //remove any non-numeric characters
        if (repTotalStr.equals("")) {
            repTotalStr = "0";
        }
        try {
            addedReps = Integer.parseInt(repTotalStr);
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in EditDrill fragment: "
                    +  ex.getMessage());
        }
        drillNew.setDrillRepTotal(drill.getDrillRepTotal() + addedReps);

        //update name
        ev = (MyEditText) findViewById(R.id.drillNameEV_Edit);
        drillNew.setDrillName(ev.getText().toString());

        //update the row in the database
        dataSource.update(drillNew);
        dataSource.close();
        finish();
    }

    //delete a specific drill
    public void deleteDrill(View view) {
        dataSource.removeFromDrills(drill);
        dataSource.close();
        finish();
    }

    //back
    public void backEditDrill(View view) {
        dataSource.close();
        finish();
    }
}
