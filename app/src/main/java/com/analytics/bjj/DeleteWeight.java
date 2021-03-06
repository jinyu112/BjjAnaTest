package com.analytics.bjj;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import com.analytics.bjj.db.WeightDataSource;

public class DeleteWeight extends ActionBarActivity {

    WeightDataSource dataSource;
    private String[] arrayMonthSpinner;
    private String[] arrayDaySpinner;
    private String[] arrayYearSpinner;
    private Spinner spin;
    private static int currDay;
    private static int currMonth;
    private static int currYear;

    public DeleteWeight () {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_weight);
        dataSource = new WeightDataSource(this);
        dataSource.open();

        Calendar c = Calendar.getInstance(); //get the current date
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

        //populate month spinner
        arrayMonthSpinner = new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        spin = (Spinner) findViewById(R.id.del_month_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayMonthSpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currMonth);

        //populate day spinner
        arrayDaySpinner = new String[31];
        for (int i=0; i<31; i++) {
            arrayDaySpinner[i] = Integer.toString(i+1);
        }
        spin = (Spinner) findViewById(R.id.del_day_spinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayDaySpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currDay);

        //populate year spinner
        arrayYearSpinner = new String[70];
        for (int i=0; i<70; i++) {
            arrayYearSpinner[i] = Integer.toString(i+2001);
        }
        spin = (Spinner) findViewById(R.id.del_year_spinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayYearSpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currYear);

    }

    public void deleteWeight(View view) {
        int dateTemp=19000101;
        boolean removalSuccess=false;

        spin = (Spinner) findViewById(R.id.del_day_spinner);
        currDay = spin.getSelectedItemPosition()+1; //day is index PLUS 1

        spin = (Spinner) findViewById(R.id.del_month_spinner);
        currMonth = spin.getSelectedItemPosition()+1;

        spin = (Spinner) findViewById(R.id.del_year_spinner);
        currYear = spin.getSelectedItemPosition()+1;

        dateTemp=formatDate(currYear, currMonth, currDay);

        //prevent insensible date inputs (ie Feb 31)
        if (checkValidDate(currMonth,currDay)) {
            removalSuccess = dataSource.removeFromWeightsByDate(dateTemp);
            dataSource.close();

            if (removalSuccess) {
                Toast.makeText(this, "Weight deleted",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No weight deleted.",
                        Toast.LENGTH_LONG).show();
            }
            finish();
        }
        else {
            Toast.makeText(this, "Invalid date.",
                    Toast.LENGTH_LONG).show();
        }
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

    public int formatDate(int yearIn, int monthIn, int dayIn) {
        String tempMonth="";
        String tempDay="";
        String formattedDateStr="";
        int formattedDateInt=19000101;

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

        formattedDateStr = Integer.toString(yearIn) + tempMonth + tempDay;

        try {
            formattedDateInt = Integer.parseInt(formattedDateStr);
        } catch (NumberFormatException ex)
        {
            System.err.println("Caught NumberFormatException in DeletWeight activity: "
                    +  ex.getMessage());
        }

        return formattedDateInt;
    }

    public boolean checkValidDate(int monthIn, int dayIn) {
        boolean validDate = true;
        if (monthIn==2 && dayIn>29) {
            validDate = false;
        }
        if ((monthIn==4 || monthIn==6 || monthIn==9 || monthIn==11) && dayIn>30) {
            validDate = false;
        }
        return validDate;
    }
}
