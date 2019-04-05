package com.example.bamaappredesign.ActionCard;

public class Transaction {
    private String price;
    private String location;
    private String type;

    public Transaction(String price, String location, String type){
        this.price = price;
        this.location = location;
        this.type = type;
    }

    public String getPrice(){
        return price;
    }
    public String getLocation(){
        return location;
    }
    public String getType(){
        return type;
    }
}
