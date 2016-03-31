package com.analytics.bjj;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import com.analytics.bjj.db.DrillDataSource;
import com.analytics.bjj.pro.GlobConstants;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class DrillListFragment extends ListFragment{

    private static DrillDataSource dataSource;
    private List<Drill> drills;
    private static int MAX_DRILL_DATABASE_ROWS = 49;



    public DrillListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobConstants drillLimitObj = new GlobConstants();
        MAX_DRILL_DATABASE_ROWS = drillLimitObj.getDrillLimit();

        dataSource = new DrillDataSource(getActivity());
        dataSource.open();
        setHasOptionsMenu(true);
        refreshDisplay();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_drill, menu);
        menu.getItem(0).setIcon(R.drawable.plus_50);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drill_add:
                //Intent intent = new Intent(getActivity(),AddDrill.class);
                //startActivity(intent);

                //setting up alert dialog
                LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.alert_dialog_adddrill, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                // set alert_dialog_tourn.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final MyEditText ev = (MyEditText) promptsView.findViewById(R.id.alertDialog_DrillName);
                final MyEditText ev1 = (MyEditText) promptsView.findViewById(R.id.alertDialog_NumReps);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Drill drill = new Drill();

                                        drill.setDrillName(ev.getText().toString());

                                        String repTotalStr = "0";
                                        repTotalStr = ev1.getText().toString();
                                        int addedReps = 0;

                                        repTotalStr = repTotalStr.replaceAll("[^\\d]", "");
                                        if (repTotalStr.equals("") || repTotalStr.equals(".") || repTotalStr.equals(",")) {
                                            repTotalStr = "0";
                                        }
                                        try {
                                            addedReps = Integer.parseInt(repTotalStr);
                                        } catch (NumberFormatException ex) {
                                            System.err.println("Caught NumberFormatException in EditDrill fragment: "
                                                    + ex.getMessage());
                                        }
                                        drill.setDrillRepTotal(addedReps);


                                        if (dataSource.getDrillLen() <= MAX_DRILL_DATABASE_ROWS) { //limit the number of saved drills
                                            dataSource.create(drill);
                                            refreshDisplay();
                                        } else {
                                            Toast.makeText(getActivity(), "Drill limit reached. Please buy Pro version to unlock.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


                break;

            case R.id.drill_del:
                dataSource.removeAllDrills();
                refreshDisplay();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

//        Intent intent = new Intent(getActivity(),EditDrill.class);
//        Drill drill;
//        drill = drills.get(pos);
//        intent.putExtra("drill_obj_edit",drill); //this name should be the same as the getparcelable name
//        startActivity(intent);

        final int posFinal = pos;

        //setting up alert dialog
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.alert_dialog_editdrill, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set alert_dialog_tourn.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText ev = (EditText) promptsView.findViewById(R.id.alertDialog_editDrillName);
        final MyEditText ev1 = (MyEditText) promptsView.findViewById(R.id.alertDialog_editNumReps);
        Drill drillTemp;
        drillTemp = drills.get(pos);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Drill drill = drills.get(posFinal);


                                String repTotalStr = "0";
                                int addedReps = 0;
                                repTotalStr = ev1.getText().toString();
                                repTotalStr = repTotalStr.replaceAll("[^\\d]", ""); //remove any non-numeric characters
                                if (repTotalStr.equals("") || repTotalStr.equals(".") || repTotalStr.equals("..") || repTotalStr.equals(",")) {
                                    repTotalStr = "0";
                                }
                                try {
                                    addedReps = Integer.parseInt(repTotalStr);
                                } catch (NumberFormatException ex) {
                                    System.err.println("Caught NumberFormatException in EditDrill fragment: "
                                            + ex.getMessage());
                                }
                                drill.setDrillRepTotal(drill.getDrillRepTotal() + addedReps);

                                //update name
                                drill.setDrillName(ev.getText().toString());

                                //update the row in the database
                                dataSource.update(drill);
                                refreshDisplay();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Drill drill = drills.get(posFinal);
                dataSource.removeFromDrills(drill);
                refreshDisplay();
            }
        });
        ev.setText(drillTemp.getDrillName());
        // show it
        alertDialog.show();

    }

    public void refreshDisplay() {
        ArrayAdapter<Tourn> adapter = new DrillListAdapter(getActivity(), getDrills(), dataSource.getMaxReps());
        setListAdapter(adapter);
    }

    public List<Drill> getDrills() {
        drills = dataSource.findAll();
        return drills;
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
        refreshDisplay();
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

}
