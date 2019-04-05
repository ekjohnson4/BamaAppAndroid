package com.example.bamaappredesign;

import com.example.bamaappredesign.ActionCard.ActionCardFragment;
import com.example.bamaappredesign.Home.Module;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModuleTest {
    @Test
    public void testModuleFragment() {
        Module a = Module.ACTION_CARD;
        assertEquals(a.getFragment().getClass().toString(), new ActionCardFragment().getClass().toString());
    }
}
