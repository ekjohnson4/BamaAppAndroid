package com.example.bamaappredesign.Grades;

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
        myViewHolder.cls.setText(mData.get(i).getCls());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView cls;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.contact_item);
            cls = itemView.findViewById(R.id.cls);
        }
    }
}