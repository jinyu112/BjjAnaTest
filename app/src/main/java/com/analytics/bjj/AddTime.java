package com.analytics.bjj;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class AddTime extends ActionBarActivity {

    private String filename = "myTimeData";
    private static final String LOGTAG = "BJJTRAINING";
    private static String[] arrayMonthSpinner;
    private static String[] arrayDaySpinner;
    private static String[] arrayYearSpinner;
    private static Spinner spin;
    private static MyEditText ev;
    private static int currDay;
    private static int currMonth;
    private static int currYear;
    //private static final int MAX_WEIGHT_DATABASE_ROWS = 365;

    public AddTime () {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        Calendar c = Calendar.getInstance(); //get the current date
        currDay = c.get(Calendar.DAY_OF_MONTH) - 1;
        if (currDay > 30) {
            currDay = 30;
        }
        currMonth = c.get(Calendar.MONTH); //January has a value of 0
        if (currMonth > 11) {
            currMonth = 11;
        }
        currYear = c.get(Calendar.YEAR) - 2001;
        if (currYear >69) {
            currYear = 69;
        }

        //populate month spinner
        arrayMonthSpinner = new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        spin = (Spinner) findViewById(R.id.month_spinner_time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayMonthSpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currMonth);

        //populate day spinner
        arrayDaySpinner = new String[31];
        for (int i=0; i<31; i++) {
            arrayDaySpinner[i] = Integer.toString(i+1);
        }
        spin = (Spinner) findViewById(R.id.day_spinner_time);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayDaySpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currDay);

        //populate year spinner
        arrayYearSpinner = new String[70];
        for (int i=0; i<70; i++) {
            arrayYearSpinner[i] = Integer.toString(i+2001);
        }
        spin = (Spinner) findViewById(R.id.year_spinner_time);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayYearSpinner);
        spin.setAdapter(adapter);
        spin.setSelection(currYear);

        ev = (MyEditText) findViewById(R.id.timeEV);
        ev.setText("0.0");
        ev.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public void saveTime(View view) {
        String timeStr="";
        Time time = new Time();
        String timeDataArray[] = new String[] {"0", "0", "0", "0"};
        ev = (MyEditText) findViewById(R.id.timeEV);

        timeStr = ev.getText().toString();
        if (timeStr.equals(".") || timeStr.equals("..") || timeStr.equals("") || timeStr.equals(",")) {
            timeStr = "0";
        }
        double hours = 0.0;
        try {
            hours = Double.parseDouble(timeStr);
        } catch (NumberFormatException ex)
        {
            System.err.println("Caught NumberFormatException in AddTime activity: "
                    +  ex.getMessage());
            hours = 0.0;
        }
        spin = (Spinner) findViewById(R.id.day_spinner_time);
        currDay = spin.getSelectedItemPosition()+1; //day is index PLUS 1

        spin = (Spinner) findViewById(R.id.month_spinner_time);
        currMonth = spin.getSelectedItemPosition()+1;

        spin = (Spinner) findViewById(R.id.year_spinner_time);
        currYear = spin.getSelectedItemPosition()+1;

        time.setHours(hours);
        time.setDay(currDay);
        time.setMonth(currMonth);
        time.setYear(currYear);
        time.setDate(formatDate(currYear, currMonth, currDay));

        timeDataArray[0]="1";                               // training session increment
        timeDataArray[1]=Integer.toString(time.getDate());  // minimum date
        timeDataArray[2]=Integer.toString(time.getDate());  // maximum date
        timeDataArray[3]=Double.toString(time.getHours());  // total hours trained

        if (timeStr.equals("0")) {
            Toast.makeText(this, "Invalid time.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            //prevent insensible date inputs (ie Feb 31)
            if (checkValidDate(currMonth, currDay)) {
                Toast.makeText(this, "Time saved.",
                        Toast.LENGTH_LONG).show();
                saveTimeDataInternal(timeDataArray);
                finish();
            } else {
                Toast.makeText(this, "Invalid date.",
                        Toast.LENGTH_LONG).show();
            }
        }

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
        formattedDateInt = 19000101;
        try {
            formattedDateStr = formattedDateStr.replaceAll("[^\\d]", "");
            formattedDateInt = Integer.parseInt(formattedDateStr);
        } catch (NumberFormatException ex) {
            System.err.println("Caught NumberFormatException in AddTime activity, formatDate method: "
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

    //use internal storage to save the training time data
    public void saveTimeDataInternal(String[] timeDataArray) {
        FileOutputStream outputStream;

        if (fileExistance(filename)) {
            String[] timeDataArrayRetreived = readTimeDataInternal();

            if (Integer.parseInt(timeDataArray[1]) > Integer.parseInt(timeDataArrayRetreived[1])) {
                timeDataArray[1] = timeDataArrayRetreived[1];
            }

            if (Integer.parseInt(timeDataArray[2]) < Integer.parseInt(timeDataArrayRetreived[2])) {
                timeDataArray[2] = timeDataArrayRetreived[2];
            }

            timeDataArray[0] = Integer.toString(Integer.parseInt(timeDataArray[0]) + Integer.parseInt(timeDataArrayRetreived[0])); //increment training session
            timeDataArray[3] = Double.toString(Double.parseDouble(timeDataArray[3]) + Double.parseDouble(timeDataArrayRetreived[3])); //add trained hours to total

            try {
                outputStream = openFileOutput(filename, this.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                oos.writeObject(timeDataArray);
                oos.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                outputStream = openFileOutput(filename, this.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                oos.writeObject(timeDataArray);
                oos.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //this function returns data from the internal storage with information about the time trained
    //the new time data point is then added on to this array data
    public String[] readTimeDataInternal() {
        String timeDataArrayRetrieved[] = new String[]{"0", "0", "0", "0"};
        try {
            FileInputStream fin = openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fin);
            timeDataArrayRetrieved = (String[]) ois.readObject();
            ois.close();
            fin.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return timeDataArrayRetrieved;
    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}