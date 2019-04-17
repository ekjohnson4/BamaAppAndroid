package com.example.bamaappredesign.Settings;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//import java.util.ArrayList;
import com.example.bamaappredesign.Home.Module;
import com.example.bamaappredesign.Home.ModuleHomeAdapter;
import com.example.bamaappredesign.Home.ModuleVisitorAdapter;
import com.example.bamaappredesign.R;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {

    Context mContext;
    List<Module> mData;
    Module moduleOne;
    Module moduleTwo;
    Module moduleThree;
    Module moduleFour;

    public SettingsAdapter(Context mContext, List<Module> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public SettingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.settings_list,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        ModuleHomeAdapter modAdapter = new ModuleHomeAdapter();
        moduleOne = modAdapter.getModuleOne();
        moduleTwo = modAdapter.getModuleTwo();
        ModuleVisitorAdapter modAdapterTwo = new ModuleVisitorAdapter();
        moduleThree = modAdapterTwo.getModuleOne();
        moduleFour = modAdapterTwo.getModuleTwo();
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(mData.get(i).getName());
        Spannable greenTitle = new SpannableString(mData.get(i).getName());
        greenTitle.setSpan(new ForegroundColorSpan(Color.rgb(26, 117, 37)), 0, greenTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if(mData.get(i).getName().equals(moduleOne.getName()) || mData.get(i).getName().equals(moduleTwo.getName())){
            //System.out.println("found one");
            myViewHolder.tv_title.setText(greenTitle);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item;
        private TextView tv_title;
        private TextView tv_description;
        private TextView tv_location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.title);
        }

    }
}