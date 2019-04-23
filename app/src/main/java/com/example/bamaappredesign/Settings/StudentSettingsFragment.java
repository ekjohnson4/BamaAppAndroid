package com.example.bamaappredesign.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.bamaappredesign.Home.HomeStudentFragment;
import com.example.bamaappredesign.Home.Module;
import com.example.bamaappredesign.Home.ModuleHomeAdapter;
import com.example.bamaappredesign.R;

import java.util.ArrayList;

public class StudentSettingsFragment extends SettingsInterface {
    private ArrayList<String> linkList = new ArrayList<>();
    private ArrayList<String> linkList1 = new ArrayList<>();
    Module moduleOne;
    Module moduleTwo;
    Spinner spinner;
    Spinner spinner1;
    SharedPreferences sharedPref;
    ModuleHomeAdapter modHomeAdapter;

    public StudentSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visitor_settings, container, false);

        // Spinner element
        spinner = v.findViewById(R.id.spinner);
        spinner1 = v.findViewById(R.id.spinner1);
        setStudentModulesOne();
        Context context = getActivity();
        assert context != null;
        sharedPref = context.getSharedPreferences(
                "modules", Context.MODE_PRIVATE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, linkList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, linkList1);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        setStudentModulesTwo();
        spinner1.setAdapter(adapter1);
        modHomeAdapter = new ModuleHomeAdapter();
        moduleOne = modHomeAdapter.getModuleOne();
        moduleTwo = modHomeAdapter.getModuleTwo();
        Button save =  v.findViewById(R.id.button);
        spinner.setSelection(linkList.indexOf(moduleOne.getName()));
        spinner1.setSelection(linkList1.indexOf(moduleTwo.getName()));
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeModulesToDatabase();
            }
        });
        return v;
    }

    private void setStudentModulesOne(){
        for (Module m : Module.values()) {
            if(m != Module.NEWS && m != Module.ACTION_CARD) {
                linkList.add(m.getName());
            }
        }
    }
    private void setStudentModulesTwo(){
        for (Module m : Module.values()) {
            if(m != Module.NEWS && m != Module.TICKETS) {
                linkList1.add(m.getName());
            }
        }
    }

    void writeModulesToDatabase(){
        String a = spinner.getSelectedItem().toString();
        String b = spinner1.getSelectedItem().toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("modThree", a);
        editor.putString("modFour", b);
        editor.apply();
        modHomeAdapter.setModuleOne(getModule(a));
        modHomeAdapter.setModuleTwo(getModule(b));

        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flMain, new HomeStudentFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}
