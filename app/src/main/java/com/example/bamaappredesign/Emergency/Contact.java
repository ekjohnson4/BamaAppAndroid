package com.example.bamaappredesign.Emergency;

class Contact {
    private String Name;
    private String Phone;

    Contact(String name, String phone) {
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
