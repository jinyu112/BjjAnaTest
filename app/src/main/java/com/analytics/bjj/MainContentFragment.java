package com.analytics.bjj;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.analytics.bjj.db.DrillDataSource;
import com.analytics.bjj.db.TechDataSource;
import com.analytics.bjj.db.TournDataSource;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainContentFragment extends Fragment {
    private MyTextView tv;

    //database related
    static TournDataSource dataSource;
    static DrillDataSource drillsDataSource;
    static TechDataSource techsDataSource;
    private static HorizontalBarChart horizontalBarChart_OffPerc;
    private static HorizontalBarChart horizontalBarChart_TournAvg;
    private static PieChart pieChart_TournTotals;
    private static final String filename = "myTimeData";

    public MainContentFragment (){
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_content_frag,container,false);
        dataSource = new TournDataSource(getActivity());
        drillsDataSource = new DrillDataSource(getActivity());
        techsDataSource = new TechDataSource(getActivity());

        horizontalBarChart_OffPerc = (HorizontalBarChart) rootView.findViewById(R.id.tournOffPercChart);
        horizontalBarChart_OffPerc.setDrawValueAboveBar(true);
        horizontalBarChart_OffPerc.setDescription("");
        horizontalBarChart_OffPerc.animateY(1000);
        horizontalBarChart_OffPerc.setPinchZoom(false);
        horizontalBarChart_OffPerc.setTouchEnabled(false);
        horizontalBarChart_OffPerc.getLegend().setEnabled(false);

        XAxis xl_offperc = horizontalBarChart_OffPerc.getXAxis(); //actual data on the x axis (horizontal chart only)
        xl_offperc.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl_offperc.setDrawGridLines(false);
        xl_offperc.setEnabled(false);

        YAxis yl_offperc = horizontalBarChart_OffPerc.getAxisLeft(); //left y axis
        yl_offperc.setDrawLabels(false);
        yl_offperc.setEnabled(false);
        yl_offperc.setAxisMaxValue(100f);

        YAxis yr_offperc = horizontalBarChart_OffPerc.getAxisRight(); //right y axis
        yr_offperc.setDrawLabels(false);
        yr_offperc.setEnabled(false);
        yr_offperc.setAxisMaxValue(100f);

        horizontalBarChart_OffPerc.setNoDataText("No data.");


        //Tournament average horizon bar chart
        horizontalBarChart_TournAvg = (HorizontalBarChart) rootView.findViewById(R.id.tournAvgChart);
        horizontalBarChart_TournAvg.setDrawValueAboveBar(true);
        horizontalBarChart_TournAvg.setDescription("");
        horizontalBarChart_TournAvg.animateY(1000);
        horizontalBarChart_TournAvg.setPinchZoom(false);
        horizontalBarChart_TournAvg.setTouchEnabled(false);
        horizontalBarChart_TournAvg.getLegend().setEnabled(false);


        XAxis xl_avg = horizontalBarChart_TournAvg.getXAxis();  //actual data on the x axis (horizontal chart only)
        xl_avg.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl_avg.setDrawGridLines(false);
        xl_avg.setEnabled(false);

        YAxis yl_avg = horizontalBarChart_TournAvg.getAxisLeft(); //left y axis
        yl_avg.setDrawLabels(false);
        yl_avg.setEnabled(false);

        YAxis yr_avg = horizontalBarChart_TournAvg.getAxisRight(); //right y axis
        yr_avg.setDrawLabels(false);
        yr_avg.setEnabled(false);

        horizontalBarChart_TournAvg.setNoDataText("No data.");


        //pie chart
        pieChart_TournTotals = (PieChart) rootView.findViewById(R.id.pieChartTotals);
        pieChart_TournTotals.setDescription("");
        pieChart_TournTotals.setDrawHoleEnabled(true);
        pieChart_TournTotals.setHoleColorTransparent(true);
        pieChart_TournTotals.setTransparentCircleColor(Color.WHITE);
        pieChart_TournTotals.setTransparentCircleAlpha(110);
        pieChart_TournTotals.getLegend().setEnabled(false);
        pieChart_TournTotals.setTouchEnabled(false);
        pieChart_TournTotals.setHoleRadius(45f);
        pieChart_TournTotals.setTransparentCircleRadius(49f);
        pieChart_TournTotals.setDrawCenterText(true);

        pieChart_TournTotals.setRotationAngle(0);
        pieChart_TournTotals.setRotationEnabled(true);

        pieChart_TournTotals.setNoDataText("No data.");
        return rootView;
    }

    public void getStats () {
        List<Tourn> tourns = dataSource.findAll();
        if (tourns.size()!=0) {

            tv = (MyTextView) getView().findViewById(R.id.main_avgMatchTime);
            tv.setText(String.format("%5.2f",dataSource.getAvgMatchTime()));

            tv = (MyTextView) getView().findViewById(R.id.main_record);
            tv.setText(String.format("%d W-%d L",dataSource.getWins(),dataSource.getTournLen()-dataSource.getWins()));

            //set colors
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(ColorTemplate.getHoloBlue());

            //plot offense percentage data
            ArrayList<BarEntry> dataPoints_Avg = new ArrayList<BarEntry>();
            ArrayList<String> xAxisStrings = new ArrayList<String>();
            BarEntry entry;

            entry = new BarEntry ((float) dataSource.getAvgTdScored(), 0);
            dataPoints_Avg.add(entry);
            xAxisStrings.add("");

            entry = new BarEntry ((float) dataSource.getAvgTdAtt(), 1);
            dataPoints_Avg.add(entry);
            xAxisStrings.add("");

            entry = new BarEntry((float) dataSource.getAvgPassesScored(), 2);
            dataPoints_Avg.add(entry);
            xAxisStrings.add("");

            entry = new BarEntry((float) dataSource.getAvgPassesAtt(), 3);
            dataPoints_Avg.add(entry);
            xAxisStrings.add("");

            entry = new BarEntry ((float) dataSource.getAvgSweepsScored(), 4);
            dataPoints_Avg.add(entry);
            xAxisStrings.add("");

            entry = new BarEntry ((float) dataSource.getAvgSweepsAtt(), 5);
            dataPoints_Avg.add(entry);
            xAxisStrings.add("");

            entry = new BarEntry((float) dataSource.getAvgSubsScored(), 6);
            dataPoints_Avg.add(entry);
            xAxisStrings.add("");

            entry = new BarEntry((float) dataSource.getAvgSubsAtt(), 7);
            dataPoints_Avg.add(entry);
            xAxisStrings.add("");

            BarDataSet set1 = new BarDataSet(dataPoints_Avg, "Tournament Offsense Percentages");
            set1.setBarSpacePercent(0f);
            set1.setColors(colors);

            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xAxisStrings, dataSets);

            horizontalBarChart_TournAvg.setData(data);
            horizontalBarChart_TournAvg.invalidate();


            //plot offense percentage data
            ArrayList<BarEntry> dataPoints_OffPerc = new ArrayList<BarEntry>();
            ArrayList<String> xAxisStrings_OffPerc = new ArrayList<String>();

            entry = new BarEntry ((float) dataSource.getTdSucPerc()*100, 0);
            dataPoints_OffPerc.add(entry);
            xAxisStrings_OffPerc.add("TD %");

            entry = new BarEntry((float) dataSource.getPassSucPerc()*100, 1);
            dataPoints_OffPerc.add(entry);
            xAxisStrings_OffPerc.add("Pass %");

            entry = new BarEntry((float) dataSource.getSweepSucPerc()*100, 2);
            dataPoints_OffPerc.add(entry);
            xAxisStrings_OffPerc.add("Sweep %");

            entry = new BarEntry((float) dataSource.getSubSucPerc()*100, 3);
            dataPoints_OffPerc.add(entry);
            xAxisStrings_OffPerc.add("Sub %");

            BarDataSet set1_OffPerc = new BarDataSet(dataPoints_OffPerc, "Tournament Offsense Percentages");
            set1_OffPerc.setColors(colors);
            set1_OffPerc.setBarSpacePercent(0f);

            ArrayList<BarDataSet> dataSets_OffPerc = new ArrayList<BarDataSet>();
            dataSets_OffPerc.add(set1_OffPerc);

            BarData data_OffPerc = new BarData(xAxisStrings_OffPerc, dataSets_OffPerc);

            horizontalBarChart_OffPerc.setData(data_OffPerc);
            horizontalBarChart_OffPerc.invalidate();

            //plot point totals
            ArrayList<Entry> dataPoints_PtsTotals = new ArrayList<Entry>();
            ArrayList<String> xAxisStrings_Totals = new ArrayList<String>();

            dataPoints_PtsTotals.add(new Entry(Math.round(dataSource.getTotalPts()),0));
            xAxisStrings_Totals.add("Points Scored");

            dataPoints_PtsTotals.add(new Entry(Math.round(dataSource.getTotalPtsAllowed()), 1));
            xAxisStrings_Totals.add("Points Allowed");

            PieDataSet dataSet_Totals = new PieDataSet(dataPoints_PtsTotals, "Point Totals");
            dataSet_Totals.setSliceSpace(3f);
            dataSet_Totals.setSelectionShift(5f);

            PieData data_Totals = new PieData(xAxisStrings_Totals, dataSet_Totals);
            data_Totals.setValueFormatter(new IntValueFormatter()); //format the values a integers

            //set pie chart colors
            ArrayList<Integer> colorsPie = new ArrayList<Integer>();
            colorsPie.add(ColorTemplate.getHoloBlue());
            colorsPie.add(Color.rgb(255, 167, 0));

            dataSet_Totals.setColors(colorsPie);
            pieChart_TournTotals.setData(data_Totals);
            pieChart_TournTotals.highlightValues(null);
            pieChart_TournTotals.invalidate();


            //training time data
            String timeDataForDisplay[] = new String[]{"0", "0", "0", "0"};
            int minDateInt=19000101;
            int maxDateInt=minDateInt;
            int numDaysBetween=0;
            double weeks = 0.0;
            double avgTimePerWeek=0.0;
            double totalTimeTrained=0.0;
            if (fileExistance(filename)) {
                timeDataForDisplay = readTimeDataInternal();
                minDateInt = 19000101;
                maxDateInt = minDateInt;
                try {
                    minDateInt = Integer.parseInt(timeDataForDisplay[1]);
                    maxDateInt = Integer.parseInt(timeDataForDisplay[2]);
                } catch (NumberFormatException ex) {
                    System.err.println("Caught NumberFormatException in mainactivity: "
                            + ex.getMessage());
                }
                numDaysBetween = determineDaysBetween(maxDateInt, minDateInt);
                if (numDaysBetween < 7) {
                    weeks = 1;
                } else weeks = (double) numDaysBetween / 7.0;
                avgTimePerWeek = 0.0;
                totalTimeTrained=0.0;
                try {
                    avgTimePerWeek = (double) Double.parseDouble(timeDataForDisplay[3]) / weeks;
                    totalTimeTrained = (double) Double.parseDouble(timeDataForDisplay[3]);
                } catch (NumberFormatException ex) {
                    System.err.println("Caught NumberFormatException in AddTimeFragment fragment: "
                            + ex.getMessage());
                }

                avgTimePerWeek = Math.round(avgTimePerWeek * 10) / 10.0;
                totalTimeTrained = Math.round(totalTimeTrained*10)/10.0;
            }

            tv = (MyTextView) getView().findViewById(R.id.main_totalTime);
            tv.setText(String.format("%6.1f",totalTimeTrained));

            tv = (MyTextView) getView().findViewById(R.id.main_avgTime);
            tv.setText(String.format("%6.1f",avgTimePerWeek));

        }
//        else {
            //Toast toast;
            //toast=Toast.makeText(getActivity(), "No tournament data found", Toast.LENGTH_LONG);
            //toast.show();
//        }

        int drillLenTemp = drillsDataSource.runDrillCountQuery();
        tv = (MyTextView) getView().findViewById(R.id.main_numDrills);
        if (drillLenTemp!=0) {
            tv.setText(String.format("%d",drillLenTemp));
        }
        else {
            tv.setText("0");
        }

        int techLenTemp = techsDataSource.runTechCountQuery();
        tv = (MyTextView) getView().findViewById(R.id.main_numTechs);
        if (techLenTemp!=0) {
            tv.setText(String.format("%d",techLenTemp));
        }
        else {
            tv.setText("0");
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
        drillsDataSource.open();
        techsDataSource.open();
        getStats();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
        drillsDataSource.close();
        techsDataSource.close();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataSource.close();
        drillsDataSource.close();
        techsDataSource.close();
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

}
