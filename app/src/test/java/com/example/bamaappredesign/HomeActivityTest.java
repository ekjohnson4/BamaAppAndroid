package com.example.bamaappredesign;

import com.example.bamaappredesign.Home.HomeActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class HomeActivityTest {
    private HomeActivity h = null;

    @Before
    public void beforeEachTest() {
        h = Mockito.mock(HomeActivity.class);
    }
    @Test
    public void testHomeActivity(){
        assertEquals(h.getLoggedIn(), false);
    }
}
