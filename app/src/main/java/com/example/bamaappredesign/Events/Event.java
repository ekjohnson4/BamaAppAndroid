package com.example.bamaappredesign.Events;

public class Event {
    private String title;
    private String description;
    private String location;
    private String date;

    public Event(String title, String description, String location, String date){
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
    }

    public String getTitle(){
        return title;
    }
    String getDescription(){
        return description;
    }
    String getLocation(){
        return location;
    }
    String getDate(){
        return fixDate(date);
    }

    private String fixDate(String date){
        String fixedDate = "";
        fixedDate = fixedDate.concat(date.substring(5,7));
        fixedDate = fixedDate.concat("/" + date.substring(8,10));
        fixedDate = fixedDate.concat("/" + date.substring(0,4));
        return fixedDate;
    }
}
