package com.quotescollection.quotesdata.others;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.quotescollection.quotesdata.R;


public class AppRaterUtil {

    public final static int MAX_REMIND_PROMPT = 3;
//    public final static int TOTAL_PROMPT = 5;
    public final static int MAX_NEVER_PROMPT = 1;
    public final static int MAX_RATE_PROMPT = 2;
    public static int DAYS_UNTIL_PROMPT = 1;//number of days

    public static int total_launch_count;
    public static int never_count;
    public static int rate_count;
    public static Long first_launch_date_time;
    public static Long launch_date_time;

    public static Context context;




    public static void app_launched(Context mContext, int layout_id, int never_button_id, int remind_button_id, int rate_now_nutton_id) {
        context = mContext;
        SharedPreferences prefs = mContext.getSharedPreferences("app_rater", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        total_launch_count = prefs.getInt("total_launch_count", 1);
        never_count = prefs.getInt("never_count", 1);
        rate_count = prefs.getInt("rate_count", 1);


        if (prefs.getBoolean("do_not_show_again", false)) {
//            ((Activity)mContext).onBackPressed();  //if go back and perform any task writhe in onBackPressed()
            return;
        }

        // Get date of first launch
        first_launch_date_time = prefs.getLong("first_launch_date_time", 0);
        if (first_launch_date_time == 0) {
            first_launch_date_time = System.currentTimeMillis();
            editor.putLong("first_launch_date_time", first_launch_date_time);
        }

        launch_date_time = prefs.getLong("launch_date_time", 0);
        if (System.currentTimeMillis() >= launch_date_time + (24 * 60 * 60 * 1000)) {
            if (DAYS_UNTIL_PROMPT <= MAX_REMIND_PROMPT) {
                editor.putLong("launch_date_time", System.currentTimeMillis());
                DAYS_UNTIL_PROMPT++;

            }
        }

        if (total_launch_count <= MAX_REMIND_PROMPT) {
            if (editor != null) {
                editor.putInt("total_launch_count", total_launch_count+1);
                editor.commit();
            }

            if (total_launch_count == 1) {
                showRateDialog(context, layout_id, never_button_id, remind_button_id, rate_now_nutton_id);
            }else if (System.currentTimeMillis() >= launch_date_time + (24 * 60 * 60 * 1000)) {
                showRateDialog(context, layout_id, never_button_id, remind_button_id, rate_now_nutton_id);
            }else {
            }
        }
        editor.commit();
    }

    public static void showRateDialog(final Context mContext, int layout_id, int never_button_id, int remind_button_id, int rate_now_nutton_id) {
        Typeface font;
        SharedPreferences prefs = mContext.getSharedPreferences("app_rater", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        if (prefs.getBoolean("do_not_show_again", false)) {
//            ((Activity)mContext).onBackPressed();  //if go back and perform any task writhe in onBackPressed()
            return;
        }

      //  font = Typeface.createFromAsset(context.getAssets(), "font/trim_font.TTF");

        final Dialog dialog = new Dialog(mContext);
//        dialog.getWindow().setFlags(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                , View.SYSTEM_UI_FLAG_LOW_PROFILE
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        );
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = ((Activity) context).getLayoutInflater().inflate(layout_id, null);
        View never_button = view.findViewById(never_button_id);
        View remind_me_button = view.findViewById(remind_button_id);
        View rate_now_button = view.findViewById(rate_now_nutton_id);




//        TextView txt_rate_now = view.findViewById(R.id.txt_rate_now);
//        TextView txt_never = view.findViewById(R.id.txt_never);
//        TextView txt_remindme = view.findViewById(R.id.txt_remindme);


       // rate_title.setTypeface(font);
        //txt_rate_now.setTypeface(font);
        //txt_never.setTypeface(font);
       // txt_remindme.setTypeface(font);



        never_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (never_count <= MAX_NEVER_PROMPT) {
                    if (editor != null) {
                        editor.putInt("never_count", never_count+1);
                        editor.commit();
                    }
                } else {
                    if (editor != null) {
                        editor.putBoolean("do_not_show_again", true);
                        editor.commit();
                    }
                }
                dialog.dismiss();
//                ((Activity)mContext).onBackPressed();  //if go back and perform any task writhe in onBackPressed()
            }
        });

        remind_me_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                ((Activity)mContext).onBackPressed(); //if go back and perform any task writhe in onBackPressed()
            }
        });

        rate_now_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rate_count <= MAX_RATE_PROMPT) {
                    if (editor != null) {
                        editor.putInt("rate_count", rate_count+1);
                        editor.commit();
                    }
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                }else{
                    if (editor != null) {
                        editor.putBoolean("do_not_show_again", true);
                        editor.commit();
                    }
                }
                dialog.dismiss();
//                ((Activity)mContext).onBackPressed();  //if go back and perform any task writhe in onBackPressed()
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}