package com.example.all.status.quotescollection.models;

public class QuotesxCollectionModel {

    private String Quotes, Author;
    private int Image;


    public QuotesxCollectionModel(String name, int image, String author) {
        Quotes = name;
        Image = image;
        Author = author;
    }


    public String getQuotes() {
        return Quotes;
    }

    public int getImage() {
        return Image;
    }

    public String getAuthor() {
        return Author;
    }
}
