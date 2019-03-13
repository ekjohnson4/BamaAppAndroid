package com.example.bamaappredesign;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeStudentFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    ViewFlipper viewFlipper;
    FragmentManager fragmentManager;
    int gallery_grid_Images[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    public HomeStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //Set view
        final View view = inflater.inflate(R.layout.fragment_home_student, container, false);

        //Display user's email
        //TextView frv = view.findViewById(R.id.welcomeText);
        //frv.setText("Welcome, " + user.getEmail() + "!");

        //Set slide-show
        viewFlipper = view.findViewById(R.id.viewflip);
        for(int i=0; i<gallery_grid_Images.length; i++){
            // This will create dynamic image views and add them to the ViewFlipper.
            setFlipperImage(gallery_grid_Images[i]);
        }

        return view;
    }

    private void setFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageView image = new ImageView(getContext());
        image.setBackgroundResource(res);
        viewFlipper.addView(image);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }
}
