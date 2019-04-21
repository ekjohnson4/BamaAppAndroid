package com.example.bamaappredesign.CourseCatalog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bamaappredesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CourseFragment extends Fragment {
    TextView c;
    FirebaseFirestore db;

    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_course, container, false);
        c = v.findViewById(R.id.courseText);
        db = FirebaseFirestore.getInstance();

        Bundle arguments = getArguments();
        assert arguments != null;
        final String term = arguments.getString("term");
        final String subject = arguments.getString("subject");
        final String course = arguments.getString("course");

        c.setText(course);

        assert term != null;
        assert subject != null;
        assert course != null;
        final DocumentReference courseRef = db.collection("courseCatalog").document(term).collection("subject").document(subject).collection("courses").document(course);

        //Display info
        courseRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        TextView d = v.findViewById(R.id.courseDescription);
                        d.setText(document.getString("description"));
                        TextView p = v.findViewById(R.id.prereq);
                        p.setText(document.getString("prereq"));
                        TextView pc = v.findViewById(R.id.prereqc);
                        pc.setText(document.getString("prereqc"));
                        TextView h = v.findViewById(R.id.hours);
                        h.setText("Hours: " + document.getString("hours"));
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        return v;
    }

}