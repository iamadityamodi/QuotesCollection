package com.example.all.status.quotescollection.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.all.status.quotescollection.R;
import com.example.all.status.quotescollection.adapter.QuotesCollectionAdapter;
import com.example.all.status.quotescollection.models.QuotesxCollectionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

public class QuotesCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> jsonnamelist;


    Button btn;
    int pos;
    QuotesCollectionAdapter quotesCollectionAdapter;
    ArrayList<QuotesxCollectionModel> quotesxCollectionModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_category);

        int numberOfColumns = 2;


        pos = getIntent().getIntExtra("POS", 0);

        jsonnamelist = new ArrayList<>();
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
        jsonnamelist.add(getResources().getResourceName(R.raw.travel));


        new GetContacts().execute();






       /* InputStream is = getResources().openRawResource(R.raw.change);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();
        Log.e("jsonStringjsonString", "onCreate: "+jsonString );

        QuotesxCollectionModel quotesxCollectionModel = new QuotesxCollectionModel(jsonString);
        quotesxCollectionModels.add(quotesxCollectionModel);*/

       /* try {

            JSONArray jArray = new JSONArray(loadJSONFromAsset());
            quotesxCollectionModels = new ArrayList<>();
            // Extract data from json and store into ArrayList as class objects
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                QuotesxCollectionModel quotesxCollectionModel = new QuotesxCollectionModel();
                String quote = json_data.getString("quote");
                quotesxCollectionModels.add(quotesxCollectionModel);
            }

            // Setup and Handover data to recyclerview
            quotesCollectionAdapter = new QuotesCollectionAdapter(this, quotesxCollectionModels);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(quotesCollectionAdapter);

        } catch (JSONException e) {
            Toast.makeText(QuotesCategoryActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }*/


    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            try {

                JSONArray contacts = new JSONArray(loadJSONFromAsset());

                quotesxCollectionModels = new ArrayList<>();
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String quotes = c.getString("quote");
                    String author = c.getString("author");
                    Log.e("quotesquotes", "doInBackground: " + quotes);
                    quotesxCollectionModels.add(new QuotesxCollectionModel(quotes, 0, null));
                }


            } catch (final JSONException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            recyclerView = findViewById(R.id.recyclerviewcategory);
            LinearLayoutManager llm = new LinearLayoutManager(QuotesCategoryActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            quotesCollectionAdapter = new QuotesCollectionAdapter(QuotesCategoryActivity.this, quotesxCollectionModels);
            recyclerView.setAdapter(quotesCollectionAdapter);
        }
    }


    public String loadJSONFromAsset() {


        String json = null;
        try {
            InputStream is = getAssets().open("change.json");
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


}
