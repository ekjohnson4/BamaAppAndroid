package com.example.bamaappredesign;

import com.example.bamaappredesign.Events.Event;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTest {
    @Test
    public void testEventTitle() {
        Event a = new Event("Awesome event", "This is an event", "Gorgas", "04/03/2019");
        assertEquals(a.getTitle(), "Awesome event");
    }
}
