package com.example.bamaappredesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class HomeVisitorFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
    int images[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    public HomeVisitorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //Set view
        final View view = inflater.inflate(R.layout.fragment_home_visitor, container, false);

        //Display user's email
        //TextView frv = view.findViewById(R.id.welcomeText);
        //frv.setText("Welcome, " + user.getEmail() + "!");

        //Home page slides
        viewPager = view.findViewById(R.id.viewPager);
        myCustomPagerAdapter = new MyCustomPagerAdapter(Objects.requireNonNull(getActivity()), images);
        viewPager.setAdapter(myCustomPagerAdapter);

        return view;
    }
}

