package com.quotescollection.quotesdata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.interfaces.CatogryClickListener;
import com.quotescollection.quotesdata.models.CategoryListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuotesCategoryAdapter extends RecyclerView.Adapter<QuotesCategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryListModel> categoryListModels;
    CatogryClickListener listener;
    List<String> stringList;
    int color;


    public QuotesCategoryAdapter(Context context, ArrayList<CategoryListModel> name, CatogryClickListener listener) {
        this.context = context;
        this.categoryListModels = name;
        this.listener = listener;

        this.color = color;
        stringList = Arrays.asList(context.getResources().getStringArray(R.array.colorname));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_quotes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

/*
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
*/


        int[] androidColors = context.getResources().getIntArray(R.array.colorname);
        int color = androidColors[new Random().nextInt(androidColors.length)];


        final CategoryListModel categoryListModel = categoryListModels.get(position);

        holder.textView.setText(categoryListModel.getQuote());
//        holder.textView.setTextSize(18f);

        final int finalColor = color;
        holder.text_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position, null, categoryListModel.getAutor(), categoryListModel.getQuote(), finalColor, categoryListModels);
            }
        });

        holder.Detail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.text_linear.performClick();
            }
        });


    }

    public int data(int color) {
        return color;
    }

    @Override
    public int getItemCount() {
        return categoryListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        LinearLayout text_linear, linear_lay;
        ImageView Detail_img;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            cardView = itemView.findViewById(R.id.cardviewmain);
            text_linear = itemView.findViewById(R.id.text_linear);
            Detail_img = itemView.findViewById(R.id.Detail_img);
        }
    }
}
