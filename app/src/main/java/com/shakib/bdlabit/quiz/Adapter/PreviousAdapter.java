package com.shakib.bdlabit.quiz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shakib.bdlabit.quiz.Answer;
import com.shakib.bdlabit.quiz.R;
import com.shakib.bdlabit.quiz.database.MockDB;

import io.realm.RealmList;

public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.MyViewHolder> {

    private RealmList<MockDB> mList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView correctAns, wrongAns, status, mockName;

        public MyViewHolder(View view) {
            super(view);
            correctAns = view.findViewById(R.id.correct_ans);
            wrongAns = view.findViewById(R.id.wrong_ans);
            status = view.findViewById(R.id.status);
            mockName = view.findViewById(R.id.mock_name);
        }

    }

    Context context;

    public PreviousAdapter(Context context) {
        mList = new RealmList<>();
        this.context = context;
    }

    public void setData(RealmList<MockDB> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MockDB mockDB = mList.get(position);
        holder.correctAns.setText("Correct Answer: " + mockDB.getCorrectAns());
        holder.wrongAns.setText("Wrong Answer: " + mockDB.getWrongAns());
        holder.status.setText(mockDB.getStatus());
        holder.mockName.setText(mockDB.getMockupName());

        holder.itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, Answer.class).putExtra("mockupNo", mockDB.getMockupName()));
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}

