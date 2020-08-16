package com.quotescollection.quotesdata.models;

public class SavedImage_Model {


    String imagePath;

    public SavedImage_Model(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImage() {
        return imagePath;
    }
}
