package com.quotescollection.quotesdata.interfaces;

import com.quotescollection.quotesdata.models.CategoryListModel;

import java.util.ArrayList;

public interface CatogryClickListener {
    void onClick(int pos, String name, String autor, String quote, int color, ArrayList<CategoryListModel> categoryListModels);

}
