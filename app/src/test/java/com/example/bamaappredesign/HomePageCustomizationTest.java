package com.example.bamaappredesign;

import com.example.bamaappredesign.Home.Module;
import com.example.bamaappredesign.Home.ModuleHomeAdapter;
import com.example.bamaappredesign.Home.ModuleVisitorAdapter;
import com.example.bamaappredesign.Links.Link;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HomePageCustomizationTest {
    @Test
    public void testCustomization() {
        ModuleHomeAdapter a = new ModuleHomeAdapter();
        assertEquals(a.getModuleOne(), Module.TICKETS);
        assertEquals(a.getModuleTwo(), Module.ACTION_CARD);
        //set home page modules to events and schedule
        a.setModuleOne(Module.EVENTS);
        a.setModuleTwo(Module.SCHEDULE);
        assertEquals(a.getModuleOne(), Module.EVENTS);
        assertEquals(a.getModuleTwo(), Module.SCHEDULE);
    }

}
