package com.shakib.bdlabit.pmpquizprep.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shakib.bdlabit.pmpquizprep.Common.common;
import com.shakib.bdlabit.pmpquizprep.Dashboard;
import com.shakib.bdlabit.pmpquizprep.FlashCard;
import com.shakib.bdlabit.pmpquizprep.Interface.itemClickListener;
import com.shakib.bdlabit.pmpquizprep.LocSub;
import com.shakib.bdlabit.pmpquizprep.MockTest;
import com.shakib.bdlabit.pmpquizprep.Model.FlashCardListItem;
import com.shakib.bdlabit.pmpquizprep.Model.MockListItem;
import com.shakib.bdlabit.pmpquizprep.Model.Subject;
import com.shakib.bdlabit.pmpquizprep.R;

import java.util.ArrayList;
import java.util.logging.Handler;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.shakib.bdlabit.pmpquizprep.R.color.light_green;

public class FlashCardAdapter extends RecyclerView.Adapter<FlashCardAdapter.FlashCardAdapterViewHolder> {

    public Context c;
    public ArrayList<FlashCardListItem> arrayList;

    public FlashCardAdapter(Context c, ArrayList<FlashCardListItem> arrayList){
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FlashCardAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mock_list,parent,false);

        return new FlashCardAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FlashCardAdapterViewHolder holder, int position) {
        final String mockNo = arrayList.get(position).getFlashCardName();

        holder.mockNo.setText(mockNo);

        if (arrayList.get(position).isCompleted()){
            holder.card.setCardBackgroundColor(c.getResources().getColor(R.color.redish));
            holder.mockLock.setVisibility(View.INVISIBLE);
            holder.setItemClickListener((view, position1, isLongClick) -> {
                        Toast.makeText(c, "Already Completed", Toast.LENGTH_SHORT).show();
                    }
            );
        } else {
            holder.card.setCardBackgroundColor(c.getResources().getColor(light_green));
            holder.mockLock.setVisibility(View.VISIBLE);
            holder.setItemClickListener((view, position1, isLongClick) -> {
                String mockNo1 = "FlashCard "+(position1 +1);
                c.startActivity(new Intent(c.getApplicationContext(), FlashCard.class).putExtra("mock", mockNo1));
            });
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class FlashCardAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mockNo;
        ImageView mockLock;
        CardView card;

        private itemClickListener itemClickListener;

        public FlashCardAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mockNo = itemView.findViewById(R.id.mock_no);
            mockLock = itemView.findViewById(R.id.mock_lock);
            card = itemView.findViewById(R.id.card);

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
}

