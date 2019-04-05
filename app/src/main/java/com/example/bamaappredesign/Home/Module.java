package com.example.bamaappredesign.Home;

import android.support.v4.app.Fragment;

import com.example.bamaappredesign.ActionCard.ActionCardFragment;
import com.example.bamaappredesign.CampusMapFragment;
import com.example.bamaappredesign.Events.EventsFragment;
import com.example.bamaappredesign.Grades.GradesFragment;
import com.example.bamaappredesign.LaundryFragment;
import com.example.bamaappredesign.News.NewsFragment;
import com.example.bamaappredesign.ScheduleFragment;
import com.example.bamaappredesign.Tickets.TicketsFragment;
import com.example.bamaappredesign.TransportationFragment;

public enum Module {
    EVENTS(new EventsFragment()),
    ACTION_CARD(new ActionCardFragment()),
    TICKETS(new TicketsFragment()),
    GRADES(new GradesFragment()),
    SCHEDULE(new ScheduleFragment()),
    LAUNDRY(new LaundryFragment()),
    NEWS(new NewsFragment()),
    CAMPUS_MAP(new CampusMapFragment()),
    TRANSPORTATION(new TransportationFragment());

    private Fragment fragment;

    Module(Fragment fragment){
        this.fragment = fragment;
    }

    public Fragment getFragment(){
        return this.fragment;
    }
}
