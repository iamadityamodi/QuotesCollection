package com.quotescollection.quotesdata.adapter;

import android.content.Context;
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
import com.quotescollection.quotesdata.interfaces.CatogryClickListener;
import com.quotescollection.quotesdata.interfaces.image_click;
import com.quotescollection.quotesdata.models.CategoryModel;
import com.quotescollection.quotesdata.models.image_model;

import java.util.ArrayList;
import java.util.List;

public class image_adapter extends RecyclerView.Adapter<image_adapter.ViewHolder> {

    private Context context;
    private List<image_model> modelList = new ArrayList<>();
    image_click imageClick;


    public image_adapter(Context context, image_click imageClick) {
        this.context = context;
        this.imageClick = imageClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.imageview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final image_model imageModel = modelList.get(position);

        Glide.with(context)
                .load(imageModel.getImage())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Pos" + position, Toast.LENGTH_SHORT).show();

                imageClick.imageClick(imageModel.getImage());
            }
        });



    }

    public void setList(List<image_model> list) {
        this.modelList = list;
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
