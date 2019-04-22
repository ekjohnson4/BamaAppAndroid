package com.example.bamaappredesign.Schedule;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bamaappredesign.R;

import java.util.List;

public class ScheduleClassAdapter extends RecyclerView.Adapter<ScheduleClassAdapter.MyViewHolder> {
    Context mContext;
    List<ScheduleClass> mData;

    public ScheduleClassAdapter(Context mContext, List<ScheduleClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ScheduleClassAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.schedule_class_list,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleClassAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.cls.setText(mData.get(i).getCls());
        myViewHolder.time.setText(mData.get(i).getTime());
        myViewHolder.days.setText(mData.get(i).getDays());
        myViewHolder.prof.setText(mData.get(i).getProf());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView cls;
        private TextView days;
        private TextView time;
        private TextView prof;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.contact_item);
            cls = itemView.findViewById(R.id.cls);
            days = itemView.findViewById(R.id.days);
            time = itemView.findViewById(R.id.time);
            prof = itemView.findViewById(R.id.prof);
        }
    }
}