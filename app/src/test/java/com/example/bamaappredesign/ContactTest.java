package com.example.bamaappredesign;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContactTest {
    @Test
    public void testContactTitle() {
        Contact a = new Contact("Police", "205-310-3910");
        assertEquals(a.getName(), "Police");
    }
}
