package com.example.bamaappredesign.CourseCatalog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bamaappredesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CatalogFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseFirestore rootRef;
    Bundle args = new Bundle();
    Spinner spinner;
    TextView description;

    public CatalogFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_catalog, container, false);
        description = v.findViewById(R.id.catalogText);

        //Initialize variables
        db = FirebaseFirestore.getInstance();
        spinner = v.findViewById(R.id.subjects);
        final List<String> semester = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, semester);

        //Retrieve terms
        rootRef = FirebaseFirestore.getInstance();
        CollectionReference semesterRef = rootRef.collection("courseCatalog");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        semesterRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String term = document.getId();
                        semester.add(term);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Button submit = v.findViewById(R.id.catalogButton);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                retrieveSubjects(v);
            }
        });
        return v;
    }

    void retrieveSubjects(View v){
        String selected = spinner.getSelectedItem().toString();
        args.putString("term" , selected);
        final CollectionReference subjectsRef = rootRef.collection("courseCatalog").document(selected).collection("subject");

        final List<String> subjects = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        description.setText("Please select a subject.");

        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String abb = document.getId();
                        subjects.add(abb);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Button submit = v.findViewById(R.id.catalogButton);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                retrieveCourses(v, subjectsRef);
            }
        });
    }

    void retrieveCourses(View v, CollectionReference subjectsRef) {
        String selected = spinner.getSelectedItem().toString();
        args.putString("subject" , selected);
        final CollectionReference coursesRef = subjectsRef.document(selected).collection("courses");

        final List<String> subjects = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        description.setText("Please select a course.");

        coursesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String abb = document.getId();
                        subjects.add(abb);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Button submit = v.findViewById(R.id.catalogButton);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                retrieveInfo();
            }
        });
    }

    void retrieveInfo(){
        String selected = spinner.getSelectedItem().toString();

        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        CourseFragment fragment = new CourseFragment();
        args.putString("course" , selected);
        fragment.setArguments(args);
        ft.replace(R.id.flMain, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
