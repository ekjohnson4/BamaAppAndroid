package com.example.bamaappredesign;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.support.constraint.Constraints.TAG;

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
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_card, container, false);
        View inputView = inflater.inflate(R.layout.fragment_card,
                container,
                false
        );
        nameText = inputView.findViewById(R.id.textview1);
        diningDollarText = inputView.findViewById(R.id.textview2);
        bamaCashText = inputView.findViewById(R.id.textview3);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        // Create a query against the collection.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("userInformation")
                .whereEqualTo("email", "tdgillam@crimson.ua.edu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nameText.setText(nameText.getText() + "" + document.get("firstName") + " " + document.get("lastName"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        DocumentReference actRef = db.collection("actionCards").document(user.getUid());
        actRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Spannable bamaCashColor = new SpannableString("$" + document.getString("bamaCash"));
                        Spannable diningDollarColor = new SpannableString("$" + document.getString("diningDollars"));
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
    public static final Spannable getColoredString(Context context, CharSequence text, int color) {
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(color), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }


}
