package com.example.bamaappredesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class HomeStudentFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
    private EditText mEditText;
    //private List<RssFeedModel> mFeedModelList;
    int images[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    String strings[] = {"Headline 1", "Headline 2","Headline 3"};

    public HomeStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //Set view
        final View view = inflater.inflate(R.layout.fragment_home_student, container, false);

        //Display user's email
        //TextView frv = view.findViewById(R.id.welcomeText);
        //frv.setText("Welcome, " + user.getEmail() + "!");

        //Home page slides
        viewPager = view.findViewById(R.id.viewPager);
        myCustomPagerAdapter = new MyCustomPagerAdapter(Objects.requireNonNull(getActivity()), images, strings);
        viewPager.setAdapter(myCustomPagerAdapter);

        //mEditText =  view.findViewById(R.id.textView);
        //new FetchFeedTask().execute((Void) null);

        return view;
    }
}

