package com.shakib.bdlabit.quiz.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shakib.bdlabit.quiz.Interface.itemClickListener;
import com.shakib.bdlabit.quiz.MockTest;
import com.shakib.bdlabit.quiz.Model.MockListItem;
import com.shakib.bdlabit.quiz.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.shakib.bdlabit.quiz.R.color.light_green;

public class MockAdapter extends RecyclerView.Adapter<MockAdapter.MockAdapterViewHolder>{

    public Activity c;
    public ArrayList<MockListItem> arrayList;
    String mockNo1;

    public MockAdapter(Activity c, ArrayList<MockListItem> arrayList){
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MockAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mock_list,parent,false);

        return new MockAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MockAdapterViewHolder holder, int position) {

        final String mockNo = arrayList.get(position).getMockName();

        holder.mockNo.setText(mockNo);

        if (arrayList.get(position).isCompleted()){
            holder.card.setCardBackgroundColor(c.getResources().getColor(R.color.redish));
            holder.mockLock.setVisibility(View.INVISIBLE);

        } else {
            holder.card.setCardBackgroundColor(c.getResources().getColor(light_green));
            holder.mockLock.setVisibility(View.VISIBLE);

        }

        holder.setItemClickListener((view, position1, isLongClick) -> {
            mockNo1 = "Mock "+(position1 +1);
            c.startActivity(new Intent(c.getApplicationContext(), MockTest.class).putExtra("mock", mockNo1));
            c.finish();
        });


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class MockAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mockNo;
        ImageView mockLock;
        CardView card;

        private itemClickListener itemClickListener;

        public MockAdapterViewHolder(@NonNull View itemView) {
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

