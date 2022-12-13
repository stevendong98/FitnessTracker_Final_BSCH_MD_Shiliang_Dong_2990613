package com.developerrr.fitnesstracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.utils.Qoute;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.MyHolder> {

    Context context;
    List<Qoute> qoutes;


    public QuotesAdapter(Context context, List<Qoute> qoutes) {
        this.context = context;
        this.qoutes = qoutes;

    }

    public void AddItem(Qoute qoute){
        for (Qoute q:qoutes){
            if(q.getCategory().equals(qoute.getCategory())){
                return;
            }
        }
        qoutes.add(qoute);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_quote_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        Qoute qoute=qoutes.get(position);

        holder.message.setText(qoute.getMessage());
        holder.date.setText(qoute.getCategory());
        holder.author.setText(qoute.getAuther());



        if(position%2==0) {
            holder.rootCard.setBackgroundResource(R.drawable.gradient_two);
        }else {
            holder.rootCard.setBackgroundResource(R.drawable.gradient_four);
        }


    }

    @Override
    public int getItemCount() {
        return qoutes.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView message,date,author;
        CardView rootCard;
        ImageButton fav_Btn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            message=itemView.findViewById(R.id.message);
            date=itemView.findViewById(R.id.date);
            author=itemView.findViewById(R.id.author);
            rootCard=itemView.findViewById(R.id.root_card);

        }
    }
}
