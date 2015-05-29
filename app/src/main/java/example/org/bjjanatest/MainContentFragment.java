package example.org.bjjanatest;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainContentFragment extends Fragment {
    public MainContentFragment (){
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_content_frag,container,false);
        return rootView;
    }
}
