package com.example.bamaappredesign.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bamaappredesign.R;

import java.io.InputStream;

public class MyCustomPagerAdapter extends PagerAdapter {
    private String images[];
    private String strings[];
    private FragmentTransaction ft;
    private LayoutInflater layoutInflater;

    MyCustomPagerAdapter(Context context, String images[], String stringArray[], FragmentTransaction ft) {
        this.images = images;
        this.strings = stringArray;
        this.ft = ft;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.view_pager, container, false);

        //Load image and headline
        ImageView imageView = itemView.findViewById(R.id.imageView);
        new MyCustomPagerAdapter.DownloadImageTask((ImageView) itemView.findViewById(R.id.imageView))
                .execute(images[position]);
        TextView txt = itemView.findViewById(R.id.textView);
        txt.setText(strings[position]);

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewFragment fragment = new WebViewFragment();
                Bundle arguments = new Bundle();
                arguments.putInt("pos" , position);
                fragment.setArguments(arguments);
                ft.replace(R.id.flMain, fragment);
                ft.addToBackStack(null);
                ft.commit();
        }
        });

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}