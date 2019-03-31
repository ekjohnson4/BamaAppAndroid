package com.example.bamaappredesign;

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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.firestore.*;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class HomeStudentFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;

    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
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
        db = FirebaseFirestore.getInstance();
        final String[] bamaCash = new String[1];
        final String[] diningDollars = new String[1];
        final String[] opponent = new String[1];
        final String[] bowl = new String[1];
        final String[] gate = new String[1];

        //Set view
        final View view = inflater.inflate(R.layout.fragment_home_student, container, false);

        //Home page slides
        viewPager = view.findViewById(R.id.viewPager);
        myCustomPagerAdapter = new MyCustomPagerAdapter(Objects.requireNonNull(getActivity()), images, strings);
        viewPager.setAdapter(myCustomPagerAdapter);

        //Tickets Card
        CardView card_view = view.findViewById(R.id.card1);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new TicketsFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //ActionCard Card
        CardView card_view2 = view.findViewById(R.id.card2);
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new ActionCardFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //News Card
        CardView card_view3 = view.findViewById(R.id.card3);
        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new NewsFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Pull data
        DocumentReference actRef = db.collection("actionCards").document(user.getUid());
        DocumentReference ticRef = db.collection("ticketInformation").document("game1");
        DocumentReference ticRef2 = db.collection("ticketInformation").document("game1").collection("stuTickets").document(user.getUid());

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

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        ticRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        opponent[0] = document.getString("opponent");
                        TextView o = view.findViewById(R.id.ticketInfo1);
                        o.setText(opponent[0]);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        ticRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        bowl[0] = document.getString("bowl");
                        gate[0] = document.getString("gate");
                        TextView bg = view.findViewById(R.id.ticketInfo2);
                        bg.setText(bowl[0] + " - " + gate[0]);

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
}

