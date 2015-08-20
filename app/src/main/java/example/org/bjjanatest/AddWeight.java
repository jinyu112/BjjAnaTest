package example.org.bjjanatest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import example.org.bjjanatest.db.WeightDataSource;

public class AddWeight extends ActionBarActivity {

    private List<Weight> weights;
    WeightDataSource dataSource;
    private static final String LOGTAG = "BJJTRAINING";
    private String[] arrayMonthSpinner;
    private String[] arrayDaySpinner;
    private String[] arrayYearSpinner;
    private Spinner spin;
    private static EditText ev;
    private static int currDay;
    private static int currMonth;
    private static int currYear;

    public AddWeight () {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);
        dataSource = new WeightDataSource(this);
        dataSource.open();

        Calendar c = Calendar.getInstance();
        currDay = c.get(Calendar.DAY_OF_MONTH) - 1;
        if (currDay > 30) {
            currDay = 30;
        }
        currMonth = c.get(Calendar.MONTH);
        if (currMonth > 11) {
            currMonth = 11;
        }
        currYear = c.get(Calendar.YEAR) - 2001;
        if (currYear >69) {
            currYear = 69;
        }

        arrayMonthSpinner = new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        spin = (Spinner) findViewById(R.id.month_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayMonthSpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currMonth);
        Log.i(LOGTAG, "month: " + currMonth);

        arrayDaySpinner = new String[31];
        for (int i=0; i<31; i++) {
            arrayDaySpinner[i] = Integer.toString(i+1);
        }
        spin = (Spinner) findViewById(R.id.day_spinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayDaySpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currDay);
        Log.i(LOGTAG, "day: " + currDay);

        arrayYearSpinner = new String[70];
        for (int i=0; i<70; i++) {
            arrayYearSpinner[i] = Integer.toString(i+2000);
        }
        spin = (Spinner) findViewById(R.id.year_spinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayYearSpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currYear);
        Log.i(LOGTAG, "year: " + currYear);

        ev = (EditText) findViewById(R.id.weightEV);
        ev.setText("0.0");
        ev.setGravity(Gravity.CENTER_HORIZONTAL);

    }

    public void saveWeight(View view) {
        Weight weight = new Weight();
        ev = (EditText) findViewById(R.id.weightEV);
        double mass = Double.parseDouble(ev.getText().toString());
        spin = (Spinner) findViewById(R.id.day_spinner);
        currDay = spin.getSelectedItemPosition()+1; //day is index PLUS 1

        spin = (Spinner) findViewById(R.id.month_spinner);
        currMonth = spin.getSelectedItemPosition()+1;

        spin = (Spinner) findViewById(R.id.year_spinner);
        currYear = spin.getSelectedItemPosition()+1;

        weight.setMass(mass);
        weight.setDay(currDay);
        weight.setMonth(currMonth);
        weight.setYear(currYear);

        weight.setDateStr(formatDateStr(currMonth,currDay,currYear));
        Log.i(LOGTAG, Integer.toString(currMonth) + Integer.toString(currDay) + Integer.toString(currYear) );
        dataSource.create(weight);
        dataSource.close();

        Toast.makeText(this, "Weight saved.",
                Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        dataSource.open(); //opens connection to the datasource
    }

    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataSource.close();
    }

    public String formatDateStr(int monthIn, int dayIn, int yearIn) {
        String tempMonth="";
        String tempDay="";
        String formattedDate="";

        //add a 0 in front of the month if the month is less than 10
        if (monthIn<10) {
            tempMonth = "0" + Integer.toString(monthIn);
        }
        else tempMonth = Integer.toString(monthIn);

        if (dayIn<10) {
            tempDay = "0" + Integer.toString(dayIn);
        }
        else tempDay = Integer.toString(dayIn);

        yearIn = yearIn + 2000; //years start at 2000 and go to 2070 but the spinner index starts at 0 and goes to 70

        formattedDate = Integer.toString(yearIn) + tempMonth + tempDay;

        return formattedDate;
    }
}
