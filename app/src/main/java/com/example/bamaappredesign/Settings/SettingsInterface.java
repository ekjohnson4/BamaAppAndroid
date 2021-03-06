package com.example.bamaappredesign.Settings;

import android.support.v4.app.Fragment;

import com.example.bamaappredesign.Home.Module;

public abstract class SettingsInterface extends Fragment {
    abstract void writeModulesToDatabase();

    Module getModule(String mod) {
        for (Module m : Module.values()) {
            if (m.getName().equals(mod)) {
                return m;
            }
        }
        return null;
    }
}