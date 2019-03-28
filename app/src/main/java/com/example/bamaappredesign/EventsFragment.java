package com.example.bamaappredesign;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    View v;
    TextView textview;
    Button today;
    Button tomorrow;
    Button week;
    NodeList nodelist;
    // Insert image URL
    String URL = "https://www.ua.edu/api/events/?cat=2";
    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_events, container, false);
        textview = (TextView) v.findViewById(R.id.text);
        today = (Button) v.findViewById(R.id.button);
        today.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setTodaysEvents();
            }
        });
        tomorrow = (Button) v.findViewById(R.id.button4);
        tomorrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setTomorrowsEvents();
            }
        });
        week = (Button) v.findViewById(R.id.button5);
        week.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setWeeksEvents();
            }
        });
        // Execute DownloadXML AsyncTask
        new DownloadXML().execute(URL);
        return v;
    }

    private class DownloadXML extends AsyncTask<String, Void, Void> {

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
                        textview.setText(textview.getText() + "Title : "
                                + Html.fromHtml(getNode("title", eElement)) + "\n" + "\n");
                        // Get the description
                        textview.setText(textview.getText() + "Description : "
                                + Html.fromHtml(getNode("description", eElement)) + "\n" + "\n");
                        // Get the link
                        textview.setText(textview.getText() + "Location : "
                                + Html.fromHtml(getNode("location", eElement)) + "\n" + "\n");
                        // Get the date
                        textview.setText(textview.getText() + "Date : "
                                + Html.fromHtml(getNode("start_date", eElement)) + "\n" + "\n" + "\n"
                                + "\n");
                    }
                }
            }
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
        textview.setText("");
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
                    textview.setText(textview.getText() + "Title : "
                            + Html.fromHtml(getNode("title", eElement)) + "\n" + "\n");
                    // Get the description
                    textview.setText(textview.getText() + "Description : "
                            + Html.fromHtml(getNode("description", eElement)) + "\n" + "\n");
                    // Get the link
                    textview.setText(textview.getText() + "Location : "
                            + Html.fromHtml(getNode("location", eElement)) + "\n" + "\n");
                    // Get the date
                    textview.setText(textview.getText() + "Date : "
                            + Html.fromHtml(getNode("start_date", eElement)) + "\n" + "\n" + "\n"
                            + "\n");
                }
            }
        }
    }

    private void setTomorrowsEvents(){
        textview.setText("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, 1);
        String tomorrowDate = sdf.format(gc.getTime());
        System.out.println("Today's date:" + tomorrowDate);
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Set the texts into TextViews from item nodes
                // Get the title
                if(getNode("start_date", eElement).substring(0,10).equals(tomorrowDate)) {
                    textview.setText(textview.getText() + "Title : "
                            + Html.fromHtml(getNode("title", eElement)) + "\n" + "\n");
                    // Get the description
                    textview.setText(textview.getText() + "Description : "
                            + Html.fromHtml(getNode("description", eElement)) + "\n" + "\n");
                    // Get the link
                    textview.setText(textview.getText() + "Location : "
                            + Html.fromHtml(getNode("location", eElement)) + "\n" + "\n");
                    // Get the date
                    textview.setText(textview.getText() + "Date : "
                            + Html.fromHtml(getNode("start_date", eElement)) + "\n" + "\n" + "\n"
                            + "\n");
                }
            }
        }
    }
    private void setWeeksEvents(){
        textview.setText("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, 1);
        String tomorrowDate = sdf.format(gc.getTime());
        System.out.println("Today's date:" + tomorrowDate);
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                // Set the texts into TextViews from item nodes
                // Get the title
                if(checkDate(getNode("start_date", eElement).substring(0,10))) {
                    textview.setText(textview.getText() + "Title: "
                            + Html.fromHtml(getNode("title", eElement)) + "\n" + "\n");
                    // Get the description
                    textview.setText(textview.getText() + "Description: "
                            + Html.fromHtml(getNode("description", eElement)) + "\n" + "\n");
                    // Get the link
                    textview.setText(textview.getText() + "Location: "
                            + Html.fromHtml(getNode("location", eElement)) + "\n" + "\n");
                    // Get the date
                    textview.setText(textview.getText() + "Date: "
                            + Html.fromHtml(getNode("start_date", eElement)) + "\n" + "\n" + "\n"
                            + "\n");
                }
            }
        }
    }

    private boolean checkDate(String dateCheck){
        //checks if date is within 7 days
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> thisWeek = new ArrayList<String>();
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

}
