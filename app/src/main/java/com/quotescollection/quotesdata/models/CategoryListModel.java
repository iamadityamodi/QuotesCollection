package com.quotescollection.quotesdata.models;

import java.io.Serializable;

public class CategoryListModel implements Serializable {

    private String quote, autor, c_name;
    int id;

    private int fevValue;

    public CategoryListModel() {
    }

    public CategoryListModel(String quote, String autor, int fevValue, String c_name) {
        this.quote = quote;
        this.autor = autor;
        this.fevValue = fevValue;
        this.c_name = c_name;
    }

    public CategoryListModel(String quote, String autor) {
        this.quote = quote;
        this.autor = autor;

    }

    public String getQuote() {
        return quote;
    }

    public String getAutor() {
        return autor;
    }

    public int getFevValue() {
        return fevValue;
    }

    public void setFevValue(int fevValue) {
        this.fevValue = fevValue;
    }
}
