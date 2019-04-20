package com.example.bamaappredesign.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bamaappredesign.Home.Module;
import com.example.bamaappredesign.Home.ModuleType;
import com.example.bamaappredesign.Home.ModuleVisitorAdapter;
import com.example.bamaappredesign.R;

import java.util.ArrayList;

public class VisitorSettingsFragment extends SettingsInterface {
    private ArrayList<String> linkList = new ArrayList<>();
    Module moduleOne;
    Module moduleTwo;
    SharedPreferences sharedPref;
    Spinner spinner;
    ModuleVisitorAdapter modVisitorAdapter;
    Spinner spinner1;

    public VisitorSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visitor_settings, container, false);

        // Spinner element
        spinner = v.findViewById(R.id.spinner);
        spinner1 = v.findViewById(R.id.spinner1);
        setVisitorModules();
        Context context = getActivity();
        assert context != null;
        sharedPref = context.getSharedPreferences(
                "modules", Context.MODE_PRIVATE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, linkList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        modVisitorAdapter = new ModuleVisitorAdapter();
        moduleOne = modVisitorAdapter.getModuleOne();
        moduleTwo = modVisitorAdapter.getModuleTwo();
        spinner.setSelection(linkList.indexOf(moduleOne.getName()));
        spinner1.setSelection(linkList.indexOf(moduleTwo.getName()));
        Button save =  v.findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeModulesToDatabase();
            }
        });
        return v;
    }

    private void setVisitorModules(){
        for (Module m : Module.values()) {
            if(m.getType() == ModuleType.VISITOR) {
                linkList.add(m.getName());
            }
        }
    }

    void writeModulesToDatabase(){
        String a = spinner.getSelectedItem().toString();
        String b = spinner1.getSelectedItem().toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("modOne", a);
        editor.putString("modTwo", b);
        editor.apply();
        modVisitorAdapter.setModuleOne(getModule(a));
        modVisitorAdapter.setModuleTwo(getModule(b));
        Toast.makeText(getActivity(),"Saved favorites.", Toast.LENGTH_SHORT).show();
        //spinner.setSelection(linkList.indexOf(a));
        //spinner1.setSelection(linkList.indexOf(b));
    }
}
