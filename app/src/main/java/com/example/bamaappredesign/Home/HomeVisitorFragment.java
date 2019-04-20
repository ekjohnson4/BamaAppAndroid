package com.example.bamaappredesign.Home;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bamaappredesign.News.NewsFragment;
import com.example.bamaappredesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class HomeVisitorFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    ViewPager viewPager;
    Module moduleOne = Module.EVENTS;
    Module moduleTwo = Module.SHOPPING;
    Module moduleThree = Module.NEWS;
    MyCustomPagerAdapter myCustomPagerAdapter;
    String images[] = {"","",""};
    String strings[] = {"", "",""};
    SharedPreferences sharedPref;
    TextView modOne;
    TextView modTwo;
    ImageView imageOne;
    ImageView imageTwo;

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
        Context context = getActivity();
        sharedPref = context.getSharedPreferences(
                "modules", Context.MODE_PRIVATE);
        if(!sharedPref.getString("modOne", "null").equals("null")){
            Module temp = getModule(sharedPref.getString("modOne", "null"));
            if(temp!=null){
                moduleOne = temp;
                System.out.println("Set module one to " + temp.getName());
            }
        }
        if(!sharedPref.getString("modTwo", "null").equals("null")){
            Module temp = getModule(sharedPref.getString("modTwo", "null"));
            if(temp!=null){
                moduleTwo = temp;
                System.out.println("Set module two to " + temp.getName());
            }
        }
        modOne = view.findViewById(R.id.modOne);
        modTwo = view.findViewById(R.id.modTwo);
        imageOne = view.findViewById(R.id.imageOne);
        imageTwo = view.findViewById(R.id.imageTwo);
        modOne.setText(moduleOne.getName());
        modTwo.setText(moduleTwo.getName());
        if(moduleOne.getImage() != 0) {
            imageOne.setImageResource(moduleOne.getImage());
        }
        if(moduleTwo.getImage() != 0){
            imageTwo.setImageResource(moduleTwo.getImage());
        }
        // Create a query against the collection.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
       // assert user != null;
        DocumentReference slideRef = db.collection("homePage").document("slides");

        //Display action card image
        slideRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        strings[0] = document.getString("slide1Header");
                        strings[1] = document.getString("slide2Header");
                        strings[2] = document.getString("slide3Header");

                        images[0] = document.getString("slide1");
                        images[1] = document.getString("slide2");
                        images[2] = document.getString("slide3");

                        //Home page slides
//                        assert getFragmentManager() != null;
                        try {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            viewPager = view.findViewById(R.id.viewPager);
                            myCustomPagerAdapter = new MyCustomPagerAdapter(Objects.requireNonNull(getActivity()), images, strings, ft);
                            viewPager.setAdapter(myCustomPagerAdapter);
                        }
                        catch(NullPointerException e){
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Events Card
        CardView card_view = view.findViewById(R.id.card4);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Opening module one");
                if(moduleOne == Module.SHOPPING) {
                    Uri uri = Uri.parse("https://www.universitysupplystore.com/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else{
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.flMain, moduleOne.getFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        //Shop Card
        CardView card_view2 = view.findViewById(R.id.card5);
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(moduleTwo == Module.SHOPPING) {
                    Uri uri = Uri.parse("https://www.universitysupplystore.com/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else{
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.flMain, moduleTwo.getFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        //News Card
        CardView card_view3 = view.findViewById(R.id.card6);
        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new NewsFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    Module getModule(String mod){
        for(Module m : Module.values()){
            if(m.getName().equals(mod)){
                return m;
            }
        }
        return null;
    }

}

