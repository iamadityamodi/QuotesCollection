package com.quotescollection.quotesdata.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.DatabaseErrorHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.adapter.FevoriteAdapter;
import com.quotescollection.quotesdata.database.DatabaseHelper;
import com.quotescollection.quotesdata.interfaces.FevActivityClickListener;
import com.quotescollection.quotesdata.models.CategoryListModel;

import java.util.ArrayList;

public class FevoriteActivity extends AppCompatActivity {

    RecyclerView  recyclerView;
    ArrayList<CategoryListModel> categoryListModels;

    DatabaseHelper databaseHelper;
    FevoriteAdapter fevoriteAdapter;
    String string;
    int pos;
    TextView textView;
    ImageView backimageview;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fevorite);
        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.fevoriterecyclerview);
        textView = findViewById(R.id.titlename);
        backimageview = findViewById(R.id.backimageview);
        backimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView.setText("Fevorite");
        new FevoriteTask().execute();



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
    }
    class FevoriteTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            categoryListModels = new ArrayList<>();
            categoryListModels = databaseHelper.getFavQuotes();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FevoriteActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);
            fevoriteAdapter = new FevoriteAdapter(FevoriteActivity.this, categoryListModels, new FevActivityClickListener() {
                @Override
                public void onClick(String aString, int pos1) {

                    string = aString;
                    pos = pos1;
                    new FevoriteDelete().execute();
                }
            });
            recyclerView.setAdapter(fevoriteAdapter);

        }
    }

    class FevoriteDelete extends AsyncTask<Void, Void, Void>{

        Boolean isDelete = false;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                databaseHelper.RemoveFev(string);

                isDelete= true;
            }catch (Exception e){
                isDelete = false;
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (isDelete && fevoriteAdapter!=null && pos != -1){
                    fevoriteAdapter.notifyItemRemoved(pos);
                    categoryListModels.remove(pos);
                    fevoriteAdapter.notifyDataSetChanged();
            }else {
                Toast.makeText(FevoriteActivity.this,"Error to Remove fevorite!",Toast.LENGTH_LONG).show();
            }

        }
    }


}