package com.example.bamaappredesign;

import com.example.bamaappredesign.Home.MainActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EmailTest {
    MainActivity m;

    @Before
    public void makeEmail() {
        m = new MainActivity();
        m.setEmail("nlsaban@crimson.ua.edu");
    }

    @Test
    public void testGetSet() {
        Assert.assertEquals(m.getEmail(), "nlsaban@crimson.ua.edu");
    }
}