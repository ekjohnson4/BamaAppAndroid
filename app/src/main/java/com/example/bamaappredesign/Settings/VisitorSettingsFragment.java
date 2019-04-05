package com.example.bamaappredesign.Settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bamaappredesign.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisitorSettingsFragment extends Fragment {


    public VisitorSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visitor_settings, container, false);
    }

}
