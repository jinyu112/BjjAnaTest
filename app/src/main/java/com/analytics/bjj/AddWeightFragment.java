package com.analytics.bjj;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.analytics.bjj.db.WeightDataSource;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AddWeightFragment extends Fragment {

    WeightDataSource dataSource;
    private static LineChart lineChart; //should this be static?
    private static MyTextView tv_avgWeight;
    private static MyTextView tv_maxWeight;
    private static MyTextView tv_minWeight;
    private static MyTextView tv_weightDiff;
    private static YAxis yAxisL;
    private static YAxis yAxisR;


    public AddWeightFragment () {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new WeightDataSource(getActivity());
        dataSource.open();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_weight, menu);
        menu.getItem(0).setIcon(R.drawable.plus_50);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weight_add:
                Intent intent = new Intent(getActivity(),AddWeight.class);
                startActivity(intent);

                break;
            case R.id.weight_del:
                dataSource.removeAllWeights();
                displayWeightData(refreshDisplay());
                lineChart.clearValues();
                break;
            case R.id.delete_weight_menu:
                Intent intent1 = new Intent(getActivity(),DeleteWeight.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weight_frag, container, false);

        //define line chart and its settings
        lineChart = (LineChart) rootView.findViewById(R.id.weightGraph);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        yAxisL = lineChart.getAxisLeft();
        yAxisL.setStartAtZero(false);
        yAxisL.setDrawGridLines(false);

        yAxisR = lineChart.getAxisRight();
        yAxisR.setDrawLabels(false);

        //define text views
        tv_avgWeight = (MyTextView) rootView.findViewById(R.id.avgWeight);
        tv_maxWeight = (MyTextView) rootView.findViewById(R.id.maxWeight);
        tv_minWeight = (MyTextView) rootView.findViewById(R.id.minWeight);
        tv_weightDiff = (MyTextView) rootView.findViewById(R.id.weightDiff);

        displayWeightData(refreshDisplay());
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        dataSource.open(); //opens connection to the datasource
        dataSource.findAll();
        if (dataSource.getWeightLen()>0) {
            yAxisL.setAxisMinValue((float) dataSource.getMinWeightDB() - 1);
            yAxisL.setAxisMaxValue((float) dataSource.getMaxWeightDB() + 1);
        }
        else {
            yAxisL.setAxisMinValue((float) 0);
            yAxisL.setAxisMaxValue((float) 100);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //ga
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        final Tracker tracker = application.getDefaultTracker();
        if(tracker != null){
            tracker.setScreenName(getClass().getSimpleName());
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }


        dataSource.open(); //opens connection to the datasource
        displayWeightData(refreshDisplay());
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

    public List<Weight> refreshDisplay() {
        return dataSource.findAll();
    }

    //This function graphs the weight data points
    public void displayWeightData(List<Weight> weights) {
        Weight weight, prevWeight=new Weight();
        int numDays=0; //number of days between the the previous date
        int date, prevDate=19000101;
        int index=0; //this is the x axis index (ie the data point (index,weight) is plotted)
        int tempYear, tempMonth, tempDay;
        int weightLen=0;
        int[] tempDateArray = new int[3];
        double avgWeight=0, weightDiff=0;
        double sumWeights=0;
        double firstWeight=0, lastWeight=0;
        boolean firstPass=true;


        ArrayList<Entry> dataPoints = new ArrayList<Entry>();
        ArrayList<String> xStrings = new ArrayList<String>();
        Entry entry;

        //setup data points for plotting
        //mysqlite already orders the data by date
        weightLen=weights.size();
        for ( int i = 0; i < weightLen; i++ )
        {

            weight = weights.get(i);
            sumWeights = sumWeights + weight.getMass();
            date = weight.getDate();

            //this following code creates space between data points that are more than
            //a day apart
            if (firstPass) {
                firstWeight=weight.getMass();
                firstPass=false;
                numDays=0;
            }
            else {
                //check if more than a day apart, if so calculate how many days and cap at 7
                if (date-prevDate!=1) { //date should be later than prevDate
                    numDays = determineDaysBetween(date, prevDate);
                    if (numDays > 7) {
                        numDays = 7;
                    }
                }
                else numDays=1;
            }

            //if greater than 1 day apart, need to add extra labels on x axis
            if (numDays>1) {
                tempYear=prevWeight.getYear();
                tempMonth=prevWeight.getMonth();
                tempDay=prevWeight.getDay();
                for (int j = 0; j <numDays-1; j++){
                    tempDateArray=incrementDate(tempYear,tempMonth,tempDay); //add one day to date
                    xStrings.add(formatDateStr(tempDateArray[0],tempDateArray[1],tempDateArray[2])); //format to string and add to labels
                    index=index+1;
                    //set previous date
                    tempYear=tempDateArray[0];
                    tempMonth=tempDateArray[1];
                    tempDay=tempDateArray[2];
                }
            }

            //then add the actual data point
            entry = new Entry((float) weight.getMass(), index);
            dataPoints.add(entry);
            xStrings.add(Integer.toString(date));
            index = index + 1;

            prevWeight = weight;
            prevDate=date; //save the previous date to make days between calculation

            if (i == weightLen - 1) {
                lastWeight = weight.getMass();
            }
        }

        //plot the data
        LineDataSet dataSet = new LineDataSet(dataPoints, "weight");
        dataSet.setDrawValues(false);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(dataSet);

        LineData data = new LineData(xStrings, dataSets);

        lineChart.setVisibleXRangeMaximum(30);
        lineChart.moveViewToX(index);
        lineChart.setData(data);
        lineChart.invalidate();

        //Show the weight statistics in the TVs
        avgWeight = sumWeights/weightLen;

        tv_avgWeight.setText(Double.toString(Math.round((avgWeight) * 10) / 10.0));

        if (weightLen>0) {
            tv_maxWeight.setText(Double.toString(Math.round((dataSource.getMaxWeightDB()) * 10) / 10.0));
            tv_minWeight.setText(Double.toString(Math.round((dataSource.getMinWeightDB()) * 10) / 10.0));

            yAxisL.setAxisMinValue((float) dataSource.getMinWeightDB() - 1);
            yAxisL.setAxisMaxValue((float) dataSource.getMaxWeightDB() + 1);
        }
        else {
            tv_maxWeight.setText("0.0");
            tv_minWeight.setText("0.0");

            yAxisL.setAxisMinValue((float) 0);
            yAxisL.setAxisMaxValue((float) 100);
        }

        weightDiff=Math.abs(firstWeight - lastWeight);
        if (firstWeight > lastWeight) {
            tv_weightDiff.setText("You have lost " + Double.toString(Math.round((weightDiff) * 10) / 10.0) + " pounds.");
        }
        else tv_weightDiff.setText("You have gained " + Double.toString(Math.round((weightDiff) * 10) / 10.0) + " pounds.");

        lineChart.invalidate();

    }

    //This function calculates the number of days between two string dates
    public int determineDaysBetween(int currDate, int prevDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDateTemp=Integer.toString(currDate);
        String prevDateTemp=Integer.toString(prevDate);
        int numDaysBetween = 0;
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
