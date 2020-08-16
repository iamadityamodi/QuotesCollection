package com.quotescollection.quotesdata.adapter;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.models.font_model;

import java.util.ArrayList;

public class font_adapter extends RecyclerView.Adapter<font_adapter.ViewHolder> {


    Context context;
    String[] font_models;

    public font_adapter(Context context, String[] font_models) {
        this.context = context;
        this.font_models = font_models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.fontview,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), String.valueOf(font_models));
        holder.textView.setTypeface(typeFace);
    }

    @Override
    public int getItemCount() {
        return font_models.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.font);

        }
    }
}
