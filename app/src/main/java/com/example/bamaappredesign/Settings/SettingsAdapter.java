package com.example.bamaappredesign.Settings;


import android.app.Dialog;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//import java.util.ArrayList;
import com.example.bamaappredesign.Home.Module;
import com.example.bamaappredesign.R;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {

    Context mContext;
    List<Module> mData;
    Dialog myDialog;

    public SettingsAdapter(Context mContext, List<Module> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public SettingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.settings_list,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);



        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(mData.get(i).getName());
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