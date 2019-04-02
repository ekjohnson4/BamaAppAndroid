package com.example.bamaappredesign;

import android.support.v4.app.Fragment;

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
