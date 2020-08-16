package com.quotescollection.quotesdata.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.adapter.FontAdapter;
import com.quotescollection.quotesdata.adapter.colorAdapter;
import com.quotescollection.quotesdata.adapter.image_adapter;
import com.quotescollection.quotesdata.interfaces.ColorClickListener;
import com.quotescollection.quotesdata.interfaces.FontInterface;
import com.quotescollection.quotesdata.interfaces.image_click;
import com.quotescollection.quotesdata.models.CategoryModel;
import com.quotescollection.quotesdata.models.image_model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    RelativeLayout main_text_size, main_text_style, image_quotes, set_color;
    ImageView save_image;
    Intent intent;
    TextView getquotes, getauthor;
    RecyclerView recyclerviewquotes;
    SeekBar seekbartextsizesize;
    int flag = 0;
    ImageView imageView, bg_imageview;
    CardView cardView;

    image_adapter imageAdapter;
    FontAdapter font_adapter;
    ArrayList<Integer> colorarray;
    TypedArray typedArray;
    int selectpos;
    TextView textSize_txt1;
    LinearLayout filterSeekLay;
    colorAdapter colorAdapter1;

    InterstitialAd interstitialAd = null;;

    AdView adView;

    AdRequest adRequest;
    private int[] image = {R.drawable.change, R.drawable.death, R.drawable.dream, R.drawable.family, R.drawable.famous,
            R.drawable.friendship, R.drawable.happiness, R.drawable.inspirational, R.drawable.life, R.drawable.love,
            R.drawable.motivational, R.drawable.moveon, R.drawable.nature, R.drawable.strength, R.drawable.travel};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        main_text_size = findViewById(R.id.main_text_size);
        bg_imageview = findViewById(R.id.bg_imageview);
        save_image = findViewById(R.id.save);
        image_quotes = findViewById(R.id.image_quotes);
        main_text_style = findViewById(R.id.main_text_style);
        getquotes = findViewById(R.id.textviewquotes);
        getauthor = findViewById(R.id.textviewauthor);
        filterSeekLay = findViewById(R.id.filterSeekLay);
        set_color = findViewById(R.id.set_color);
        recyclerviewquotes = findViewById(R.id.recyclerviewquotes);
        seekbartextsizesize = findViewById(R.id.seekbartextsizesize);
        imageView = findViewById(R.id.backimageview);
        cardView = findViewById(R.id.edit_card_view);
        textSize_txt1 = findViewById(R.id.textSize_txt1);
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
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        interstitialAd= new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433");
        adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        setName(image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekbartextsizesize.setMin(10);
        }
        seekbartextsizesize.setMax(30);
        seekbartextsizesize.setProgress(17);
        textSize_txt1.setText("17");
      /*  loadBitmapFromView(cardView);

        Log.e("cardViewcardView", "onCreate: "+loadBitmapFromView(cardView).toString() );*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeImage(getBitmapFromView(cardView));
            }
        });

        seekbartextsizesize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int p = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

//                getquotes.setTextSize(i);

                p = i;


           /*     if (p == 10) {
                    getquotes.setTextSize(10);
                    getauthor.setTextSize(10);
                    seekbartextsizesize.setProgress(10);
                } else {*/
                getquotes.setTextSize(p);
                getauthor.setTextSize(p);
                textSize_txt1.setText(String.valueOf(i));
