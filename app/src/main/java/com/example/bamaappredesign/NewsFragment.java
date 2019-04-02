package com.example.bamaappredesign;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings({"unchecked","deprecation"})

public class NewsFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerAdapter mRecyclerAdapter;
    Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.news_swipe_refresh_layout);
        mRecyclerView = view.findViewById(R.id.news_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerAdapter = new RecyclerAdapter(mContext, new ArrayList<RssDataParser.Item>());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerAdapter.clear();
                new GetNewsFeed().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private class GetNewsFeed extends AsyncTask<Void, Void, ArrayList<RssDataParser.Item>> {
        @Override
        protected ArrayList<RssDataParser.Item> doInBackground(Void... params) {
            try {
                return loadXmlFromNetwork("https://cw.ua.edu/feed/");
            } catch (IOException e) {
                Log.e("Error",e.getMessage());
            } catch (XmlPullParserException e) {
                Log.e("Error",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<RssDataParser.Item> items) {
            if (items != null) {
                Log.d("ArrayList","Items: "+items.toString());
                int itemSize = items.size();
                for (int i = 0; i < itemSize; i++) {
                    mRecyclerAdapter.add(i,items.get(i));
                }
                mRecyclerAdapter.notifyDataSetChanged();
            } else {
                Log.e("OnPostExecute","ArrayList Is Null");
                Snackbar.make(getView(), "No Connection Was Made",Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private ArrayList loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream inputStream = null;
        RssDataParser rssDataParser = new RssDataParser();
        ArrayList<RssDataParser.Item> entries = null;

        try {
            inputStream = downloadUrl(urlString);
            entries = rssDataParser.parse(inputStream);
            return entries;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

    private InputStream downloadUrl(String urlString) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        HttpURLConnection.setFollowRedirects(false);
        connection.setConnectTimeout(15 * 1000);
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        return inputStream;
    }
}

/*
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.

public class NewsFragment extends Fragment {


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

}
*/