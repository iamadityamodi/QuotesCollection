package com.quotescollection.quotesdata.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.interfaces.ColorClickListener;

import java.util.ArrayList;

public class colorAdapter extends RecyclerView.Adapter<colorAdapter.ViewHolder> {


    Context context;
    ArrayList<Integer> colorarray;
    int selectpos;
    ColorClickListener colorClickListener;


    public colorAdapter(Context context, ArrayList<Integer> colorarray,int selectpos,ColorClickListener colorClickListener) {
        this.context = context;
        this.colorarray = colorarray;
        this.colorClickListener = colorClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.color_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.normaldrawable.setColorFilter(context.getResources().getColor(colorarray.get(position)));



        holder.normaldrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorClickListener.click(position,colorarray.get(position));

                selectpos = position;
                notifyDataSetChanged();
            }
        });
 
        if (selectpos == position)
        {
            holder.strokedrawable.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.strokedrawable.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return colorarray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView normaldrawable, strokedrawable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            normaldrawable = itemView.findViewById(R.id.normal_drawable);
            strokedrawable = itemView.findViewById(R.id.stroke_drawable);

        }

    }
}
