package com.example.bamaappredesign.Schedule;

public class ScheduleClass {
    private String cls;
    private String time;
    private String days;
    private String prof;
    private String location;

    public ScheduleClass(String cls, String time, String days, String prof, String location){
        this.cls = cls;
        this.time = time;
        this.days = days;
        this.prof = prof;
        this.location = location;
    }

    public String getCls(){
        return cls;
    }
    public String getTime(){
        return time;
    }
    public String getDays(){
        return days;
    }
    public String getProf(){
        return prof;
    }
    public String getLocation(){
        return location;
    }
}
