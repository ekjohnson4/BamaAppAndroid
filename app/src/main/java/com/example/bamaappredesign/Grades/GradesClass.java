package com.example.bamaappredesign.Grades;

public class GradesClass {
    private String cls;
    private double grd;

    public GradesClass(String cls, double grd){
        this.cls = cls;
        this.grd = grd;
    }

    public String getCls(){
        return cls;
    }
    public double getGrd(){
        return grd;
    }
}
