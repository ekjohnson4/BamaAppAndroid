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

import java.util.ArrayList;
import java.util.List;


class Contact {

    private String Name;
    private String Phone;


    public Contact() {
    }

    public Contact(String name, String phone) {//maybe int for phone?
        Name = name;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    //settter

    public void setName(String name) {
        Name = name;
    }

    /*public void setPhone(String phone) {
        Phone = phone;
    }*/
}





/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment {//implements EmergencyAdapter.ItemClickListener {

    View v;
    private RecyclerView myrecyclerview;
    private List<Contact> listContact;


    public EmergencyFragment() {
        // Required empty public constructor
    }

    //EmergencyAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_emergency, container, false);

        myrecyclerview = (RecyclerView) v.findViewById(R.id.rvContacts);
        EmergencyAdapter adapter = new EmergencyAdapter(getContext(),listContact);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(adapter);

        myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


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
       // listContact.add(new Contact("Michael Ahern","1111111111"));
    }
}



        /*
        ArrayList<String> names = new ArrayList<>();
        names.add("University Police");
        names.add("Tuscaloosa Police");
        names.add("Tuscaloosa County Sheriff's Office");
        names.add("Northport Police");
        names.add("348-RIDE (after hours)");
        names.add("MAP (business hours)");
        names.add("MAP (after hours)");
        names.add("University Operator");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
*/
     /*   RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new EmergencyAdapter(getActivity(), names);

        //adapter.setClickListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


     //   adapter.setClickListener(getActivity());


        recyclerView.setAdapter(adapter);


        return view;
    }

   // @Override
    //public void onItemClick(View view, int position) {
      //  Toast.makeText(getActivity(), new StringBuilder().append("You clicked ").append(adapter.getItem(position)).append(" on row number ").append(position).toString(), Toast.LENGTH_SHORT).show();
   //}

}*/



