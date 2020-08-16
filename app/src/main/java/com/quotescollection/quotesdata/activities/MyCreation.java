package com.quotescollection.quotesdata.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.adapter.SavedImage_Adapter;
import com.quotescollection.quotesdata.models.SavedImage_Model;

import java.io.File;
import java.util.ArrayList;

public class MyCreation extends AppCompatActivity {

    TextView atc_name;
    ArrayList<SavedImage_Model> savedImageModels;
    RecyclerView recyclerView;
    SavedImage_Adapter savedImage_adapter;
    ImageView img_back;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_creation);



    }

    void init() {
        atc_name = (TextView) findViewById(R.id.titlename);
        atc_name.setText("Saved Quotes");
        img_back = (ImageView) findViewById(R.id.backimageview);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.save_rv);

        adView = findViewById(R.id.ad_view);
        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (adView.getVisibility() == View.GONE) {
                    adView.setVisibility(View.VISIBLE);

                    Log.e("ADS", "load: " );

                }else {
                    Log.e("ADS", "un-load: " );
                }
            }


        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        new LoadImages().execute();
    }

    class LoadImages extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            File file = new File(Environment.getExternalStorageDirectory()
                    + "/"
                    + getResources().getString(R.string.app_name)
            );
            if (!file.exists()) {
                file.mkdirs();
            }
            getSavedQuotes(file.getPath());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setLayoutManager(new GridLayoutManager(MyCreation.this, 3, LinearLayoutManager.VERTICAL, false));
            if (savedImageModels != null && savedImageModels.size() != 0) {
                savedImage_adapter = new SavedImage_Adapter(MyCreation.this, savedImageModels);
                recyclerView.setAdapter(savedImage_adapter);
            }
        }
    }

    void getSavedQuotes(String path) {
        savedImageModels = new ArrayList<>();
        File file = new File(path);

        if (file.isDirectory()) {
            File[] listFile = file.listFiles();


            for (int i = 0; i < listFile.length; i++) {

                savedImageModels.add(new SavedImage_Model(listFile[i].getAbsolutePath()));

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}