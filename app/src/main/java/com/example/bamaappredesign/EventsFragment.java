package com.example.bamaappredesign;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.util.LayoutDirection.LOCALE;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<Event> linkList = new ArrayList<>();;
    TextView textview;
    Button today;
    Button tomorrow;
    Button week;
    NodeList nodelist;
    EventsAdapter adapter;
    ProgressBar progress;
    // Insert image URL
    String URL = "https://www.ua.edu/api/events/?cat=2";
    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_events, container, false);
        // Execute DownloadXML AsyncTask
        myrecyclerview = (RecyclerView) v.findViewById(R.id.rvContacts);
        adapter = new EventsAdapter(getContext(),linkList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        System.out.println(linkList.size());
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        today = v.findViewById(R.id.button);
        today.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setTodaysEvents();
            }
        });
        tomorrow =  v.findViewById(R.id.button4);
        tomorrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setTomorrowsEvents();
            }
        });
        week = v.findViewById(R.id.button5);
        week.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setWeeksEvents();
            }
        });
        progress = v.findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
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
                nodelist = doc.getElementsByTagName("event");

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void args) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String todayDate = sdf.format(c.getTime());
            System.out.println("Today's date:" + todayDate);
            for (int temp = 0; temp < nodelist.getLength(); temp++) {
                Node nNode = nodelist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // Set the texts into TextViews from item nodes
                    // Get the title
                    if(getNode("start_date", eElement).substring(0,10).equals(todayDate)) {
                        Event a = new Event(getNode("title", eElement), getNode("description", eElement), getNode("location", eElement), getNode("start_date", eElement));
                        linkList.add(a);
                    }
                }
            }
            progress.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();

            // Close progressbar
    }
    }
    // getNode function
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    private void setTodaysEvents(){
        if (nodelist == null){
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String todayDate = sdf.format(c.getTime());
        linkList.clear();
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Set the texts into TextViews from item nodes
                // Get the title
                if(getNode("start_date", eElement).substring(0,10).equals(todayDate)) {
                    Event a = new Event(getNode("title", eElement), getNode("description", eElement), getNode("location", eElement), getNode("start_date", eElement));
                    linkList.add(a);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void setTomorrowsEvents(){
        if (nodelist == null){
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, 1);
        linkList.clear();
        String tomorrowDate = sdf.format(gc.getTime());
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Set the texts into TextViews from item nodes
                // Get the title
                if(getNode("start_date", eElement).substring(0,10).equals(tomorrowDate)) {
                    Event a = new Event(getNode("title", eElement), getNode("description", eElement), getNode("location", eElement), getNode("start_date", eElement));
                    linkList.add(a);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
    private void setWeeksEvents(){
        if (nodelist == null){
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, 1);
        linkList.clear();
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Set the texts into TextViews from item nodes
                // Get the title
                if(checkDate(getNode("start_date", eElement).substring(0,10))) {
                    Event a = new Event(getNode("title", eElement), getNode("description", eElement), getNode("location", eElement), getNode("start_date", eElement));
                    linkList.add(a);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private boolean checkDate(String dateCheck){
        //checks if date is within 7 days
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> thisWeek = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        String todayDate = sdf.format(c.getTime());
        thisWeek.add(todayDate);
        for(int i = 1; i<7; i++) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.add(Calendar.DATE, i);
            String date = sdf.format(gc.getTime());
            thisWeek.add(date);
        }
        for(int i = 0; i<thisWeek.size(); i++){
            if(!thisWeek.contains(dateCheck)){
                return false;
            }
        }
        return true;
    }

    public String convertDate(String date){
        String fixedDate = "";


        return date;
    }

    private void showEvent(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadXML().execute(URL);
    }

}
