package com.example.bamaappredesign;

public class News {
    private String title;
    private String link;
    private String pubdate;
    //private String author;
    private String description;


    //category, image, author

    public News() {

    }

    public News(String title, String link, String pubdate, String description)
    {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubdate = pubdate;
    }

    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getDate(){
        return pubdate;
    }
    public String getLink(){
        return link;
    }


}

