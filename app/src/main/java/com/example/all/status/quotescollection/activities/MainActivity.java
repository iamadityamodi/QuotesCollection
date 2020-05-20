package com.example.all.status.quotescollection.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.all.status.quotescollection.R;
import com.example.all.status.quotescollection.adapter.QuotesCollectionAdapter;
import com.example.all.status.quotescollection.models.QuotesxCollectionModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn;
    QuotesCollectionAdapter quotesCollectionAdapter;
    ArrayList<QuotesxCollectionModel> quotesxCollectionModels = new ArrayList<>();
    String[] name = {"change", "death", "dream", "family",
            "famous", "friendship", "happiness", "inspiration", "life", "love", "motivation", "move on ", "nature"
            , "strength", "travel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuotesCategoryActivity.class);
                startActivity(intent);
            }
        });
        int numberOfColumns = 2;
        quotesxCollectionModels = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);


        for (int i = 0; i < name.length; i++) {
            quotesxCollectionModels.add(new QuotesxCollectionModel(name[i]));
        }
        quotesCollectionAdapter = new QuotesCollectionAdapter(this, quotesxCollectionModels);
        recyclerView.setAdapter(quotesCollectionAdapter);
        Log.e("namename", "onCreate: " + quotesxCollectionModels.get(0).getName() + " last " + quotesxCollectionModels.get(quotesxCollectionModels.size() - 1).getName());
      /*  for (int i = 0; i < name.length; i++) {

            Log.e("NAMENAME", "onCreate: "+name.length );
            quotesxCollectionModel.setName(name[i]);
            quotesxCollectionModels.add(quotesxCollectionModel);
        }*/

       /* for (int i = 0; i < name.length; i++) {
            String data = name[i];
            quotesxCollectionModel.setName(data);
            quotesxCollectionModels.add(quotesxCollectionModel);
        }*/



      /*  quotesxCollectionModel.setName("change");
        quotesxCollectionModel.setName("death");
        quotesxCollectionModel.setName("dream");
        quotesxCollectionModel.setName("family");
        quotesxCollectionModel.setName("famous");
        quotesxCollectionModel.setName("friendship");
        quotesxCollectionModel.setName("happiness");
        quotesxCollectionModel.setName("inspiration");
        quotesxCollectionModel.setName("life");
        quotesxCollectionModel.setName("love");
        quotesxCollectionModel.setName("motivation");
        quotesxCollectionModel.setName("move on");
        quotesxCollectionModel.setName("nature");
        quotesxCollectionModel.setName("strength");
        quotesxCollectionModel.setName("travel");
        quotesxCollectionModels.add(quotesxCollectionModel);*/


    }
}
