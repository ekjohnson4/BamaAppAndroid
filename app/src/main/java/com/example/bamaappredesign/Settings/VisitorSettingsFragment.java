package com.example.bamaappredesign.Settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bamaappredesign.Events.Event;
import com.example.bamaappredesign.Events.EventsAdapter;
import com.example.bamaappredesign.Home.Module;
import com.example.bamaappredesign.Home.ModuleType;
import com.example.bamaappredesign.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisitorSettingsFragment extends Fragment {
    private RecyclerView myrecyclerview;
    private List<Module> linkList = new ArrayList<>();
    private SettingsAdapter adapter;


    public VisitorSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visitor_settings, container, false);
        // Execute DownloadXML AsyncTask
        myrecyclerview = (RecyclerView) v.findViewById(R.id.rvSettings);
        adapter = new SettingsAdapter(getContext(),linkList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        System.out.println(linkList.size());
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        setVisitorModules();
        return v;
    }

    private void setVisitorModules(){
        for (Module m : Module.values()) {
            if(m.getType() == ModuleType.VISITOR) {
                linkList.add(m);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
