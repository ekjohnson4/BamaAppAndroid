package com.example.bamaappredesign.Schedule;

class ScheduleClass {
    private String cls;
    private String time;
    private String days;
    private String prof;
    private String location;

    ScheduleClass(String cls, String time, String days, String prof, String location){
        this.cls = cls;
        this.time = time;
        this.days = days;
        this.prof = prof;
        this.location = location;
    }

    String getCls(){
        return cls;
    }
    String getTime(){
        return time;
    }
    String getDays(){
        return days;
    }
    String getProf(){
        return prof;
    }
    String getLocation(){
        return location;
    }
}
