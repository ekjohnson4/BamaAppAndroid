package com.example.bamaappredesign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
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
    String strings[] = {"Headline 1", "Headline 2","Headline 3"};

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
        myCustomPagerAdapter = new MyCustomPagerAdapter(Objects.requireNonNull(getActivity()), images, strings);
        viewPager.setAdapter(myCustomPagerAdapter);

        //Events Card
        CardView card_view = view.findViewById(R.id.card4);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new EventsFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Shop Card
        CardView card_view2 = view.findViewById(R.id.card5);
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.universitysupplystore.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //News Card
        CardView card_view3 = view.findViewById(R.id.card6);
        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new NewsFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}

