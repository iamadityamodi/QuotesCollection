package com.quotescollection.quotesdata.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.quotescollection.quotesdata.BuildConfig;
import com.quotescollection.quotesdata.fragment.FevoriteFragment;
import com.quotescollection.quotesdata.fragment.HomeFragment;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.adapter.MenuAdapter;
import com.quotescollection.quotesdata.others.AppRaterUtil;


import java.util.ArrayList;
import java.util.Arrays;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;


public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {

    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;

    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        // Show main fragment in container
        goToFragment(new HomeFragment(), false, "FavFG");
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
//        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
//        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack, String s) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if (addToBackStack) {
            transaction.addToBackStack(s);
        }

        transaction.replace(R.id.container, fragment, s).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {

            case 0:
                setTitle(mTitles.get(0));
                goToFragment(new HomeFragment(), true, "FavFG");
                break;



            case 1:
                startActivity(new Intent(MainActivity.this, FevoriteActivity.class));
                mMenuAdapter.setViewSelected(0, true);
                break;

            case 2:
                startActivity(new Intent(MainActivity.this, MyCreation.class));
                mMenuAdapter.setViewSelected(0, true);
                break;

            case 3:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app" + " " + getApplicationName(MainActivity.this) + " " + " https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                mMenuAdapter.setViewSelected(0, true);
                break;

            case 4:
                showCustomDialog();
                mMenuAdapter.setViewSelected(0, true);
                break;

            case 5:
                Intent i = new Intent(this, PrivacyPolicyActivity.class);
                startActivity(i);
                mMenuAdapter.setViewSelected(0, true);
                break;
           /* default:
                goToFragment(new HomeFragment(), false);
                break;*/
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }

    @Override
    public void onBackPressed() {
        RateMe();
    }

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.my_custom_alert_dailog1, viewGroup, false);

        TextView No = dialogView.findViewById(R.id.btn_No);
        TextView rate_us = dialogView.findViewById(R.id.btn_rate_us);
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                RateApp();
            }
        });

        builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        alertDialog.show();
    }

    private void RateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
    public void RateMe() {
        SharedPreferences prefs = getSharedPreferences("app_rater", Context.MODE_PRIVATE);
        int total_launch_count = prefs.getInt("total_launch_count", 1);
        int never_count = prefs.getInt("never_count", 1);
        int rate_count = prefs.getInt("rate_count", 1);
        Long first_launch_date_time = prefs.getLong("first_launch_date_time", 0);
        Long launch_date_time = prefs.getLong("launch_date_time", 0);

        if (first_launch_date_time == 0) {
// Log.e("back"," if if ");
            AppRaterUtil.app_launched(MainActivity.this, R.layout.my_custom_alert_dailog, R.id.btn_No, R.id.btn_yes, R.id.btn_rate_us);
        } else if (java.lang.System.currentTimeMillis() >= launch_date_time + (24 * 60 * 60 * 1000) && total_launch_count <= AppRaterUtil.MAX_REMIND_PROMPT &&
                never_count <= AppRaterUtil.MAX_NEVER_PROMPT &&
                rate_count <= AppRaterUtil.MAX_RATE_PROMPT) {
// Log.e("back"," else if else if ");
            AppRaterUtil.app_launched(MainActivity.this, R.layout.my_custom_alert_dailog, R.id.btn_No, R.id.btn_yes, R.id.btn_rate_us);
        } else
//            if(onBack)
            {
            ShowDailogeMsg(MainActivity.this);
        }
    }

    private void ShowDailogeMsg(Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.exit_dlg_view);

        dialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) (Resources.getSystem().getDisplayMetrics().widthPixels / 1.2);
        lp.dimAmount = 0.7f;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        TextView no_btn = dialog.findViewById(R.id.No_btn);
        TextView yes_btn = dialog.findViewById(R.id.Yes_btn);

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finishAffinity();
            }
        });

    }
}
