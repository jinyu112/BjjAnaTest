package example.org.bjjanatest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DrillListAdapter extends ArrayAdapter{
    private Context context;
    private static List<Drill> drills;
    private static final String LOGTAG = "BJJTRAINING";
    private static HorizontalBarChart horizontalBarChart_Reps;
    private static int maxReps = 0;
    private static ArrayList<Integer> colors = new ArrayList<Integer>();

    public DrillListAdapter(Context context, List<Drill> drills, int maxRepsIn) {
        super(context,android.R.layout.simple_list_item_1,drills);
        this.context = context;
        this.drills = drills;
        int tempMaxReps = (int) Math.round(maxRepsIn*1.2);
        this.maxReps = tempMaxReps;

        //set colors
        colors.add(ColorTemplate.getHoloBlue());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.drill_list_item, null);

        Drill drill = drills.get(position);

        //setting the drill name
        MyTextView tv = (MyTextView) view.findViewById(R.id.drill_list_item_name);
        tv.setText(drill.getDrillName());

        //graphing the rep bar chart
        horizontalBarChart_Reps = (HorizontalBarChart) view.findViewById(R.id.drill_item_chart);
        horizontalBarChart_Reps.setDrawValueAboveBar(true);
        horizontalBarChart_Reps.setDescription("");
        horizontalBarChart_Reps.setPinchZoom(false);
        horizontalBarChart_Reps.setTouchEnabled(false);
        horizontalBarChart_Reps.getLegend().setEnabled(false);

        XAxis xl_reps = horizontalBarChart_Reps.getXAxis(); //actual data on the x axis (horizontal chart only
        xl_reps.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl_reps.setDrawGridLines(false);
        xl_reps.setEnabled(true);

        YAxis yl_reps = horizontalBarChart_Reps.getAxisLeft(); //left y axis
        yl_reps.setDrawLabels(false);
        yl_reps.setEnabled(false);
        yl_reps.setAxisMaxValue((float) maxReps);

        YAxis yr_reps = horizontalBarChart_Reps.getAxisRight(); //right y axis
        yr_reps.setDrawLabels(false);
        yr_reps.setEnabled(false);
        yr_reps.setAxisMaxValue((float) maxReps);

        //plot reps
        ArrayList<BarEntry> dataPoints_Reps = new ArrayList<BarEntry>();
        ArrayList<String> xAxisStrings = new ArrayList<String>();
        BarEntry entry;

        entry = new BarEntry ((float) drill.getDrillRepTotal(), 0);
        dataPoints_Reps.add(entry);
        xAxisStrings.add("Total Reps:");

        BarDataSet set1 = new BarDataSet(dataPoints_Reps, "Reps");
        set1.setBarSpacePercent(0f);
        set1.setColors(colors);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xAxisStrings, dataSets);
        data.setValueFormatter(new IntValueFormatter());

        horizontalBarChart_Reps.setData(data);
        horizontalBarChart_Reps.invalidate();

        return view;
    }
}
