package com.shakib.bdlabit.pmpquizprep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.DBRepo;
import com.shakib.bdlabit.pmpquizprep.database.QuestionDB;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import io.realm.Realm;

public class FlashCard extends FragmentActivity {

    EasyFlipView flip;
    TextView ques, ans;
    Button nextQues, seeAns;
    Realm realm;
    DBRepo dbRepo;
    String subName;
    List<QuestionDB> quesList;
    QuestionDB question = new QuestionDB();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);

        flip = findViewById(R.id.flip_view);
        ques = findViewById(R.id.flashcard_ques);
        ans = findViewById(R.id.flashcard_ans);

        seeAns = findViewById(R.id.btn_question);
        nextQues = findViewById(R.id.btn_answer);

        getQuestion();

        seeAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip.flipTheView();
                getAnswer();
            }
        });

        nextQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip.flipTheView();
                getQuestion();
            }
        });




    }

    private void getQuestion() {



        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        quesList = dbRepo.getSubjectWiseRandomQuestion(subName, 1);

        question = quesList.get(0);
        ques.setText(question.getQsn());


    }

    private void getAnswer() {



        int answer = Integer.valueOf(question.getCorrectAns());
        switch (answer){
            case 1:
                ans.setText(question.getOptions1());
                break;
            case 2:
                ans.setText(question.getOptions2());
                break;
            case 3:
                ans.setText(question.getOptions3());
                break;
            case 4:
                ans.setText(question.getOptions4());
                break;
        }


    }
}
