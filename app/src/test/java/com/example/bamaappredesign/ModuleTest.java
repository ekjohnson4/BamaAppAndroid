package com.example.bamaappredesign;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModuleTest {
    @Test
    public void testModuleFragment() {
        Module a = Module.ACTION_CARD;
        assertEquals(a.getFragment().getClass().toString(), new ActionCardFragment().getClass().toString());
    }
}
