package com.example.bamaappredesign.Events;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//import java.util.ArrayList;
import com.example.bamaappredesign.R;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    Context mContext;
    List<Event> mData;
    Dialog myDialog;

    public EventsAdapter(Context mContext, List<Event> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.event_list,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);



        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(mData.get(i).getTitle());
        myViewHolder.tv_description.setText(Html.fromHtml(mData.get(i).getDescription()));
        myViewHolder.tv_location.setText(mData.get(i).getDate() + " | " + Html.fromHtml(mData.get(i).getLocation()));
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

            item =  itemView.findViewById(R.id.contact_item);
            tv_title = itemView.findViewById(R.id.title);
            tv_description = itemView.findViewById(R.id.description);
            tv_location = itemView.findViewById(R.id.location);
        }

    }
}