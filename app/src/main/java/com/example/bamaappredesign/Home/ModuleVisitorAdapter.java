package com.example.bamaappredesign.Home;

public class ModuleVisitorAdapter extends HomeVisitorFragment {

    public Module getModuleOne(){
        return moduleOne;
    }
    public Module getModuleTwo(){
        return moduleTwo;
    }
    public void setModuleOne(Module mod){
        moduleOne = mod;
    }
    public void setModuleTwo(Module mod){
        moduleTwo = mod;
    }
}
