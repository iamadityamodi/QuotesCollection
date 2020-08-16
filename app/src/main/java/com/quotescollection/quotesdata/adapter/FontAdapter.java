package com.quotescollection.quotesdata.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.interfaces.FontInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontViewHolder> {
    Context mContext;

    FontInterface fontInterface;
    Typeface position_font;
    private ArrayList<String> fontstylename = new ArrayList<>();
    private ArrayList<Typeface> fontstyle = new ArrayList<>();
    public static int selectedposition = 0;
    int final_font_ttf = 0;


    public FontAdapter(Context mContext, FontInterface fontInterface) {
        this.mContext = mContext;
        this.fontInterface = fontInterface;
        getallfontstyle();
    }

    public FontAdapter(Context mContext, FontInterface fontInterface, Typeface position_font) {
        this.position_font = position_font;
        this.mContext = mContext;
        this.fontInterface = fontInterface;
        getallfontstyle();


        for (int i = 0; i < fontstyle.size(); i++) {
            if (fontstyle.get(i).equals(position_font)) {

                final_font_ttf = i;

            } else {
            }
        }

    }


    @NonNull
    @Override
    public FontViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.font_item, viewGroup, false);
        return new FontViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FontViewHolder viewHolder, final int i) {

        if (selectedposition != -1) {

            if (selectedposition == i) {
                viewHolder.textstyleitem.setTextColor(mContext.getResources().getColor(R.color.red));
            } else {
                viewHolder.textstyleitem.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        } else {

            if (position_font != null) {

                if (position_font == fontstyle.get(i)) {
                    viewHolder.textstyleitem.setTextColor(mContext.getResources().getColor(R.color.red));

                } else {
                    viewHolder.textstyleitem.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            } else {
                viewHolder.textstyleitem.setTextColor(mContext.getResources().getColor(R.color.black));
            }


        }


        viewHolder.textstyleitem.setTypeface(fontstyle.get(i));
        viewHolder.textstyleitem.setText("Quotes");

        viewHolder.fontbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fontInterface != null) {
                    fontInterface.FontClick(fontstyle.get(i), fontstylename.get(i));
                    selectedposition = i;
                    notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return fontstyle.size();

    }

    public class FontViewHolder extends RecyclerView.ViewHolder {


        private LinearLayout fontbackground;
        private TextView textstyleitem;
        private ImageView checked;

        public FontViewHolder(@NonNull View v) {
            super(v);


            textstyleitem = itemView.findViewById(R.id.textstyleitem);
            fontbackground = itemView.findViewById(R.id.fontbackground);
            checked = itemView.findViewById(R.id.checked);

        }
    }


    private void getallfontstyle() {

        AssetManager assetManager = mContext.getAssets();
        try {

            String[] files = assetManager.list("fonts");
            fontstylename = new ArrayList<String>(Arrays.asList(files));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String name : fontstylename) {


            fontstyle.add(Typeface.createFromAsset(mContext.getAssets(), "fonts/" + name));
        }
    }

}
