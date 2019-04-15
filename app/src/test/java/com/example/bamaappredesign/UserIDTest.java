package com.example.bamaappredesign;

import com.example.bamaappredesign.Home.MainActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserIDTest {
    MainActivity m;

    @Before
    public void makeUser() {
        m = new MainActivity();
        m.setUID("123456789");
    }

    @Test
    public void testGetSet() {
        Assert.assertEquals(m.getUID(), "123456789");
    }
}