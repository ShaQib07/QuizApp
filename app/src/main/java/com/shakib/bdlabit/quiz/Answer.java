package com.shakib.bdlabit.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shakib.bdlabit.quiz.Adapter.SeeAnswerAdapter;
import com.shakib.bdlabit.quiz.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.quiz.database.MockDB;

import java.util.Objects;

import io.realm.Realm;

public class Answer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAnalytics mFirebaseAnalytics;

    private ActionBarDrawerToggle drawerToggle;
    private InterstitialAd interstitialAd;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String mockupNo;
    String subName;
    Realm realm;
    Button back;

    SeeAnswerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        realm = Realm.getDefaultInstance();
        mockupNo = getIntent().getStringExtra("mockupNo");
        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        setTitle(subName);

        back = findViewById(R.id.back_btn);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.answer_list);
        adapter = new SeeAnswerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        MockDB realmModel = realm.where(MockDB.class)
                .equalTo("subjectName", subName)
                .equalTo("mockupName", mockupNo)
                .findFirst();


        adapter.setData(realmModel.getQuestionMarkDBS());

        showAds();
        showInterstitialAds();

        back.setOnClickListener(v -> {
            finish();
            if (interstitialAd.isLoaded()){
                interstitialAd.show();
            } else {
                finish();
            }
        });

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

    private void showAds() {
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        AdView adView = findViewById(R.id.banner_ad);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("FBFB1CF2E4D9FD9AA66C45BEBAE661B2").build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.menu_fullmock)
            startActivity(new Intent(Answer.this, FullMock.class));
        else if (id == R.id.menu_practice)
            startActivity(new Intent(Answer.this, PracticeActivity.class));
        else if (id == R.id.menu_chapter)
            startActivity(new Intent(Answer.this, Chapter.class));
        else if (id == R.id.menu_flashcard)
            startActivity(new Intent(Answer.this, FlashCardActivity.class));
        else if (id == R.id.menu_previous_result)
            startActivity(new Intent(Answer.this, PreviousResult.class));
        else if (id == R.id.menu_important_link)
            startActivity(new Intent(Answer.this, ImportantLink.class));
        else if (id == R.id.menu_change_subject)
            startActivity(new Intent(Answer.this, LocSub.class));
        else if (id == R.id.menu_go_pro)
            Toast.makeText(this, "Go Pro", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onBackPressed() {

        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        } else {
            super.onBackPressed();
        }

    }
}

