package com.example.bamaappredesign.ActionCard;

public class Transaction {
    private String price;
    private String location;
    private String type;

    Transaction(String price, String location, String type){
        this.price = price;
        this.location = location;
        this.type = type;
    }

    String getPrice(){
        return price;
    }
    String getLocation(){
        return location;
    }
    public String getType(){
        return type;
    }
}
