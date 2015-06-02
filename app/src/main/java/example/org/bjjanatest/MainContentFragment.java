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

import example.org.bjjanatest.db.TournDataSource;

public class MainContentFragment extends Fragment {
    private TextView tv;

    //database related
    TournDataSource dataSource;

    public MainContentFragment (){
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_content_frag,container,false);
        dataSource = new TournDataSource(getActivity());
        return rootView;
    }

    public void getStats () {
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
            toast=Toast.makeText(getActivity(), "No tournament data found", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dataSource.open(); //opens connection to the datasource
        getStats();
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
