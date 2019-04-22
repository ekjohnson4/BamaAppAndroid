package com.example.bamaappredesign.Home;

import android.support.v4.app.Fragment;

import com.example.bamaappredesign.ActionCard.ActionCardFragment;
import com.example.bamaappredesign.CampusMap.CampusMapFragment;
import com.example.bamaappredesign.Events.EventsFragment;
import com.example.bamaappredesign.Grades.GradesFragment;
import com.example.bamaappredesign.Laundry.LaundryFragment;
import com.example.bamaappredesign.News.NewsFragment;
import com.example.bamaappredesign.R;
import com.example.bamaappredesign.Schedule.ScheduleFragment;
import com.example.bamaappredesign.Tickets.TicketsFragment;
import com.example.bamaappredesign.Transportation.TransportationFragment;

public enum Module {
    EVENTS("Events", new EventsFragment(), ModuleType.VISITOR, R.drawable.events2),
    ACTION_CARD("Action Card", new ActionCardFragment(), ModuleType.STUDENT),
    TICKETS("Tickets", new TicketsFragment(), ModuleType.STUDENT),
    GRADES("Grades", new GradesFragment(), ModuleType.STUDENT,R.drawable.grades),
    SCHEDULE("Schedule", new ScheduleFragment(), ModuleType.STUDENT, R.drawable.schedule),
    LAUNDRY("Laundry", new LaundryFragment(), ModuleType.VISITOR, R.drawable.laundry2),
    NEWS("News", new NewsFragment(), ModuleType.VISITOR),
    CAMPUS_MAP("Campus Map", new CampusMapFragment(), ModuleType.VISITOR, R.drawable.map2),
    TRANSPORTATION("Transport", new TransportationFragment(), ModuleType.VISITOR, R.drawable.transportation2),
    SHOPPING("Shopping", new TransportationFragment(), ModuleType.VISITOR, R.drawable.supe);

    private Fragment fragment;
    private String name;
    private ModuleType moduleType;
    private int image;

    public String getName(){
        return name;
    }

    Module(String name, Fragment fragment, ModuleType a){
        this.name = name;
        this.fragment = fragment;
        moduleType = a;
        image = 0;
    }
    Module(String name, Fragment fragment, ModuleType a, int image){
        this.name = name;
        this.fragment = fragment;
        moduleType = a;
        this.image = image;
    }
    public ModuleType getType(){
        return moduleType;
    }

    public Fragment getFragment(){
        return this.fragment;
    }

    public int getImage() {
        return image;
    }
}
