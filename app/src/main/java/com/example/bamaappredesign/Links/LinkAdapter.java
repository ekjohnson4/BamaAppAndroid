package com.example.bamaappredesign.Links;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bamaappredesign.R;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.MyViewHolder> {

    Context mContext;
    List<Link> mData;

    public LinkAdapter(Context mContext, List<Link> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public LinkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.link_layout,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        vHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String website = mData.get(vHolder.getAdapterPosition()).getUrl();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                mContext.startActivity(browserIntent);
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinkAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_name.setText(mData.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item;
        private TextView tv_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.contact_item);
            tv_name = itemView.findViewById(R.id.name_contact);
        }
    }
}