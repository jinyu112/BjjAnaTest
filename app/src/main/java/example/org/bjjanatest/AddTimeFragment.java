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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AddTimeFragment extends Fragment {

    private static final String LOGTAG = "BJJTRAINING";
    private static final String filename = "myTimeData";
    private static MyTextView tv_totalTime;
    private static MyTextView tv_avgTimePerWeek;
    private static MyTextView tv_totalSessions;
    private static MyTextView tv_avgSessionsPerWeek;


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
        menu.getItem(0).setIcon(R.drawable.plus_50);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.time_add:
                Intent intent = new Intent(getActivity(),AddTime.class);
                startActivity(intent);
                break;
            case R.id.time_del:
                deleteTimeData();
                displayTimeData();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_frag, container, false);

        //define text views
        tv_totalTime = (MyTextView) rootView.findViewById(R.id.totalTime);
        tv_avgTimePerWeek = (MyTextView) rootView.findViewById(R.id.avgTime);
        tv_totalSessions = (MyTextView) rootView.findViewById(R.id.totalSessions);
        tv_avgSessionsPerWeek = (MyTextView) rootView.findViewById(R.id.avgSessions);

        displayTimeData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayTimeData();
    }

    //This function graphs the weight data points
    public void displayTimeData() {
        String timeDataForDisplay[] = new String[]{"0", "0", "0", "0"};
        int minDateInt=19000101;
        int maxDateInt=minDateInt;
        int numDaysBetween=0;
        double weeks = 0.0;
        double avgTimePerWeek=0.0;
        double avgSessPerWeek=0.0;
        if (fileExistance(filename)) {
            timeDataForDisplay=readTimeDataInternal();
            minDateInt = 19000101;
            maxDateInt = minDateInt;
            try {
                minDateInt = Integer.parseInt(timeDataForDisplay[1]);
                maxDateInt = Integer.parseInt(timeDataForDisplay[2]);
            } catch (NumberFormatException ex) {
                System.err.println("Caught NumberFormatException in AddTimeFragment fragment: "
                        +  ex.getMessage());
            }
            numDaysBetween=determineDaysBetween(maxDateInt,minDateInt);
            if (numDaysBetween < 7) {
                weeks=1;
            }
            else weeks = (double) numDaysBetween/7.0;
            avgTimePerWeek=0.0;
            avgSessPerWeek=0.0;
            try {
                avgTimePerWeek = (double) Double.parseDouble(timeDataForDisplay[3])/weeks;
                avgSessPerWeek = (double) Integer.parseInt(timeDataForDisplay[0])/weeks;
            } catch (NumberFormatException ex) {
                System.err.println("Caught NumberFormatException in AddTimeFragment fragment: "
                        +  ex.getMessage());
            }

            avgTimePerWeek = Math.round(avgTimePerWeek*10)/10.0;
            avgSessPerWeek = Math.round(avgSessPerWeek*10)/10.0;
            tv_totalTime.setText(timeDataForDisplay[3]);
            tv_avgTimePerWeek.setText(Double.toString(avgTimePerWeek));
            tv_totalSessions.setText(timeDataForDisplay[0]);
            tv_avgSessionsPerWeek.setText(Double.toString(avgSessPerWeek));
        }
        else {
            tv_totalTime.setText("0");
            tv_avgTimePerWeek.setText("0");
            tv_totalSessions.setText("0");
            tv_avgSessionsPerWeek.setText("0");
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

    public String[] readTimeDataInternal() {
        String timeDataArrayRetrieved[] = new String[]{"0", "0", "0", "0"};
        try {
            FileInputStream fin = getActivity().openFileInput(filename);
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
        File file = getActivity().getFileStreamPath(fname);
        return file.exists();
    }

    //
    public boolean deleteTimeData() {
        File dir = getActivity().getFilesDir();
        File file = new File(dir, filename);
        boolean deleted = file.delete();
        return deleted;
    }
}
