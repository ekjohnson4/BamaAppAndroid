package com.example.bamaappredesign;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LinksFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<Link> linkList;


    public LinksFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_links, container, false);

        myrecyclerview = (RecyclerView) v.findViewById(R.id.rvContacts);
        LinkAdapter adapter = new LinkAdapter(getContext(),linkList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(adapter);

        myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linkList = new ArrayList<>();
        linkList.add(new Link("SupeStore","https://www.universitysupplystore.com/"));
        linkList.add(new Link("BlackBoard Student","https://ualearn.blackboard.com/webapps/portal/execute/tabs/tabAction?tab_tab_group_id=_1_1"));
        linkList.add(new Link("Guardian","https://www.universitysupplystore.com/"));
        linkList.add(new Link("iTunesU","https://itunes.apple.com/us/app/itunes-u/id490217893?mt=8"));
        linkList.add(new Link("Gameday","https://uagameday.com/"));
        linkList.add(new Link("Alumni","https://alumni.ua.edu/"));
        linkList.add(new Link("WellBama","http://wellness.ua.edu/wellbama/"));
    }


}
