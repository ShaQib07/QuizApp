package com.shakib.bdlabit.pmpquizprep;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.DBRepo;
import com.shakib.bdlabit.pmpquizprep.database.Favourite;
import com.shakib.bdlabit.pmpquizprep.database.MockDB;
import com.shakib.bdlabit.pmpquizprep.database.PracticeDB;
import com.shakib.bdlabit.pmpquizprep.database.QuestionDB;
import com.shakib.bdlabit.pmpquizprep.database.QuestionMarkDB;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;

public class Practice extends AppCompatActivity {

    RelativeLayout containerView;
    ProgressBar timer;

    ImageButton cancelButton, favoriteButton;
    TextView question, counter, explanation;
    Button nextButton;
    RadioGroup options;
    RadioButton option1, option2, option3, option4;
    QuestionDB ques;

    Realm realm;
    DBRepo dbRepo;

    int i = 2, progress = 0, index = 0, right = 0, wrong = 0;
    String subName, pracName;
    List<QuestionDB> quesList;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        pracName = getIntent().getStringExtra("prac");

        DBsetup();
        viewSetup();
        fetchData();
        onClick();
        populateView();
    }

    private void onClick() {
        favoriteButton.setOnClickListener(v -> {
            favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
            Favourite favourite = new Favourite();
            favourite.setSubjectName(subName);
            favourite.setQuestion(ques);
            favourite.setId(subName+"_"+ques.getQsnNumber());
            realm.executeTransaction(realm -> realm.insertOrUpdate(favourite));

        });
        nextButton.setOnClickListener(v -> nextQuestion());
        cancelButton.setOnClickListener(v -> {
            counter.setText("10/10");
            nextQuestion();
        });
    }

    private void nextQuestion() {

        if (counter.getText().toString().equals("10/10")) {
            countDownTimer.cancel();
            wrong = 10 - right;

            PracticeDB practiceDB = new PracticeDB();
            practiceDB.setPracticeName(pracName);
            practiceDB.setSubjectName(subName);

            realm.executeTransaction(realm -> {
                realm.insertOrUpdate(practiceDB);
            });

            Result.startResult(this, right, wrong, "Practice");
            finish();
        } else {
            counter.setText("" + i + "/10");
            if (i == 10)
                nextButton.setText("Finish");
            i++;
            Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_animation);
            containerView.startAnimation(myAnim);
            populateView();
        }
    }

    private void fetchData() {
        index = 0;
        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        quesList = dbRepo.getSubjectWiseRandomQuestion(subName, 10);
    }

    private void viewSetup() {
        containerView = findViewById(R.id.containerView);
        timer = findViewById(R.id.timer);
        question = findViewById(R.id.question);
        counter = findViewById(R.id.counter);
        options = findViewById(R.id.options);
        option1 = findViewById(R.id.option_one);
        option2 = findViewById(R.id.option_two);
        option3 = findViewById(R.id.option_three);
        option4 = findViewById(R.id.option_four);
        explanation = findViewById(R.id.explanation);
        cancelButton = findViewById(R.id.cancel_button);
        favoriteButton = findViewById(R.id.favorite_button);
        nextButton = findViewById(R.id.next_button);
    }

    private void DBsetup() {

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);
    }

    private void populateView() {

        startTimer();

        favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        explanation.setVisibility(View.INVISIBLE);

        ques = quesList.get(index);
        index++;

                question.setText(ques.getQsn());
                options.setVisibility(View.VISIBLE);
                options.clearCheck();
                option1.setEnabled(true);
                option2.setEnabled(true);
                option3.setEnabled(true);
                option4.setEnabled(true);
                option1.setTextColor(getResources().getColor(R.color.black));
                option2.setTextColor(getResources().getColor(R.color.black));
                option3.setTextColor(getResources().getColor(R.color.black));
                option4.setTextColor(getResources().getColor(R.color.black));
                option1.setText("A. "+ques.getOptions1());
                option2.setText("B. "+ques.getOptions2());
                option3.setText("C. "+ques.getOptions3());
                option4.setText("D. "+ques.getOptions4());

                options.setOnCheckedChangeListener((group, checkedId) -> {
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                    option3.setEnabled(false);
                    option4.setEnabled(false);

                    checkAnswer(ques);

                    if (option1.isChecked()||option2.isChecked()||option3.isChecked()||option4.isChecked()){
                        int correctAns = Integer.valueOf(ques.getCorrectAns());
                        RadioButton userAns = findViewById(options.getCheckedRadioButtonId());
                        userAns.setTextColor(getResources().getColor(R.color.redish));

                        switch (correctAns){
                            case 1:
                                option1.setTextColor(getResources().getColor(R.color.light_green));
                                break;
                            case 2:
                                option2.setTextColor(getResources().getColor(R.color.light_green));
                                break;
                            case 3:
                                option3.setTextColor(getResources().getColor(R.color.light_green));
                                break;
                            case 4:
                                option4.setTextColor(getResources().getColor(R.color.light_green));
                                break;
                        }
                    }

                    String ex = ques.getExplanation();
                    if (ex != null){
                        explanation.setVisibility(View.VISIBLE);
                        explanation.setText("EXPLANATION: "+ex);
                    }


                });


            }

    private void startTimer() {

        progress = 0;
        timer.setProgress(progress);

        if (countDownTimer != null){
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress++;
                timer.setProgress(progress*100/(20000/1000));
            }

            @Override
            public void onFinish() {
                progress++;
                timer.setProgress(100);
                nextQuestion();
            }
        };

        countDownTimer.start();

    }

    private void checkAnswer(QuestionDB ques) {

        if (option1.isChecked()||option2.isChecked()||option3.isChecked()||option4.isChecked()){

            int correctAns = Integer.valueOf(ques.getCorrectAns());
            String correct = "";
            RadioButton userAns = findViewById(options.getCheckedRadioButtonId());
            int answeredOption = -1;
            switch (correctAns){
                case 1:
                    correct = option1.getText().toString();
                    answeredOption = 1;
                    break;
                case 2:
                    correct = option2.getText().toString();
                    answeredOption = 2;
                    break;
                case 3:
                    correct = option3.getText().toString();
                    answeredOption = 3;
                    break;
                case 4:
                    correct = option4.getText().toString();
                    answeredOption = 4;
                    break;
            }

            if (userAns.getText().toString().equals(correct)){
                right++;
            } else {
                wrong++;
            }




        }
    }




}
