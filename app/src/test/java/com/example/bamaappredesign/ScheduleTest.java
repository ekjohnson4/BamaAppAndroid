package com.example.bamaappredesign;

import com.example.bamaappredesign.Links.Link;
import com.example.bamaappredesign.Schedule.ScheduleClass;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScheduleTest {
    @Test
    public void testSchedule() {
        ScheduleClass a = new ScheduleClass("CS 495", "11", "MWF", "Gray", "SERC");
        assertEquals(a.getCls(), "CS 495");
    }
}
