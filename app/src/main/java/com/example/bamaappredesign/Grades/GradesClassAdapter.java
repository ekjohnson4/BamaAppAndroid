package com.example.bamaappredesign.Grades;

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

public class GradesClassAdapter extends RecyclerView.Adapter<GradesClassAdapter.MyViewHolder> {
    Context mContext;
    List<GradesClass> mData;

    public GradesClassAdapter(Context mContext, List<GradesClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public GradesClassAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.grades_class_list,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GradesClassAdapter.MyViewHolder myViewHolder, int i) {
        double temp = mData.get(i).getGrd();
        myViewHolder.cls.setText(mData.get(i).getCls());
        myViewHolder.grd.setText(String.format( "%.2f", (mData.get(i).getGrd())));

        if (temp >= 90.00){
            myViewHolder.grd.setTextColor(Color.parseColor("#008000"));
        }
        else if (temp >= 80.00 & temp < 90.00) {
            myViewHolder.grd.setTextColor(Color.parseColor("#00b300"));
        }
        else if (temp >= 70.00 & temp < 80.00) {
            myViewHolder.grd.setTextColor(Color.parseColor("#e6e600"));
        }
        else if (temp >= 60.00 & temp < 70.00) {
            myViewHolder.grd.setTextColor(Color.parseColor("#e6b800"));
        }
        else{
            myViewHolder.grd.setTextColor(Color.parseColor("#ff0000"));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView cls;
        private TextView grd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.contact_item);
            cls = itemView.findViewById(R.id.cls);
            grd = itemView.findViewById(R.id.grd);
        }
    }
}