package com.shakib.bdlabit.pmpquizprep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.MockDB;
import com.shakib.bdlabit.pmpquizprep.database.PracticeDB;

import io.realm.Realm;

public class Result extends AppCompatActivity {

    TextView correctAns, wrongAns, status;
    int right = 0, wrong = 0;
    TextView seeAnswer;
    String mockupNo;
    public static void startResult(Activity activity, int right, int wrong, String mockUpName){

        Intent intent = new Intent(activity, Result.class);
        intent.putExtra("RIGHT", right);
        intent.putExtra("WRONG", wrong);
        intent.putExtra("mockupNo", mockUpName);
        activity.startActivity(intent);
        activity.finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        right = getIntent().getIntExtra("RIGHT", 0);
        wrong = getIntent().getIntExtra("WRONG", 0);
        mockupNo = getIntent().getStringExtra("mockupNo");


        correctAns = findViewById(R.id.correct_ans);
        wrongAns = findViewById(R.id.wrong_ans);
        status = findViewById(R.id.status);
        seeAnswer = findViewById(R.id.see_answer);

        correctAns.setText("Correct Answer: " + right);
        wrongAns.setText("Wrong Answer: " + wrong);

        if (right < 4) {
            status.setText("FAILED");
        } else {
            status.setText("PASSED");
        }

        if (mockupNo.contains("Practice")){
            seeAnswer.setVisibility(View.GONE);
        }

        seeAnswer.setOnClickListener(v -> startActivity(new Intent(Result.this, Answer.class).putExtra("mockupNo", mockupNo)));

    }
}
