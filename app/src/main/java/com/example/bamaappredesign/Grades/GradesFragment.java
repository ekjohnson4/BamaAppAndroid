package com.example.bamaappredesign.Grades;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bamaappredesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

public class GradesFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseUser user;
    GradesClassAdapter adapter;
    private TextView gpaText;
    private List<GradesClass> linkList = new ArrayList<>();

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        linkList.clear();
        final View inputView =  inflater.inflate(R.layout.fragment_grades, container, false);
        final String[] gpa = new String[1];

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        assert user != null;

        //Display gpa
        DocumentReference gpaRef = db.collection("gradeInformation").document(user.getUid());
        gpaRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        gpa[0] = document.getString("gpa");
                        gpaText = inputView.findViewById(R.id.gpa_text);
                        gpaText.setText("GPA: " + gpa[0]);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Display recycler view
        RecyclerView myrecyclerview = inputView.findViewById(R.id.classes);
        adapter = new GradesClassAdapter(getContext(),linkList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        System.out.println(linkList.size());
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL));

        getClasses();
        return inputView;
    }

    private void getClasses(){
        linkList.clear();
        DocumentReference classRef = db.collection("gradeInformation").document(user.getUid());
        classRef.collection("classes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                GradesClass a = new GradesClass(String.valueOf(document.get("title")));
                                linkList.add(a);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
