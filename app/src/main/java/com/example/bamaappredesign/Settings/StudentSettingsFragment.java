package com.example.bamaappredesign.Settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.bamaappredesign.Home.Module;
import com.example.bamaappredesign.Home.ModuleHomeAdapter;
import com.example.bamaappredesign.Home.ModuleVisitorAdapter;
import com.example.bamaappredesign.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSettingsFragment extends Fragment {
    private RecyclerView myrecyclerview;
    private SettingsAdapter adapter;
    private ArrayList<String> linkList = new ArrayList<>();
    Module moduleOne;
    Module moduleTwo;

    public StudentSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visitor_settings, container, false);
        // Spinner element
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        Spinner spinner1 = (Spinner) v.findViewById(R.id.spinner1);
        setStudentModules();
        // Spinner click listener
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, linkList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        ModuleHomeAdapter a = new ModuleHomeAdapter();
        moduleOne = a.getModuleOne();
        moduleTwo = a.getModuleTwo();
        spinner.setSelection(linkList.indexOf(moduleOne.getName()));
        spinner1.setSelection(linkList.indexOf(moduleTwo.getName()));
        return v;
    }

    private void setStudentModules(){
        for (Module m : Module.values()) {
            linkList.add(m.getName());
        }
    }
}
