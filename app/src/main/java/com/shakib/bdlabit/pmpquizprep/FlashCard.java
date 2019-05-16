package com.shakib.bdlabit.pmpquizprep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.DBRepo;
import com.shakib.bdlabit.pmpquizprep.database.FlashDB;
import com.shakib.bdlabit.pmpquizprep.database.QuestionDB;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import io.realm.Realm;

public class FlashCard extends FragmentActivity {

    private InterstitialAd interstitialAd;

    EasyFlipView flip;
    TextView ques, ans, counter;
    Button btn;
    Realm realm;
    DBRepo dbRepo;
    String subName, flashName;
    List<QuestionDB> quesList;
    QuestionDB question = new QuestionDB();
    int i = 0, c = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        flashName = getIntent().getStringExtra("flash");

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);

        flip = findViewById(R.id.flip_view);
        ques = findViewById(R.id.flashcard_ques);
        ans = findViewById(R.id.flashcard_ans);
        counter = findViewById(R.id.counter_flash);

        btn = findViewById(R.id.btn);


        getQuestion();

        btn.setOnClickListener(v -> {
            flip.flipTheView();
            if (i == 0){
                if (c == 11){
                    btn.setText("Finish");
                } else {
                    btn.setText("next question");
                }
                i = 1;
                getAnswer();
                btn.setBackgroundColor(getResources().getColor(R.color.redish));

            } else {
                if (btn.getText().toString().equalsIgnoreCase("Finish")){
                    FlashDB flashDB = new FlashDB();
                    flashDB.setFlashCardName(flashName);
                    flashDB.setSubjectName(subName);

                    realm.executeTransaction(realm -> {
                        realm.insertOrUpdate(flashDB);
                    });
                    Toast.makeText(FlashCard.this, "FlashCard Finished", Toast.LENGTH_SHORT).show();
                    if (interstitialAd.isLoaded()){
                        interstitialAd.show();
                    } else {
                        finish();
                    }
                }
                getQuestion();
                counter.setText(c+"/10");
                c++;
                btn.setText("see answer");
                btn.setBackgroundColor(getResources().getColor(R.color.light_green));
                i = 0;
            }
        });

        showAds();
        showInterstitialAds();


    }

    private void showAds() {
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        AdView adView = findViewById(R.id.banner_ad);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("FBFB1CF2E4D9FD9AA66C45BEBAE661B2").build();
        adView.loadAd(adRequest);
    }

    private void showInterstitialAds() {
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("FBFB1CF2E4D9FD9AA66C45BEBAE661B2").build());
        interstitialAd.setAdListener(new AdListener()
                                     {
                                         @Override
                                         public void onAdClosed() {
                                             interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("FBFB1CF2E4D9FD9AA66C45BEBAE661B2").build());
                                             finish();
                                         }
                                     }
        );
    }

    private void getQuestion() {



        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        quesList = dbRepo.getSubjectWiseRandomQuestion(subName, 1);

        question = quesList.get(0);
        ques.setText(question.getQsn());
        //Toast.makeText(this, ""+question.getQsn(), Toast.LENGTH_SHORT).show();


    }

    private void getAnswer() {



        int answer = Integer.valueOf(question.getCorrectAns());
        switch (answer){
            case 1:
                ans.setText(question.getOptions1());
                //Toast.makeText(this, ""+question.getOptions1(), Toast.LENGTH_SHORT).show();
                break;
            case 2:
                ans.setText(question.getOptions2());
                //Toast.makeText(this, ""+question.getOptions2(), Toast.LENGTH_SHORT).show();
                break;
            case 3:
                ans.setText(question.getOptions3());
                //Toast.makeText(this, ""+question.getOptions3(), Toast.LENGTH_SHORT).show();
                break;
            case 4:
                ans.setText(question.getOptions4());
                //Toast.makeText(this, ""+question.getOptions4(), Toast.LENGTH_SHORT).show();
                break;
        }


    }
}
