package com.example.bamaappredesign;

import com.example.bamaappredesign.Links.Link;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkTest {
    @Test
    public void testLinksTitle() {
        Link a = new Link("Google", "google.com");
        assertEquals(a.getTitle(), "Google");
    }
}
