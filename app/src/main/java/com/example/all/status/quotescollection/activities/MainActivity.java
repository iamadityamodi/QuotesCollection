package com.example.all.status.quotescollection.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.all.status.quotescollection.R;
import com.example.all.status.quotescollection.adapter.QuotesCollectionAdapter;
import com.example.all.status.quotescollection.inter.RecyclerViewClickListener;
import com.example.all.status.quotescollection.models.QuotesxCollectionModel;
import com.example.all.status.quotescollection.others.RecyclerViewTouchListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn;
    QuotesCollectionAdapter quotesCollectionAdapter;
    ArrayList<QuotesxCollectionModel> quotesxCollectionModels = new ArrayList<>();
    String[] name = {"Change", "Death", "Dream", "Family", "Famous",
            "Friendship", "Happiness", "Inspiration", "Life", "Love",
            "Motivation", "Move On ", "Nature", "Strength", "Travel"};

    int[] image = {R.drawable.change, R.drawable.death, R.drawable.dream, R.drawable.family, R.drawable.famous,
            R.drawable.friendship, R.drawable.happiness, R.drawable.inspirational, R.drawable.life, R.drawable.love,
            R.drawable.motivational, R.drawable.moveon, R.drawable.nature, R.drawable.strength, R.drawable.travel};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerview);
        btn = findViewById(R.id.btn);
//        btn.setVisibility(View.GONE);

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
            quotesxCollectionModels.add(new QuotesxCollectionModel(name[i], image[i], null));
        }


        quotesCollectionAdapter = new QuotesCollectionAdapter(this, quotesxCollectionModels);
        recyclerView.setAdapter(quotesCollectionAdapter);
        Log.e("namename", "onCreate: " + quotesxCollectionModels.get(0).getQuotes() + " last " + quotesxCollectionModels.get(quotesxCollectionModels.size() - 1).getQuotes());

/*
        final ArrayList<String> jsonnamelist = new ArrayList<>();
        jsonnamelist.add(getResources().getResourceName(R.raw.change));
        jsonnamelist.add(getResources().getResourceName(R.raw.death));
        jsonnamelist.add(getResources().getResourceName(R.raw.dream));
        jsonnamelist.add(getResources().getResourceName(R.raw.family));
        jsonnamelist.add(getResources().getResourceName(R.raw.famous));
        jsonnamelist.add(getResources().getResourceName(R.raw.friendship));
        jsonnamelist.add(getResources().getResourceName(R.raw.happiness));
        jsonnamelist.add(getResources().getResourceName(R.raw.inspirational));
        jsonnamelist.add(getResources().getResourceName(R.raw.life));
        jsonnamelist.add(getResources().getResourceName(R.raw.love));
        jsonnamelist.add(getResources().getResourceName(R.raw.motivational));
        jsonnamelist.add(getResources().getResourceName(R.raw.moveon));
        jsonnamelist.add(getResources().getResourceName(R.raw.nature));
        jsonnamelist.add(getResources().getResourceName(R.raw.strength));
        jsonnamelist.add(getResources().getResourceName(R.raw.travel));*/

//        quotesxCollectionModels.add(jsonnamelist);



        /*jsonnamelist.add(loadJSONFromAsset("change.json"));
        jsonnamelist.add(loadJSONFromAsset("death.json"));
        jsonnamelist.add(loadJSONFromAsset("dream.json"));
        jsonnamelist.add(loadJSONFromAsset("family.json"));
        jsonnamelist.add(loadJSONFromAsset("famous.json"));
        jsonnamelist.add(loadJSONFromAsset("friendship.json"));
        jsonnamelist.add(loadJSONFromAsset("happiness.json"));
        jsonnamelist.add(loadJSONFromAsset("inspirational.json"));
        jsonnamelist.add(loadJSONFromAsset("life.json"));
        jsonnamelist.add(loadJSONFromAsset("love.json"));
        jsonnamelist.add(loadJSONFromAsset("motivational.json"));
        jsonnamelist.add(loadJSONFromAsset("moveon.json"));
        jsonnamelist.add(loadJSONFromAsset("nature.json"));
        jsonnamelist.add(loadJSONFromAsset("strength.json"));
        jsonnamelist.add(loadJSONFromAsset("travel.json"));*/


//        Log.e("jsonnamelist", "onCreate: " + jsonnamelist.size());


        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, final int position) {




                Intent intent = new Intent(MainActivity.this, QuotesCategoryActivity.class);
//                    Bundle args = new Bundle();
                intent.putExtra("POS", position);
//                    args.putSerializable("ARRAYLIST", (Serializable) jsonnamelist);
//                    intent.putExtra("BUNDLE", args);
                Log.e("positionposition", "onClick: " + position);
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

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


    public String loadJSONFromAsset(String json1) {
        String json = null;
        try {
            InputStream is = getAssets().open(json1);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private boolean listAssetFiles(String path) {

        String[] list;
        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    if (!listAssetFiles(path + "/" + file))
                        return false;
                    else {
                        // This is a file
                        // TODO: add file name to an array list
                    }
                }
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
