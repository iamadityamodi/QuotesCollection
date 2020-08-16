package com.quotescollection.quotesdata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.quotescollection.quotesdata.R;
import com.quotescollection.quotesdata.interfaces.CatogryClickListener;
import com.quotescollection.quotesdata.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class QuotesListAdapter extends RecyclerView.Adapter<QuotesListAdapter.ViewHolder> {


    private Context context;
    private List<CategoryModel> modelList = new ArrayList<>();
    private CatogryClickListener listener;

    public QuotesListAdapter(Context context, CatogryClickListener listener) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CategoryModel model = modelList.get(position);
        holder.textView.setText(model.getName());
        holder.textView.setSelected(true);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                listener.onClick(model.getName());
            }
        });

    }

    public void setList(List<CategoryModel> list){
        this.modelList = list;
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        ImageView imageviewmain;



        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.textviewname);
            cardView = itemView.findViewById(R.id.cardviewmain);
            imageviewmain = itemView.findViewById(R.id.imageviewmain);

        }
    }
}
