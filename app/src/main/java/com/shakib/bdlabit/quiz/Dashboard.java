package com.shakib.bdlabit.quiz;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shakib.bdlabit.quiz.Common.common;
import com.shakib.bdlabit.quiz.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.quiz.database.DBRepo;

import io.realm.Realm;

public class Dashboard extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    public  static Activity dashBoard;

     CardView fullMock, practice, chapter, flashCard, previousResult, importantLink, goPro, changeSubject;
     ImageButton share, rate, favorite;
     AdView adView;
     Realm realm;
     DBRepo dbRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        common.isFirstTime = false;
        dashBoard = this;
        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);

        String subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        setTitle(subName);
        setUpOnClickListener();
        showAds();

    }

    private void showAds() {
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        adView = findViewById(R.id.banner_ad);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("FBFB1CF2E4D9FD9AA66C45BEBAE661B2").build();
        adView.loadAd(adRequest);
    }

    private void setUpOnClickListener() {
        fullMock = findViewById(R.id.fullmock);
        fullMock.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, FullMock.class)));

        practice = findViewById(R.id.practice);
        practice.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, PracticeActivity.class)));

        chapter = findViewById(R.id.chapter);
        chapter.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,Chapter.class)));

        flashCard = findViewById(R.id.flashcard);
        flashCard.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,FlashCardActivity.class)));

        previousResult = findViewById(R.id.previous_result);
        previousResult.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,PreviousResult.class)));

        importantLink = findViewById(R.id.important_link);
        importantLink.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,ImportantLink.class)));

        goPro = findViewById(R.id.go_pro);
        goPro.setOnClickListener(v -> {

        });

        changeSubject = findViewById(R.id.change_subject);
        changeSubject.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, LocSub.class));
        });

        share = findViewById(R.id.share);
        share.setOnClickListener(v -> {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            final String appPackageName = getApplicationContext().getPackageName();
            String appLink="https://play.google.com/store/apps/details?id=" +appPackageName;

            intent.setType("text/plain");
            String shareBody = "Hey!" + "\n"+""+appLink;
            String shareSub = "APP NAME/TITLE";
            intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(intent, "Share Using"));
        });

        rate = findViewById(R.id.rate);
        rate.setOnClickListener(v -> {
            final String appPackageName = getApplicationContext().getPackageName();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +appPackageName)));
        });

        favorite = findViewById(R.id.favorite);
        favorite.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, Favorite.class)));

    }
}
