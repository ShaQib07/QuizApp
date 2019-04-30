package com.shakib.bdlabit.pmpquizprep.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.shakib.bdlabit.pmpquizprep.Interface.itemClickListener;
import com.shakib.bdlabit.pmpquizprep.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class subjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView subName;
    public CardView subCard;

    private itemClickListener itemClickListener;

    public subjectViewHolder(@NonNull View itemView) {
        super(itemView);

        subCard = itemView.findViewById(R.id.subject_card);
        subName = itemView.findViewById(R.id.sub_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}