package com.quotescollection.quotesdata.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.quotescollection.quotesdata.R;

import java.io.File;
import java.io.FileInputStream;

public class PreviewQuoteActivty extends AppCompatActivity {
    Intent intent;
    String path;
    ImageView preview_img,img_back, share, delete;
    AdView adView;
    AdRequest adRequest;
    TextView titlename;
    InterstitialAd interstitialAd = null;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_quote);
        intent = getIntent();
        intent.getExtras();
        if (intent.hasExtra("img_path")) {
            path = intent.getStringExtra("img_path");
        }
        img_back = (ImageView)findViewById(R.id.backimageview);
        titlename = findViewById(R.id.titlename);
        titlename.setText("Preview Activity");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        preview_img =(ImageView)findViewById(R.id.preview_img);
        delete =(ImageView)findViewById(R.id.delete);
        share =(ImageView)findViewById(R.id.share);
        adView = findViewById(R.id.ad_view);
        Glide.with(this)
                .load(getBitmap(path)).centerInside()
                .into(preview_img);

        interstitialAd= new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433");
        adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new deleteImage().execute();
            }
        });

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
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(PreviewQuoteActivty.this, getPackageName()+".myProvider", new File(path)));
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share image..."));
            }
        });

    }
    public Bitmap getBitmap(String path) {
        Bitmap bitmap=null;
        try {
            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap ;
    }


    class deleteImage extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            onBackPressed();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {


            File f= new File(path);
            if (f.exists())
            {
                f.delete();
            }

            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        }else{
            super.onBackPressed();
        }

    }
}