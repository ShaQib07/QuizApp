package com.shakib.bdlabit.pmpquizprep.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shakib.bdlabit.pmpquizprep.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static ClickListener clickListener;
    private List<String> mList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView subName;
        public CardView subCard;

        public MyViewHolder(View view) {
            super(view);
            subName = view.findViewById(R.id.sub_name);
            subCard = view.findViewById(R.id.subject_card);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerAdapter.clickListener = clickListener;
    }


    public RecyclerAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.subName.setText(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public String getItem(int position) {
        return mList.get(position);
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
