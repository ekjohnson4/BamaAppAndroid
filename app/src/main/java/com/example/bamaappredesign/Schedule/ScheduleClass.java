package com.example.bamaappredesign.Schedule;

public class ScheduleClass {
    private String cls;
    private String time;
    private String days;
    private String prof;

    public ScheduleClass(String cls, String time, String days, String prof){
        this.cls = cls;
        this.time = time;
        this.days = days;
        this.prof = prof;
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
}
