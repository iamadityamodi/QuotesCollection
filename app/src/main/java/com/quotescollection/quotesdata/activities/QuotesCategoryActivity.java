package com.quotescollection.quotesdata.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.quotescollection.quotesdata.BuildConfig;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.adapter.QuotesCategoryAdapter;
import com.quotescollection.quotesdata.adapter.QuotesCollectionAdapter;
import com.quotescollection.quotesdata.interfaces.CatogryClickListener;
import com.quotescollection.quotesdata.models.CategoryListModel;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class QuotesCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String ListName, quotes, autors;
    private TextView titlename;
    private ImageView backimageview;
    private AdRequest adRequest;
    int colors;
    AdView adView;
    Button btn;
    int pos;
    QuotesCollectionAdapter quotesCollectionAdapter;

    ArrayList<CategoryListModel> quotesxCollectionModels;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar*/
        setContentView(R.layout.activity_quotes_category);

        titlename = findViewById(R.id.titlename);
        backimageview = findViewById(R.id.backimageview);
        adView = findViewById(R.id.ad_view);

        backimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String getCategory = getIntent().getStringExtra(getResources().getString(R.string.Cat_Name));
        titlename.setText(getCategory);
        ListName = getIntent().getStringExtra(getResources().getString(R.string.Cat_Name));

        new GetContacts().execute();

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

    private class GetContacts extends AsyncTask<Void, Void, ArrayList<CategoryListModel>> {


        @Override
        protected void onPostExecute(ArrayList<CategoryListModel> categoryListModels) {
            super.onPostExecute(categoryListModels);

            recyclerView = findViewById(R.id.recyclerviewcategory);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(staggeredGridLayoutManager);


            QuotesCategoryAdapter quotesCategoryAdapter = new QuotesCategoryAdapter(QuotesCategoryActivity.this,categoryListModels,
                    new CatogryClickListener() {
                @Override
                public void onClick(int pos, String name, String autor, String quote, int color, ArrayList<CategoryListModel> categoryListModels) {


                    quotes = quote;
                    autors = autor;
                    colors = color;

                    Intent intent1 = new Intent(QuotesCategoryActivity.this, GetAuterQuoteAvtivity.class);
                    intent1.putExtra(getResources().getString(R.string.Cat_Qutes), quote);
                    intent1.putExtra(getResources().getString(R.string.Cat_Autor), autor);
                    intent1.putExtra(getResources().getString(R.string.Cat_Name), ListName);
//                    intent1.putExtra("Color_pass", colors);

                    intent1.putExtra("POS", pos);
                    Log.e("onClick", "pos: "+pos );
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)categoryListModels);

                    Log.e("categoryListModels", "onClick1: "+categoryListModels.size() );
                    Log.e("categoryListModels", "onClick2: "+quotesxCollectionModels.size() );
                    intent1.putExtra("BUNDLE",args);

                    Log.e("onClick", "a_size: "+categoryListModels.size() );
                    startActivity(intent1);
                }
            });

        /*  QuotesCategoryAdapter quotesCategoryAdapter = new QuotesCategoryAdapter(QuotesCategoryActivity.this, categoryListModels, new CategoryClickListenerData() {
              @Override
              public void onclick(int pos, ArrayList<CategoryListModel> categoryListModels) {

                  Intent intent =new Intent(QuotesCategoryActivity.this,GetAuterQuoteAvtivity.class);
                  Bundle args = new Bundle();
                  intent.putExtra("POS", pos);
                  args.putSerializable("categoryListModels", (Serializable) categoryListModels);
                  intent.putExtra("BUN", args);
                  startActivity(intent);

              }
          });*/

            recyclerView.setAdapter(quotesCategoryAdapter);
        }

        @Override
        protected ArrayList<CategoryListModel> doInBackground(Void... voids) {

            try {


                JSONArray contacts = new JSONArray(loadJSONFromAsset());

                quotesxCollectionModels = new ArrayList<>();
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String quotes = c.getString("quote");
                    String author = c.getString("author");
                    Log.e("quotesquotes", "doInBackground: " + quotes);
                    quotesxCollectionModels.add(new CategoryListModel(quotes, author));
                }

                return quotesxCollectionModels;


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

                return null;

            }
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public String loadJSONFromAsset() {

        Log.i("NULL_CHECK", "loadJSONFromAsset: " + ListName != null ? "not null" : "null");

        String json = null;
        try {
            InputStream is = getAssets().open(ListName.toLowerCase() + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.i("NULL_CHECK", "loadJSONFromAsset: " + json);
            return json;
        } catch (IOException ex) {
            Log.i("NULL_CHECK", "loadJSONFromAsset: " + ex.toString());
            return null;
        }

    }


}
