package com.quotescollection.quotesdata.interfaces;

import com.quotescollection.quotesdata.models.CategoryListModel;

import java.util.ArrayList;

public interface CategoryClickListenerData {

    void onclick(int pos, ArrayList<CategoryListModel> categoryListModels);
}
