package com.quotescollection.quotesdata.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
//    QuotesCollectionAdapter quotesCollectionAdapter;
//    ArrayList<QuotesxCollectionModel> quotesxCollectionModels = new ArrayList<>();

    private MutableLiveData<List<CategoryModel>> CategryList;
    private String[] name;

    private int[] image = {R.drawable.change, R.drawable.death, R.drawable.dream, R.drawable.family, R.drawable.famous,
            R.drawable.friendship, R.drawable.happiness, R.drawable.inspirational, R.drawable.life, R.drawable.love,
            R.drawable.motivational, R.drawable.moveon, R.drawable.nature, R.drawable.strength, R.drawable.travel};


    ///////////////////////////////////////////////////////////////////////////
    // getter setter
    ///////////////////////////////////////////////////////////////////////////
    public void setName(String[] name) {
        CategryList = new MutableLiveData<>();
        ArrayList<CategoryModel> list = new ArrayList<>();
        for (int i = 0; i < name.length ; i++) {
            CategoryModel model = new CategoryModel(name[i],image[i]);
            list.add(model);
        }
        CategryList.setValue(list);
    }

    public LiveData<List<CategoryModel>> getCategryList() {
        return CategryList;
    }
}
