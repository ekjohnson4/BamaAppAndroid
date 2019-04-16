package com.example.bamaappredesign.News;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.bamaappredesign.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NewsFragment extends Fragment {
    View v;
    private List<News> linkList = new ArrayList<>();
    NodeList nodelist;
    NewsAdapter adapter;
    String URL = "https://cw.ua.edu/feed/";
    ProgressBar progress;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, container, false);

        // Execute DownloadXML AsyncTask
        RecyclerView myrecyclerview = v.findViewById(R.id.rvNews);
        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        adapter = new NewsAdapter(getContext(),linkList,ft);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL));
        progress = v.findViewById(R.id.progressBarNews);
        return v;
    }

    private class DownloadXML extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            if(progress!=null)
               progress.setVisibility(View.VISIBLE);
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
            }
            catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            linkList.clear();
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
                    int end2 = firstDescription.indexOf("link_thumbnail=\"\" />");
                    String firstPart = firstDescription.substring(0, end2+24).concat("<p></p>");
                    String finished = firstPart.concat(firstDescription.substring(end2+24));
                    News a = new News(getNode("title", eElement), getNode("link", eElement), getNode("pubDate", eElement), finished, image);
                    linkList.add(a);
                }
            }
            progress.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    //getNode function
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("test if created");
        new DownloadXML().execute(URL);
    }

}