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
import com.shakib.bdlabit.pmpquizprep.database.QuestionDB;
import com.shakib.bdlabit.pmpquizprep.database.QuestionMarkDB;
import io.realm.RealmList;

public class SeeAnswerAdapter extends RecyclerView.Adapter<SeeAnswerAdapter.MockAdapterViewHolder> {

    public Context c;
    public RealmList<QuestionMarkDB> arrayList;

    public SeeAnswerAdapter(Context c){
        this.c = c;
        this.arrayList = new RealmList<>();
    }

    public void setData(RealmList<QuestionMarkDB> arrayList){
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MockAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_see_answer,parent,false);

        return new MockAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MockAdapterViewHolder holder, int position) {

        int selectedOption = arrayList.get(position).getSelectedOption();
       // QuestionDB ques = arrayList.get(position).getQuestionDB();

        holder.question.setText(arrayList.get(position).getQsn());
        holder.options.setVisibility(View.VISIBLE);
        holder.options.clearCheck();
        holder.option1.setEnabled(false);
        holder.option2.setEnabled(false);
        holder.option3.setEnabled(false);
        holder.option4.setEnabled(false);
        holder.option1.setText("A. " + arrayList.get(position).getOptions1());
        holder.option2.setText("B. " + arrayList.get(position).getOptions2());
        holder.option3.setText("C. " + arrayList.get(position).getOptions3());
        holder.option4.setText("D. " + arrayList.get(position).getOptions4());

        int correctAns = Integer.valueOf(arrayList.get(position).getCorrectAns());

        switch (correctAns){
            case 1:
                holder.option1.setEnabled(true);
                holder.option1.setChecked(true);
                break;
            case 2:
                holder.option2.setEnabled(true);
                holder.option2.setChecked(true);
                break;
            case 3:
                holder.option3.setEnabled(true);
                holder.option3.setChecked(true);
                break;
            case 4:
                holder.option4.setEnabled(true);
                holder.option4.setChecked(true);
                break;
        }


       // holder.options.check(Integer.parseInt(arrayList.get(position).getCorrectAns()));

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

        TextView question;
        RadioGroup options;
        RadioButton option1, option2, option3, option4;

        public MockAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            options = itemView.findViewById(R.id.options);
            option1 = itemView.findViewById(R.id.option_one);
            option2 = itemView.findViewById(R.id.option_two);
            option3 = itemView.findViewById(R.id.option_three);
            option4 = itemView.findViewById(R.id.option_four);

        }


    }
}

