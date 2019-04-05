package com.example.bamaappredesign.News;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bamaappredesign.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<News> linkList = new ArrayList<>();
    TextView textview;
    NodeList nodelist;
    NewsAdapter adapter;
    //ProgressBar progress;
    // Insert image URL
    String URL = "https://cw.ua.edu/feed/"; //"http://feeds.reuters.com/Reuters/domesticNews";
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, container, false);
        // Execute DownloadXML AsyncTask
        myrecyclerview = (RecyclerView) v.findViewById(R.id.rvNews);
        adapter = new NewsAdapter(getContext(),linkList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //System.out.println(linkList.size());
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        //progress = v.findViewById(R.id.progressBar);
        //progress.setVisibility(View.VISIBLE);
        return v;
    }

    private class DownloadXML extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... Url) {
            try {
                java.net.URL url = new URL(Url[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                // Download the XML file
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                // Locate the Tag Name
                nodelist = doc.getElementsByTagName("item");
               // System.out.println(nodelist.getLength());
                //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void args) {

            for (int temp = 0; temp < nodelist.getLength(); temp++) {
                Node nNode = nodelist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // Set the texts into TextViews from item nodes
                    // Get the title
                    String firstDescription = getNode("description", eElement);
                    int start = getNode("description", eElement).indexOf("src");
                    int end = getNode("description", eElement).indexOf("class");
                    String image = getNode("description", eElement).substring(start + 5, end - 2);
                    int start1 = firstDescription.indexOf("<img width");
                    int end2 = firstDescription.indexOf("link_thumbnail=\"\" />");
                    String newDescription = firstDescription.substring(end2+24, firstDescription.length());

                    News a = new News(getNode("title", eElement), getNode("link", eElement), getNode("pubDate", eElement), firstDescription, image);
                    linkList.add(a);

                }
            }
            //.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();

            // Close progressbar
        }
    }
    // getNode function
    private static String getNode(String sTag, Element eElement) {
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        //System.out.println(sTag);
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        //System.out.println(nValue.getNodeValue());
        return nValue.getNodeValue();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadXML().execute(URL);
    }

}



/*
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

=================================================



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