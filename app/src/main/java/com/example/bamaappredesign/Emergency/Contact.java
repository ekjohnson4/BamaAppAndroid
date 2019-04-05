package com.example.bamaappredesign.Emergency;

class Contact {
    private String Name;
    private String Phone;

    public Contact() {
    }

    public Contact(String name, String phone) {//maybe int for phone?
        Name = name;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    //settter

    public void setName(String name) {
        Name = name;
    }

    /*public void setPhone(String phone) {
        Phone = phone;
    }*/
}
