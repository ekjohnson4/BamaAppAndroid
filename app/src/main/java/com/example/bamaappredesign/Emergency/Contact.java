package com.example.bamaappredesign.Emergency;

public class Contact {
    private String Name;
    private String Phone;

    public Contact(String name, String phone) {
        Name = name;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    String getPhone() {
        return Phone;
    }

    public void setName(String name) {
        Name = name;
    }
}
