package com.example.bamaappredesign;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActionCardFragment extends Fragment
{
    private TextView nameText;
    private TextView diningDollarText;
    private TextView bamaCashText;
    private FirebaseAuth auth;
    private FirebaseUser user;

    // Create a reference to the cities collection
    public ActionCardFragment()
    {
        //required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final String[] fname = new String[1];
        final String[] lname = new String[1];

        //Set view
        final View inputView = inflater.inflate(R.layout.fragment_card, container, false);

        //Set TextViews
        nameText = inputView.findViewById(R.id.textview1);
        diningDollarText = inputView.findViewById(R.id.textview2);
        bamaCashText = inputView.findViewById(R.id.textview3);

        // Create a query against the collection.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference nameRef = db.collection("studentInformation").document(user.getUid());

        //Display name
        nameRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        fname[0] = document.getString("fname");
                        lname[0] = document.getString("lname");
                        TextView n = inputView.findViewById(R.id.textview1);
                        n.setText(fname[0] + " " + lname[0]);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Display monetary information
        DocumentReference actRef = db.collection("actionCards").document(user.getUid());
        actRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Spannable bamaCashColor = new SpannableString("\n$" + document.getString("bamaCash") + "    ");
                        Spannable diningDollarColor = new SpannableString("\n$" + document.getString("diningDollars"));
                        diningDollarColor.setSpan(new ForegroundColorSpan(Color.rgb(26, 117, 37)), 0, diningDollarColor.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        bamaCashColor.setSpan(new ForegroundColorSpan(Color.rgb(26, 117, 37)), 0, bamaCashColor.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        bamaCashText.append(bamaCashColor);
                        diningDollarText.append(diningDollarColor);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
        return inputView;
    }

}
