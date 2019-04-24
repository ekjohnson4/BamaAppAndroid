package com.example.bamaappredesign;

import com.example.bamaappredesign.Home.Module;
import com.example.bamaappredesign.Home.ModuleHomeAdapter;
import com.example.bamaappredesign.Links.Link;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModuleAdapterTest {
    @Test
    public void testModuleAdapter() {
        ModuleHomeAdapter a = new ModuleHomeAdapter();
        assertEquals(a.getModuleOne(), Module.TICKETS);
        assertEquals(a.getModuleTwo(), Module.ACTION_CARD);
    }
}
