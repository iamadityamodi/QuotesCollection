package com.quotescollection.quotesdata.models;

public class CategoryModel {
    private String name;
    private int image;

    public CategoryModel(String name, int image) {
        this.name = name;
        this.image = image;


    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

}
