package com.example.bamaappredesign.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.bamaappredesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WebViewFragment extends Fragment {
    private WebView mWebView;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    String strings[] = {"","",""};

    public WebViewFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        final View view =  inflater.inflate(R.layout.fragment_campus_map, container, false);
        mWebView = view.findViewById(R.id.campus_map_webview);

        // Create a query against the collection.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert user != null;
        DocumentReference slideRef = db.collection("homePage").document("slides");

        Bundle arguments = getArguments();
        assert arguments != null;
        final int position = arguments.getInt("pos");

        //Display slides
        slideRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        strings[0] = document.getString("slide1link");
                        strings[1] = document.getString("slide2link");
                        strings[2] = document.getString("slide3link");

                        WebSettings webSettings = mWebView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        mWebView.loadUrl(strings[position]);
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
