package com.example.bamaappredesign.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.google.firebase.firestore.*;

import java.io.InputStream;
import java.util.Objects;

public class HomeStudentFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;

    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
    String[] images = {"", "", ""};
    String[] strings = {"", "", ""};
    SharedPreferences sharedPref;
    TextView modOne;
    TextView modTwo;
    ImageView imageOne;
    ImageView imageTwo;

    Module moduleOne = Module.TICKETS;
    Module moduleTwo = Module.ACTION_CARD;

    public HomeStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        final String[] bamaCash = new String[1];
        final String[] diningDollars = new String[1];
        final String[] opponent = new String[1];
        final String[] bowl = new String[1];
        final String[] gate = new String[1];
        final String[] url = new String[1];
        final boolean[] ticket = new boolean[1];

        //Set view
        final View view = inflater.inflate(R.layout.fragment_home_student, container, false);
        Context context = getActivity();

        // Create a query against the collection.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert user != null;
        DocumentReference slideRef = db.collection("homePage").document("slides");

        //Display slides
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
                    try {
                        assert getFragmentManager() != null;
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        viewPager = view.findViewById(R.id.viewPager);
                        myCustomPagerAdapter = new MyCustomPagerAdapter(Objects.requireNonNull(getActivity()), images, strings, ft);
                        viewPager.setAdapter(myCustomPagerAdapter);
                    }
                    catch(Exception e){
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

        //Get selected modules
        assert context != null;
        sharedPref = Objects.requireNonNull(context).getSharedPreferences(
                "modules", Context.MODE_PRIVATE);
        if(!Objects.equals(sharedPref.getString("modThree", "null"), "null")){
            Module temp = getModule(sharedPref.getString("modThree", "null"));
            if(temp!=null){
                moduleOne = temp;
                System.out.println("Set module one to " + temp.getName());
            }
        }
        if(!Objects.equals(sharedPref.getString("modFour", "null"), "null")){
            Module temp = getModule(sharedPref.getString("modFour", "null"));
            if(temp!=null){
                moduleTwo = temp;
                System.out.println("Set module two to " + temp.getName());
            }
        }
        modOne = view.findViewById(R.id.modOne);
        modTwo = view.findViewById(R.id.modTwo);
        modOne.setText(moduleOne.getName());
        modTwo.setText(moduleTwo.getName());
        imageOne = view.findViewById(R.id.ticketImg);
        imageTwo = view.findViewById(R.id.card_image2);
        if(moduleOne.getImage() != 0) {
            imageOne.setImageResource(moduleOne.getImage());
        }
        if(moduleTwo.getImage() != 0){
            imageTwo.setImageResource(moduleTwo.getImage());
        }

        //Module 1
        CardView card_view = view.findViewById(R.id.card1);
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
                assert getFragmentManager() != null;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, moduleOne.getFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
            }
        });

        //Module 2
        CardView card_view2 = view.findViewById(R.id.card2);
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            System.out.println("Opening module one");
            if(moduleTwo == Module.SHOPPING) {
                Uri uri = Uri.parse("https://www.universitysupplystore.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            else{
                assert getFragmentManager() != null;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, moduleTwo.getFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
            }
        });

        //News Module
        CardView card_view3 = view.findViewById(R.id.card3);
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

        //Pull data
        DocumentReference actRef = db.collection("actionCards").document(user.getUid());
        DocumentReference ticRef = db.collection("ticketInformation").document("game1");
        DocumentReference ticRef2 = db.collection("ticketInformation").document("game1").collection("stuTickets").document(Objects.requireNonNull(user.getEmail()));
        DocumentReference urlRef = db.collection("actionCards").document(user.getUid());

        //Display action card image
        urlRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    url[0] = document.getString("image2");

                    //Display action card
                    if(moduleTwo == Module.ACTION_CARD) {
                        new HomeStudentFragment.DownloadImageTask((ImageView) view.findViewById(R.id.card_image2))
                                .execute(url[0]);
                    }
                    if(moduleOne == Module.TICKETS){
                        new HomeStudentFragment.DownloadImageTask((ImageView) view.findViewById(R.id.ticketImg))
                                .execute("https://firebasestorage.googleapis.com/v0/b/bama-app.appspot.com/o/ticket.png?alt=media&token=9c8f2a78-314e-499c-ab3e-e216f1ca2eb0");
                    }
                } else {
                    Log.d("LOGGER", "No such document");
                }
            } else {
                Log.d("LOGGER", "get failed with ", task.getException());
            }
            }
        });

        //Display monetary data
        actRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        bamaCash[0] = document.getString("bamaCash");
                        diningDollars[0] = document.getString("diningDollars");
                        TextView bc = view.findViewById(R.id.actionInfo1);
                        bc.setText("Bama Cash: $" + bamaCash[0]);
                        TextView dd = view.findViewById(R.id.actionInfo2);
                        dd.setText("Dining Dollars: $" + diningDollars[0]);
                        bc.setVisibility(View.VISIBLE);
                        dd.setVisibility(View.VISIBLE);
                        if(moduleTwo != Module.ACTION_CARD){
                            bc.setVisibility(View.GONE);
                            dd.setVisibility(View.GONE);
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Display opponent
        ticRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        opponent[0] = document.getString("opponent");
                        TextView o = view.findViewById(R.id.ticketInfo1);
                        o.setText(opponent[0]);
                        o.setVisibility(View.VISIBLE);
                        if(moduleOne != Module.TICKETS){
                            o.setVisibility(View.GONE);
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Display bowl and gate
        ticRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.getBoolean("ticket") == false) {
                            TextView bg = view.findViewById(R.id.ticketInfo2);
                            bg.setText("No ticket");
                            bg.setVisibility(View.VISIBLE);
                        }
                        else{
                            bowl[0] = document.getString("bowl");
                            gate[0] = document.getString("gate");
                            TextView bg = view.findViewById(R.id.ticketInfo2);
                            bg.setText(bowl[0] + " - " + gate[0]);
                            bg.setVisibility(View.VISIBLE);
                        }
                        if(moduleOne != Module.TICKETS){
                            TextView bg = view.findViewById(R.id.ticketInfo2);
                            bg.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
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

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

