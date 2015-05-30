package example.org.bjjanatest;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainContentFragment extends Fragment {
    private TextView tv;
    public MainContentFragment (){
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_content_frag,container,false);
        return rootView;
    }

    public void getStats () {
        dataSource.open(); //opens connection to the datasource
        List<Tourn> tourns = dataSource.findAll();
        if (tourns.size()!=0) {

            tv = (TextView) getView().findViewById(R.id.main_ptsScored);
            tv.setText(String.format("%d",dataSource.getTotalPts()));

            tv = (TextView) getView().findViewById(R.id.main_ptsAllowed);
            tv.setText(String.format("%d",dataSource.getTotalPtsAllowed()));

            tv = (TextView) getView().findViewById(R.id.main_tdPerc);
            tv.setText(String.format("%2.2f",dataSource.getTdSucPerc()));

            tv = (TextView) getView().findViewById(R.id.main_passperc);
            tv.setText(String.format("%2.2f",dataSource.getPassSucPerc()));

            tv = (TextView) getView().findViewById(R.id.main_swpPerc);
            tv.setText(String.format("%2.2f",dataSource.getSweepSucPerc()));

            tv = (TextView) getView().findViewById(R.id.main_subPerc);
            tv.setText(String.format("%2.2f",dataSource.getSubSucPerc()));

            tv = (TextView) getView().findViewById(R.id.main_avgMatchTime);
            tv.setText(String.format("%5.2f",dataSource.getAvgMatchTime()));

            tv = (TextView) getView().findViewById(R.id.main_numTourn);
            tv.setText(String.format("%d",dataSource.getTournLen()));
        }
        else {
            Toast toast;
            toast=Toast.makeText(this, "No tournament data found", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