//                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (p < 10) {
                    p = 10;
                    seekbartextsizesize.setProgress(p);
                }

            }
        });

        LinearLayoutManager l2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewquotes.setLayoutManager(l2);

        font_adapter = new FontAdapter(this, new FontInterface() {
            @Override
            public void FontClick(Typeface style, String fontstylename) {

                getquotes.setTypeface(style);
                getauthor.setTypeface(style);
                recyclerviewquotes.getAdapter().notifyDataSetChanged();
            }
        });


        recyclerviewquotes.setAdapter(font_adapter);


        imageAdapter = new image_adapter(EditActivity.this, new image_click() {
            @Override
            public void imageClick(int imageset) {
                bg_imageview.setImageDrawable(getResources().getDrawable(imageset));
            }
        });
        recyclerviewquotes.setAdapter(imageAdapter);
        recyclerviewquotes.getAdapter().notifyDataSetChanged();

        getCategryList().observe(this, new Observer<List<image_model>>() {
            @Override
            public void onChanged(List<image_model> categoryModels) {
                if (categoryModels != null && categoryModels.size() > 0) {

                    imageAdapter.setList(categoryModels);

                } else {
                    // TODO: 5/29/2020 empty state
                    Toast.makeText(EditActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        intent = getIntent();
        intent.getExtras();

        String g_quotes = getIntent().getStringExtra("quotes");
        String g_author = getIntent().getStringExtra("author");

        getquotes.setText(g_quotes);
        getauthor.setText(g_author);

        main_text_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filterSeekLay.setVisibility(View.GONE);
                recyclerviewquotes.setAdapter(font_adapter);
                if (recyclerviewquotes.getVisibility() == View.GONE) {
                    recyclerviewquotes.setVisibility(View.VISIBLE);
                } else {
                    recyclerviewquotes.setVisibility(View.GONE);
                }
            }
        });

        set_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterSeekLay.setVisibility(View.GONE);
                recyclerviewquotes.setAdapter(colorAdapter1);
                if (recyclerviewquotes.getVisibility() == View.GONE) {
                    recyclerviewquotes.setVisibility(View.VISIBLE);
                } else {
                    recyclerviewquotes.setVisibility(View.GONE);
                }
            }
        });

        typedArray = getResources().obtainTypedArray(R.array.color_array);
        colorarray = new ArrayList<>();
        for (int i = 0; i < typedArray.length(); i++) {
            colorarray.add(typedArray.getResourceId(i, 0));
        }

         colorAdapter1 = new colorAdapter(EditActivity.this, colorarray, selectpos, new ColorClickListener() {
            @Override
            public void click(int pos, int colorcode) {
                getquotes.setTextColor(getResources().getColor(colorcode));
                getauthor.setTextColor(getResources().getColor(colorcode));
            }
        });
        recyclerviewquotes.setAdapter(colorAdapter1);

        image_quotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                filterSeekLay.setVisibility(View.GONE);
                recyclerviewquotes.setAdapter(imageAdapter);
                if (recyclerviewquotes.getVisibility() == View.GONE) {
                    recyclerviewquotes.setVisibility(View.VISIBLE);
                } else {
                    recyclerviewquotes.setVisibility(View.GONE);
                }
            }
        });


        main_text_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerviewquotes.setVisibility(View.GONE);
                if (filterSeekLay.getVisibility() == View.GONE) {
                    filterSeekLay.setVisibility(View.VISIBLE);
                } else {
                    filterSeekLay.setVisibility(View.GONE);

                }
            }
        });


    }


    public static Bitmap getBitmapFromView(View view) {

        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("pictureFile",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
            Intent intent = new Intent(this,PreviewQuoteActivty.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("img_path",pictureFile.getPath());
            startActivity(intent);
        } catch (FileNotFoundException e) {
            Log.d("pictureFile", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("pictureFile", "Error accessing file: " + e.getMessage());
        }
    }

    private File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + File.separator
                + getResources().getString(R.string.app_name)
                );

        Log.e("pictureFile", "getOutputMediaFile: " + mediaStorageDir.getPath());

        if (!mediaStorageDir.exists()) {
                mediaStorageDir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "Quotes" + timeStamp + ".jpg";

        Toast.makeText(this, "Save Image Path: " + mediaStorageDir + "/" + mImageName, Toast.LENGTH_LONG).show();

        Log.e("pictureFile", "getOutputMediaFile: " + mImageName);

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    private MutableLiveData<List<image_model>> CategryList;


    public void setName(int[] name) {
        CategryList = new MutableLiveData<>();
        ArrayList<image_model> list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            image_model model = new image_model(name[i]);
            list.add(model);
        }
        CategryList.setValue(list);
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

    public LiveData<List<image_model>> getCategryList() {
        return CategryList;
    }

}
