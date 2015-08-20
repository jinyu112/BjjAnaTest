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
import android.widget.Toast;


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

import example.org.bjjanatest.db.WeightDataSource;

public class AddWeightFragment extends Fragment {

    WeightDataSource dataSource;
    private static final String LOGTAG = "BJJTRAINING";
    private static LineChart lineChart; //should this be static?
    private String minDateStr = "19000101";
    private String maxDateStr = "19000101";
    private int minDateIndex = 0;


    public AddWeightFragment () {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new WeightDataSource(getActivity());
        dataSource.open();
        Log.i(LOGTAG, "Weight frag onCreate called");
        setHasOptionsMenu(true);
        Toast.makeText(this.getActivity(), "num weights: " + dataSource.findAll().size(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_weight, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weight_add:
                Intent intent = new Intent(getActivity(),AddWeight.class);
                startActivity(intent);
                Log.i(LOGTAG,"adding a new weight from the weight frag");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weight_frag,container,false);
        lineChart = (LineChart) rootView.findViewById(R.id.weightGraph);
        displayWeightData(refreshDisplay());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataSource.open(); //opens connection to the datasource
        Toast.makeText(this.getActivity(), "num weights: " + dataSource.findAll().size(),Toast.LENGTH_SHORT).show();
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
        List<Weight> weights = dataSource.findAll();
        minDateStr=dataSource.getMinDateStr();
        maxDateStr=dataSource.getMinDateStr();
        minDateIndex=dataSource.getMinDateIndex();
        return weights;
    }

    //This function graphs the weight data points
    public void displayWeightData(List<Weight> weights) {
        Weight weight;
        int numDays=0; //number of days between the earliest date and the current date
        String dateStr;
        int date;

        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        Entry c1e1;

        for ( int i = 0; i < weights.size(); i++ )
        {
            weight = weights.get(i);
            dateStr = weight.getDateStr();
            date = Integer.parseInt(dateStr);
            numDays=determineDaysBetween(dateStr, minDateStr);
            c1e1 = new Entry((float) weight.getMass(), i);
            valsComp1.add(c1e1);
            xVals.add(dateStr);
        }
        LineDataSet setComp1 = new LineDataSet(valsComp1, "weight");
        setComp1.setDrawValues(false);
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);

        LineData data = new LineData(xVals, dataSets);
        lineChart.setData(data);
        lineChart.invalidate();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }



    public int determineDaysBetween(String currDate, String minDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        int numDaysBetween=0;
        try {
            Date currDateObj = myFormat.parse(currDate);
            Date minDateObj = myFormat.parse(minDate);
            long diff = currDateObj.getTime() - minDateObj.getTime();
            numDaysBetween = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            numDaysBetween=-1;
        }

        return numDaysBetween;
    }
}
