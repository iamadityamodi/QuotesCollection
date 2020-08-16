package com.quotescollection.quotesdata.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.quotescollection.quotesdata.BuildConfig;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.activities.EditActivity;
import com.quotescollection.quotesdata.activities.GetAuterQuoteAvtivity;
import com.quotescollection.quotesdata.database.DatabaseHelper;
import com.quotescollection.quotesdata.interfaces.FevClickListener;
import com.quotescollection.quotesdata.models.CategoryListModel;

import java.util.ArrayList;

public class QuotesViewPager extends PagerAdapter {

    private ArrayList<CategoryListModel> object;
    private Context context;
    ImageView share, edit, copy, fev;
    FevClickListener fevClickListener;
    DatabaseHelper databaseHelper;

    public QuotesViewPager(ArrayList<CategoryListModel> object, Context context,FevClickListener fevClickListener) {
        this.object = object;
        this.context = context;
        this.fevClickListener=fevClickListener;
        databaseHelper = new DatabaseHelper(context);
        Log.e("QuotesViewPager", "QuotesViewPager: " + object.size());
    }

    @Override
    public int getCount() {
        return object.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {

        return super.getItemPosition(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

        ViewGroup layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.image_view_viewpager, container,
                false);

        TextView getQuotes = layout.findViewById(R.id.GetQuotedata);
        TextView getautor = layout.findViewById(R.id.GetAuthotdata);
        share = layout.findViewById(R.id.sharetoanother);
        copy = layout.findViewById(R.id.copydata);
        edit = layout.findViewById(R.id.editdata);
        fev = layout.findViewById(R.id.favorite);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "Quote : " + object.get(position).getQuote() + "\n\n" + "Author : " + object.get(position).getAutor() + "\n\n\n" + "App Link : " + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_using)));

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("quotes", object.get(position).getQuote());
                intent.putExtra("author", object.get(position).getAutor());
                context.startActivity(intent);

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Quote : " + object.get(position).getQuote() + "\n" + "Author : " + object.get(position).getAutor(), "Quote : " + object.get(position).getQuote() + "\n" + "Author : " + object.get(position).getAutor() + "\n\n\n" + "App Link : " + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copy To Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        fev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(object.get(position).getFevValue() == 0){
                    databaseHelper.insertFavQuotes(object.get(position).getQuote(),object.get(position).getAutor(),null);
                    object.get(position).setFevValue(1);
                }else {
                    databaseHelper.RemoveFev(object.get(position).getQuote());
                    object.get(position).setFevValue(0);
                }
                fevClickListener.onClick(true,position);
                notifyDataSetChanged();
            }
        });
        if (object.get(position).getFevValue()==1){
            fev.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_press));
        }else {
            fev.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart));
        }

        Log.e("instantiateItem", "instantiateItem: " + object.get(position).getQuote());
        Log.e("instantiateItem", "instantiateItem: " + object.get(position).getAutor());

        getQuotes.setText(object.get(position).getQuote());
        getautor.setText(object.get(position).getAutor());


        container.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    private void ShareToAnother() {

    }
}
