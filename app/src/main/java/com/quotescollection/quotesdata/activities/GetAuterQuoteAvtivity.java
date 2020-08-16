package com.quotescollection.quotesdata.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quotescollection.quotesdata.BuildConfig;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.adapter.FontAdapter;
import com.quotescollection.quotesdata.adapter.QuotesViewPager;
import com.quotescollection.quotesdata.adapter.font_adapter;
import com.quotescollection.quotesdata.database.DatabaseHelper;
import com.quotescollection.quotesdata.interfaces.FevClickListener;
import com.quotescollection.quotesdata.interfaces.FontInterface;
import com.quotescollection.quotesdata.models.CategoryListModel;
import com.google.android.gms.ads.AdRequest;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

import java.util.ArrayList;
import java.util.Random;

public class GetAuterQuoteAvtivity extends AppCompatActivity {
    TextView getquotes, getautor, titlename;

    public BottomNavigationView bottom_nav;
    private ImageView backimageview;

    ConstraintLayout coordinatordata;
    ViewPager viewPager;
    DatabaseHelper databaseHelper;
    private RelativeLayout getQuoteAutorLayout;
    ArrayList<CategoryListModel> ob;
    RecyclerView recyclerView;
    String quotes, author;
    QuotesViewPager quotesViewPager;
    ArrayList<CategoryListModel> fevList;
    static int pos;
    FevClickListener fevClickListener = new FevClickListener() {
        @Override
        public void onClick(Boolean aBoolean, int pos1) {
            pos = pos1;
            new CheckFev().execute();
        }
    };

    UltraViewPager ultraViewPager;


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar*/
        setContentView(R.layout.activity_get_auter_quote);



        ob = new ArrayList<>();
//        viewPager = findViewById(R.id.viewpager);
        coordinatordata = findViewById(R.id.coordinatordata);
        titlename = findViewById(R.id.titlename);


        databaseHelper = new DatabaseHelper(GetAuterQuoteAvtivity.this);


        intent = getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE");
        pos = intent.getIntExtra("POS", 0);
//        Log.e("onCreate", "onCreate: "+pos );
//        ob = (ArrayList<CategoryListModel>) args.getSerializable("LIST");
//        Log.e("onCreate", "size: "+ob.size());
        Bundle args = intent.getBundleExtra("BUNDLE");
        ob = (ArrayList<CategoryListModel>) args.getSerializable("ARRAYLIST");
        new CheckFev().execute();
/*

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
*/


        intent = getIntent();
        intent.getExtras();


//        if (intent.hasExtra("Color_pass")) {
//            int getcolor = getIntent().getIntExtra("Color_pass", Color.parseColor("#FFFFFF"));
//            getQuoteAutorLayout.setBackgroundColor(getcolor);
//            bottom_nav.setBackgroundColor(getcolor);
//
//        } else {
//            getQuoteAutorLayout.setBackgroundColor(Color.parseColor("#000000"));
//        }

        backimageview = findViewById(R.id.backimageview);
        quotes = getIntent().getStringExtra(getResources().getString(R.string.Cat_Qutes));
        author = getIntent().getStringExtra(getResources().getString(R.string.Cat_Autor));
        backimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final String getQuotesName = getIntent().getStringExtra(getResources().getString(R.string.Cat_Name)) + " " + "Quotes";
        titlename.setText(getQuotesName);


//        Toast.makeText(this, "Quotes : " + getIntent().getStringExtra(getResources().getString(R.string.Cat_Qutes)) + "\n" + "Name " + getIntent().getStringExtra(getResources().getString(R.string.Cat_Name)), Toast.LENGTH_SHORT).show();






    }




    class CheckFev extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            quotesViewPager = new QuotesViewPager(ob, GetAuterQuoteAvtivity.this, fevClickListener);

            if (ob != null && ob.size() > 0) {
                ob.add(new CategoryListModel());
//                viewPager.setAdapter(quotesViewPager);

                ultraViewPager = (UltraViewPager) findViewById(R.id.ultra_viewpager);
                ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//initialize UltraPagerAdapter，and add child view to UltraViewPager

                ultraViewPager.setMultiScreen(0.8f);
                ultraViewPager.setItemRatio(1.0f);
                ultraViewPager.setRatio(0.7f);
                ultraViewPager.setAutoMeasureHeight(true);
                ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
                ultraViewPager.setAdapter(quotesViewPager);

//initialize built-in indicator
//                ultraViewPager.initIndicator();
////set style of indicators
//                ultraViewPager.getIndicator()
//                        .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
//                        .setFocusColor(Color.GREEN)
//                        .setNormalColor(Color.WHITE)
//                        .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
////set the alignment
//                ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
////construct built-in indicator, and add it to  UltraViewPager
//                ultraViewPager.getIndicator().build();
//
////set an infinite loop
//                ultraViewPager.setInfiniteLoop(false);
//enable auto-scroll mode

                Log.e("onCreate", "onCreate: n_null ");
            } else {
                Log.e("onCreate", "onCreate: null ");
            }

//            ultraViewPager.setCurrentItem(pos);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fevList = new ArrayList<>();
            fevList = databaseHelper.getFavQuotes();
            if (ob != null) {
                for (int i = 0; i < ob.size(); i++) {
                    for (int j = 0; j < fevList.size(); j++) {
                        if (fevList.get(j).getQuote().equals(ob.get(i).getQuote())) {
                            ob.get(i).setFevValue(1);
                        }
                    }
                }
            }

            return null;
        }
    }


}
