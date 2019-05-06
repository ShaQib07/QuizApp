package com.shakib.bdlabit.pmpquizprep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shakib.bdlabit.pmpquizprep.R;
import com.shakib.bdlabit.pmpquizprep.database.Favourite;
import com.shakib.bdlabit.pmpquizprep.database.QuestionDB;
import com.shakib.bdlabit.pmpquizprep.database.QuestionMarkDB;
import io.realm.RealmList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MockAdapterViewHolder> {

    public Context c;
    public RealmList<Favourite> arrayList;

    public FavouriteAdapter(Context c){
        this.c = c;
        this.arrayList = new RealmList<>();
    }

    public void setData(RealmList<Favourite> arrayList){
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MockAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_list,parent,false);

        return new MockAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MockAdapterViewHolder holder, int position) {

        holder.question.setText(arrayList.get(position).getQuestion().getQsn());
        holder.option1.setText("A. " + arrayList.get(position).getQuestion().getOptions1());
        holder.option2.setText("B. " + arrayList.get(position).getQuestion().getOptions2());
        holder.option3.setText("C. " + arrayList.get(position).getQuestion().getOptions3());
        holder.option4.setText("D. " + arrayList.get(position).getQuestion().getOptions4());
        int correct = Integer.parseInt(arrayList.get(position).getQuestion().getCorrectAns());

        switch (correct){
            case 1:
                holder.correctAns.setText("Correct Answer: "+arrayList.get(position).getQuestion().getOptions1());
                break;

            case 2:
                holder.correctAns.setText("Correct Answer: "+arrayList.get(position).getQuestion().getOptions2());
                break;
            case 3:
                holder.correctAns.setText("Correct Answer: "+arrayList.get(position).getQuestion().getOptions3());
                break;
            case 4:
                holder.correctAns.setText("Correct Answer: "+arrayList.get(position).getQuestion().getOptions4());
                break;
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

    public class MockAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView question, option1, option2, option3, option4, correctAns;


        public MockAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.fav_ques);
            option1 = itemView.findViewById(R.id.fav_op1);
            option2 = itemView.findViewById(R.id.fav_op2);
            option3 = itemView.findViewById(R.id.fav_op3);
            option4 = itemView.findViewById(R.id.fav_op4);
            correctAns = itemView.findViewById(R.id.fav_ans);

        }


    }
}

