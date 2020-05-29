package com.example.all.status.quotescollection.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.all.status.quotescollection.R;
import com.example.all.status.quotescollection.models.QuotesxCollectionModel;

import java.util.ArrayList;

public class QuotesCollectionAdapter extends RecyclerView.Adapter<QuotesCollectionAdapter.ViewHolder> {

    Context context;
    ArrayList<QuotesxCollectionModel> quotesxCollectionModels;


    public QuotesCollectionAdapter(Context context, ArrayList<QuotesxCollectionModel> quotesxCollectionModels) {
        this.context = context;
        this.quotesxCollectionModels = quotesxCollectionModels;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.quotescollectiomnmain, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.e("namename", "onBindViewHolder: " + position);

        QuotesxCollectionModel quotesxCollectionModel = quotesxCollectionModels.get(position);
        holder.textView.setText(quotesxCollectionModel.getQuotes());
        holder.relativetext.setAlpha(0.9f);
        Glide.with(context)
                .load(quotesxCollectionModel.getImage())
                .into(holder.imageviewmain);

       /* Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.relativetext.setBackgroundColor(color);*/

    }

    @Override
    public int getItemCount() {
        return quotesxCollectionModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CardView cardView;
        ImageView imageviewmain;
        RelativeLayout relativetext;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.textviewname);
            cardView = itemView.findViewById(R.id.cardviewmain);
            imageviewmain = itemView.findViewById(R.id.imageviewmain);
            relativetext = itemView.findViewById(R.id.relativetext);
        }
    }
}
