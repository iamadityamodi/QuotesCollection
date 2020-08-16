package com.quotescollection.quotesdata.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.activities.PreviewQuoteActivty;
import com.quotescollection.quotesdata.interfaces.image_click;
import com.quotescollection.quotesdata.models.SavedImage_Model;
import com.quotescollection.quotesdata.models.image_model;

import java.util.ArrayList;
import java.util.List;

public class SavedImage_Adapter extends RecyclerView.Adapter<SavedImage_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<SavedImage_Model> modelList ;



    public SavedImage_Adapter(Context context,ArrayList<SavedImage_Model> modelList) {
        this.context = context;
        this.modelList =modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.imageview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final SavedImage_Model imageModel = modelList.get(position);

        Glide.with(context)
                .load(imageModel.getImage())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PreviewQuoteActivty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("img_path",imageModel.getImage());
                context.startActivity(intent);


            }
        });



    }



    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }
}
