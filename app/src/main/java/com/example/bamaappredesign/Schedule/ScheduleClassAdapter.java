package com.example.bamaappredesign.Schedule;

import android.content.Context;
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
    private Context mContext;
    private List<ScheduleClass> mData;

    ScheduleClassAdapter(Context mContext, List<ScheduleClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ScheduleClassAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.schedule_class_list,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleClassAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.cls.setText(mData.get(i).getCls());
        myViewHolder.time.setText(mData.get(i).getTime());
        myViewHolder.days.setText(mData.get(i).getDays());
        myViewHolder.prof.setText(mData.get(i).getProf());
        myViewHolder.location.setText(mData.get(i).getLocation());

        //If class is online, just ignore location
        if (mData.get(i).getLocation().equals("")){
            myViewHolder.location.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView cls;
        private TextView days;
        private TextView time;
        private TextView prof;
        private TextView location;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.contact_item);
            cls = itemView.findViewById(R.id.cls);
            days = itemView.findViewById(R.id.days);
            time = itemView.findViewById(R.id.time);
            prof = itemView.findViewById(R.id.prof);
            location = itemView.findViewById(R.id.loc);
        }
    }
}