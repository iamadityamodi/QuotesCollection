package com.quotescollection.quotesdata.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quotescollection.quotesdata.BuildConfig;
import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.activities.GetAuterQuoteAvtivity;
import com.quotescollection.quotesdata.database.DatabaseHelper;
import com.quotescollection.quotesdata.interfaces.FevActivityClickListener;
import com.quotescollection.quotesdata.models.CategoryListModel;

import java.util.ArrayList;

public class FevoriteAdapter extends RecyclerView.Adapter<FevoriteAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryListModel> categoryListModels;
    FevActivityClickListener fevActivityClickListener;

    int pos;

    public FevoriteAdapter(Context context, ArrayList<CategoryListModel> categoryListModels, FevActivityClickListener fevActivityClickListener) {
        this.context = context;
        this.categoryListModels = categoryListModels;
        this.fevActivityClickListener=fevActivityClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fevorite_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final CategoryListModel categoryListModel = categoryListModels.get(position);

        holder.fev_quotes.setText(categoryListModel.getQuote());
        holder.fev_author.setText(categoryListModel.getAutor());

        holder.fev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pos = position;

                fevActivityClickListener.onClick(categoryListModel.getQuote(), position);

            }
        });

        holder.fev_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyData();
            }
        });



        holder.fev_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "Quote : " + categoryListModels.get(position).getQuote() + "\n\n" + "Author : " +
                        categoryListModels.get(position).getAutor() + "\n\n\n" + "App Link : " + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_using)));

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryListModels.size();
    }


    private void CopyData() {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Quote : " + categoryListModels.get(pos).getQuote() + "\n" + "Author : " + categoryListModels.get(pos).getAutor(),
                "Quote : " + categoryListModels.get(pos).getQuote() + "\n" + "Author : " + categoryListModels.get(pos).getAutor() + "\n\n\n" + "App Link : " + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Copy To Clipboard", Toast.LENGTH_SHORT).show();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fev_quotes, fev_author;
        ImageView fev, fev_share, fev_copy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fev_quotes = itemView.findViewById(R.id.fev_quotes);
            fev_author = itemView.findViewById(R.id.fev_author);
            fev = itemView.findViewById(R.id.fev);
            fev_share = itemView.findViewById(R.id.fev_share);
            fev_copy = itemView.findViewById(R.id.fev_copy);

        }
    }
}
