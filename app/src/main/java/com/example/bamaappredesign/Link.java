package com.example.bamaappredesign;
public class Link {

    private String title;
    private String url;


    public Link() {
    }

    public Link(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    //settter

    public void setTitle(String name) {
        this.title = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
