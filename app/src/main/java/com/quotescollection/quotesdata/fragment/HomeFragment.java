package com.quotescollection.quotesdata.fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.activities.QuotesCategoryActivity;
import com.quotescollection.quotesdata.adapter.QuotesCollectionAdapter;
import com.quotescollection.quotesdata.database.DatabaseHelper;
import com.quotescollection.quotesdata.interfaces.CatogryClickListener;
import com.quotescollection.quotesdata.models.CategoryListModel;
import com.quotescollection.quotesdata.models.CategoryModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

//    private TextView titletextview;
//    private ImageView backimageview, menuimageview;
    InterstitialAd mInterstitialAd;
    String nameof;
    RecyclerView recyclerView;
    QuotesCollectionAdapter adapter;
    public static final String MyPREFERENCES = "MyPrefs";
    View view;
    int position;
    AdView adView;
    DatabaseHelper databaseHelper;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        databaseHelper =new DatabaseHelper(getContext());
       view = inflater.inflate(R.layout.fragment_home, container, false);

        requestStoragePermission();


        return view;
    }

    private void requestStoragePermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            openMain();

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void openMain() {


        setName(getResources().getStringArray(R.array.Name));

//        titletextview = getActivity().findViewById(R.id.titlename);
//        backimageview = getActivity().findViewById(R.id.backimageview);
        recyclerView = view.findViewById(R.id.recyclerview);
        adView = view.findViewById(R.id.ad_view);
//        backimageview.setVisibility(View.GONE);


        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/8691691433");

        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                Intent intent = new Intent(getContext(), QuotesCategoryActivity.class);
                intent.putExtra(getResources().getString(R.string.Cat_Name), nameof);
                Log.e("POSPOS", "onAdClosed: "+position);
                startActivity(intent);
            }
        });


//        titletextview.setText(getResources().getString(R.string.titlename));


        adapter = new QuotesCollectionAdapter(getContext(), new CatogryClickListener() {
            @Override
            public void onClick(int pos, final String name, String autor, String quote, int color, ArrayList<CategoryListModel> categoryListModels) {

                position = pos;
                nameof = name;


                if (Math.abs(pos % 2) == 0) {

                    Log.e("111", "onClick: " );
                    if (isNetworkAvailable()) {

                        Log.e("222", "onClick: " );
                        Log.e("Available", "Available_network ");



                        if (mInterstitialAd.isLoaded() || mInterstitialAd != null) {
                            Log.e("333", "onClick: " );
                            mInterstitialAd.show();

                        } else {

                            Log.e("444", "onClick: " );
                            Intent intent = new Intent(getContext(), QuotesCategoryActivity.class);
                            intent.putExtra(getResources().getString(R.string.Cat_Name), name);
                            Log.e("pospospos", "onClick: "+pos );
                            startActivity(intent);
                        }

                        Log.e("444", "onClick: " );
                        Intent intent1 = new Intent(getContext(), QuotesCategoryActivity.class);
                        intent1.putExtra(getResources().getString(R.string.Cat_Name), name);
                        Log.e("pospospos", "onClick: "+pos );
                        startActivity(intent1);
                    } else {

                        Log.e("555", "onClick: " );

                        Log.e("Available", "Not_Available_network ");

                        Intent intent = new Intent(getContext(), QuotesCategoryActivity.class);
                        intent.putExtra(getResources().getString(R.string.Cat_Name), name);
                        Log.e("pospospos", "onClick: "+pos );
                        startActivity(intent);

                    }

                } else {


                    Log.e("666", "onClick: ");


                    Intent intent = new Intent(getContext(), QuotesCategoryActivity.class);
                    intent.putExtra(getResources().getString(R.string.Cat_Name), name);
                    Log.e("pospos", "onClick: " + pos);
                    startActivity(intent);

                }
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
       /* layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                if((position % 3) == 0){
                    return 2;
                } else
                    return 1;


            }

        });*/
        recyclerView.setHasFixedSize(true);
        recyclerView.setMinimumHeight(150);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getCategryList().observe(this, new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categoryModels) {
                if (categoryModels != null && categoryModels.size() > 0) {

                    adapter.setList(categoryModels);

                } else {
                    // TODO: 5/29/2020 empty state
                    Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                }
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
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


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
