package com.example.bamaappredesign.CourseCatalog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bamaappredesign.R;

public class CourseFragment extends Fragment {
    TextView c;

    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_course, container, false);
        c = v.findViewById(R.id.courseText);

        Bundle arguments = getArguments();
        assert arguments != null;
        final String course = arguments.getString("course");

        c.setText(course);

        return v;
    }

}