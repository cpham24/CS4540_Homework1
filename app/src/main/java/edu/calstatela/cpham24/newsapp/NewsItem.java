package edu.calstatela.cpham24.newsapp;

/**
 * Created by bill on 6/29/17.
 */

public class NewsItem {
    public String title;
    public String author;
    public String description;
    public String url;
    public String imgurl;
    public String date;

    public NewsItem(String title, String author, String description, String date, String imgurl) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.date = date;
        this.imgurl = imgurl;
    }
}
