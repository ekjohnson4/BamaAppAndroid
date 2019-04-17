package com.example.bamaappredesign.News;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bamaappredesign.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context mContext;
    private List<News> mData;
    private FragmentTransaction ft;

    NewsAdapter(Context mContext, List<News> mData, FragmentTransaction ft) {
        this.mContext = mContext;
        this.mData = mData;
        this.ft = ft;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.news_list,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        vHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsWebViewFragment fragment = new NewsWebViewFragment();
                Bundle arguments = new Bundle();
                arguments.putString("link" , mData.get(vHolder.getAdapterPosition()).getLink());
                fragment.setArguments(arguments);
                ft.replace(R.id.flMain, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(mData.get(i).getTitle());
        myViewHolder.tv_date.setText(mData.get(i).getDate().substring(0, 16));
        myViewHolder.tv_description.setText(Html.fromHtml(mData.get(i).getDescription(), Images, null));
    }

    private Html.ImageGetter Images = new Html.ImageGetter() {

        public Drawable getDrawable(String source) {
            Drawable drawable = null;

            FetchImageUrl fiu = new FetchImageUrl(mContext,source);
            try {
                fiu.execute().get();
                drawable = fiu.GetImage();
            }
            catch (Exception e) {
                //drawable = mContext.getResources().getDrawable(R.drawable.test);
            }

            //To display image,center of screen
            assert drawable != null;
            int imgH = drawable.getIntrinsicHeight();
            int imgW = drawable.getIntrinsicWidth();
            int padding = 20;
            int realWidth = 330-(2*padding);
            int realHeight = imgH * realWidth/imgW;
            drawable.setBounds(0,0,realWidth ,realHeight);
            return drawable;
        }
    };

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_description;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.news_item);
            tv_title = itemView.findViewById(R.id.txtTitle);
            tv_date = itemView.findViewById(R.id.txtDate);
            tv_description = itemView.findViewById(R.id.txtDescription);
        }
    }
}
