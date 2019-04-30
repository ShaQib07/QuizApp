package com.shakib.bdlabit.pmpquizprep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.DBRepo;
import com.shakib.bdlabit.pmpquizprep.database.QuestionDB;

import java.util.List;

import io.realm.Realm;

public class FlashCard extends AppCompatActivity {

    Realm realm;
    DBRepo dbRepo;
    String subName;
    List<QuestionDB> quesList;
    QuestionDB question = new QuestionDB();

    TextView ques;
    Button btn;
    CardView flashCard;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);

        ques = findViewById(R.id.flashcard_ques);
        btn = findViewById(R.id.flashcard_btn);
        flashCard = findViewById(R.id.flash_card);

        getQuestion();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i == 0){
                    getAnswer();
                } else {
                    getQuestion();
                }

            }
        });



    }

    private void getQuestion() {

        Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        flashCard.startAnimation(myAnim);

        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        quesList = dbRepo.getSubjectWiseRandomQuestion(subName, 1);

        question = quesList.get(0);
        ques.setText(question.getQsn());
        btn.setText("See Answer");
        i = 0;
    }

    private void getAnswer() {

        Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        flashCard.startAnimation(myAnim);

        int ans = Integer.valueOf(question.getCorrectAns());
        switch (ans){
            case 1:
                ques.setText(question.getOptions1());
                break;
            case 2:
                ques.setText(question.getOptions2());
                break;
            case 3:
                ques.setText(question.getOptions3());
                break;
            case 4:
                ques.setText(question.getOptions4());
                break;
        }

        btn.setText("Next Question");
        i = 1;
    }
}
