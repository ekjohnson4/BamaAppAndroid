package com.example.bamaappredesign;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    Context mContext;
    List<News> mData;
    //private WebView mWebView;

    public NewsAdapter(Context mContext, List<News> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.news_list,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);


        vHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"Clicked on number: "+  String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mData.get(vHolder.getAdapterPosition()).getLink()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);


                //mWebView.loadUrl(mData.get(vHolder.getAdapterPosition()).getLink());
                //Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mData.get(vHolder.getAdapterPosition()).getPhone()));
                //mContext.startActivity(intent);
            }
        });



        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(mData.get(i).getTitle());
        myViewHolder.tv_description.setText(Html.fromHtml(mData.get(i).getDescription()));
        myViewHolder.tv_date.setText(mData.get(i).getDate().substring(0, 16));
        //myViewHolder.tv_link.setText(mData.get(i).getLink());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item;
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_description;
        //private TextView tv_link;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.news_item);
            tv_title = itemView.findViewById(R.id.txtTitle);
            tv_description = itemView.findViewById(R.id.txtDescription);
            tv_date = itemView.findViewById(R.id.txtDate);
            //tv_link = itemView.findViewById(R.id.txtLink);
        }

    }
}