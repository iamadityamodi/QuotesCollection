package com.quotescollection.quotesdata.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.interfaces.CatogryClickListener;
import com.quotescollection.quotesdata.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;

public class QuotesCollectionAdapter extends RecyclerView.Adapter<QuotesCollectionAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> modelList = new ArrayList<>();
    private CatogryClickListener listener;
    private int[] mMaterialColors;

    public QuotesCollectionAdapter(Context context, CatogryClickListener listener) {
        this.context = context;
        this.listener = listener;
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

        final CategoryModel model = modelList.get(position);
        holder.textView.setText(model.getName());
        holder.textView.setSelected(true);
//        holder.textView.setTextSize(27f);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position, model.getName(), null, null, 0, null);
            }
        });

        mMaterialColors = context.getResources().getIntArray(R.array.colorname);


        int[] androidColors = context.getResources().getIntArray(R.array.colorname);
        int color = androidColors[new Random().nextInt(androidColors.length)];




        for (int i = 0; i < modelList.size(); i++) {
            String name2 = model.getName().substring(0, 1);
            holder.firstlatter.setText(name2);

        }


        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(color);
        holder.icon.setBackground(draw);




        

       /* Drawable background = holder.icon.getBackground();

        if (background instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            for (int i = 0; i < modelList.size(); i++) {
                if (modelList.size() == androidColors.length) {
                    shapeDrawable.getPaint().setColor(context.getResources().getColor(androidColors[i]));
                }
            }
        } else if (background instanceof GradientDrawable) {

            GradientDrawable gradientDrawable = (GradientDrawable) background;

            for (int i = 0; i < modelList.size(); i++) {
                gradientDrawable.setColor(context.getResources().getColor(androidColors[i]));
            }


        } else if (background instanceof ColorDrawable) {
            // alpha value may need to be set again after this call
            ColorDrawable colorDrawable = (ColorDrawable) background;
            colorDrawable.setColor(context.getResources().getColor(R.color.color2));
        }*/


        Log.e("onBindViewHolder", "onBindViewHolder: " + mMaterialColors.length);


      /*  Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.relativetext.setBackgroundColor(color);*/

    }

    public void setList(List<CategoryModel> list) {
        this.modelList = list;
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView, firstlatter;
        CardView cardView;
        RelativeLayout icon;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.textviewname);
            firstlatter = itemView.findViewById(R.id.firstlatter);
            cardView = itemView.findViewById(R.id.cardviewmain);
            icon = itemView.findViewById(R.id.imageviewmain);

        }
    }
}
