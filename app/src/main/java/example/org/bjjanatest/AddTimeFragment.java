package example.org.bjjanatest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddTimeFragment extends Fragment {

    private static final String LOGTAG = "BJJTRAINING";


    public AddTimeFragment () {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_time, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.time_add:
                Intent intent = new Intent(getActivity(),AddTime.class);
                startActivity(intent);
                Log.i(LOGTAG,"adding a new weight from the weight frag");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_frag, container, false);
        return rootView;
    }

    //This function graphs the weight data points
    public void displayTimeData(List<Time> times) {
        Time time = new Time();
        double sumTimes = 0.0;
        int date = 19000101;
        int timeLen=times.size();
        for ( int i = 0; i < timeLen; i++ )
        {
            time = times.get(i);
            sumTimes = sumTimes + time.getHours();
            date = time.getDate();


        }

    }

    //This function calculates the number of days between two string dates
    public int determineDaysBetween(int currDate, int prevDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDateTemp=Integer.toString(currDate);
        String prevDateTemp=Integer.toString(prevDate);
        int numDaysBetween=0;
        try {
            Date currDateObj = myFormat.parse(currentDateTemp);
            Date minDateObj = myFormat.parse(prevDateTemp);
            long diff = currDateObj.getTime() - minDateObj.getTime();
            numDaysBetween = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            numDaysBetween=-1;
        }
        return numDaysBetween;
    }

    //this function increments the input date by 1 day
    public int[] incrementDate(int yearIn, int monthIn, int dayIn) {
        int[] dateArray= new int[3];
        int month=monthIn;
        int year=yearIn;
        int day=dayIn;

        day=day+1;
        if (month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) {
            if (day==32){
                month=month+1;
                day=1;
            }
        }
        else if (month==4 || month==6 || month==9 || month==11) {
            if (day==31) {
                month=month+1;
                day=1;
            }
        }
        else if (month==2) {
            if (day==29) {
                month=month+1;
                day=1;
            }
        }
        else {
            year=year+1;
            day=1;
            month=1;
        }

        dateArray[0]= year;
        dateArray[1]= month;
        dateArray[2]= day;

        return dateArray;
    }

    //this function returns a string with the correct date format for x axis labeling (similar to formatDate() in AddWeight activity)
    public String formatDateStr(int yearIn, int monthIn, int dayIn) {
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

        yearIn = yearIn + 2000;

        formattedDate = Integer.toString(yearIn) + tempMonth + tempDay;

        return formattedDate;
    }
}
