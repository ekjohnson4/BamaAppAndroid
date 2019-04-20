package com.example.bamaappredesign.Events;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.bamaappredesign.R;

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
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class EventsFragment extends Fragment {
    View v;
    private List<Event> linkList = new ArrayList<>();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_events, container, false);

        // Execute DownloadXML AsyncTask
        RecyclerView myrecyclerview = v.findViewById(R.id.rvContacts);
        adapter = new EventsAdapter(getContext(),linkList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        System.out.println(linkList.size());
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL));
        today = v.findViewById(R.id.button);
        today.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DownloadXML(0).execute(URL);
            }
        });
        tomorrow =  v.findViewById(R.id.button4);
        tomorrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DownloadXML(1).execute(URL);
            }
        });
        week = v.findViewById(R.id.button5);

        week.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DownloadXML(2).execute(URL);
            }
        });

        progress = v.findViewById(R.id.progressBarEvents);
        progress.setVisibility(View.VISIBLE);
        return v;
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadXML extends AsyncTask<String, Void, Void> {
        int x;
        DownloadXML(int x){
            this.x = x;
        }
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
            progress.setVisibility(View.VISIBLE);
            linkList.clear();
            adapter.notifyDataSetChanged();
            if(x == 0){
                setTodaysEvents();
                System.out.println("Setting today's events in new thread.");
            }
            else if(x == 1){
                setTomorrowsEvents();
                System.out.println("Setting tomorrow's events in new thread.");
            }
            else {
                setWeeksEvents();
                System.out.println("Setting week's events in new thread.");
            }
            progress.setVisibility(View.GONE);
        }
    }

    // getNode function
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }

    private void setTodaysEvents(){
        if (nodelist == null){
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String todayDate = sdf.format(c.getTime());
        progress.setVisibility(View.VISIBLE);
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Set the texts into TextViews from item nodes
                // Get the title
                if(getNode("start_date", eElement).substring(0,10).equals(todayDate)) {
                    try {
                        Event a = new Event(getNode("title", eElement), getNode("description", eElement), getNode("location", eElement), getNode("start_date", eElement));
                        linkList.add(a);
                    }
                    catch(Exception e){
                        System.out.println("Failed to get event");
                    }
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
        String tomorrowDate = sdf.format(gc.getTime());
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Set the texts into TextViews from item nodes
                // Get the title
                if(getNode("start_date", eElement).substring(0,10).equals(tomorrowDate)) {
                    try {
                        Event a = new Event(getNode("title", eElement), getNode("description", eElement), getNode("location", eElement), getNode("start_date", eElement));
                        linkList.add(a);
                    }
                    catch(Exception e){
                        System.out.println("Failed to get event");
                    }
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
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Set the texts into TextViews from item nodes
                // Get the title
                if(checkDate(getNode("start_date", eElement).substring(0,10))) {
                    try {
                        Event a = new Event(getNode("title", eElement), getNode("description", eElement), getNode("location", eElement), getNode("start_date", eElement));
                        linkList.add(a);
                    }
                    catch(Exception e){
                        System.out.println("Failed to get event");
                    }
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadXML(0).execute(URL);
    }
}