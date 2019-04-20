package com.example.bamaappredesign.Links;
public class Link {
    private String title;
    private String url;

    public Link(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    String getUrl() {
        return url;
    }

    public void setTitle(String name) {
        this.title = name;
    }
}
