package com.example.bamaappredesign.Emergency;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bamaappredesign.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmergencyFragment extends Fragment {
    View v;
    private List<Contact> listContact;

    public EmergencyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_emergency, container, false);

        RecyclerView myrecyclerview = v.findViewById(R.id.rvContacts);
        EmergencyAdapter adapter = new EmergencyAdapter(getContext(),listContact);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(adapter);

        myrecyclerview.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listContact = new ArrayList<>();
        listContact.add(new Contact("University Police","2053485454"));
        listContact.add(new Contact("Tuscaloosa Police","2053492121"));
        listContact.add(new Contact("Tuscaloosa County Sheriff's Office","2054648672"));
        listContact.add(new Contact("Northport Police","205339660"));
        listContact.add(new Contact("348-RIDE (after hours)","2053457433"));
        listContact.add(new Contact("MAP (business hours)","2053480121"));
        listContact.add(new Contact("MAP (after hours)","205348994"));
        listContact.add(new Contact("University Operator","2053486010"));
    }
}