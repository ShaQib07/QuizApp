package com.shakib.bdlabit.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.shakib.bdlabit.quiz.Adapter.RecyclerAdapter;
import com.shakib.bdlabit.quiz.Common.common;
import com.shakib.bdlabit.quiz.Utils.Constants;
import com.shakib.bdlabit.quiz.Utils.FirebaseEndPoint;
import com.shakib.bdlabit.quiz.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.quiz.database.DBRepo;
import com.shakib.bdlabit.quiz.database.FlashCardQuesDB;
import com.shakib.bdlabit.quiz.database.QuestionDB;
import com.shakib.bdlabit.quiz.database.SubjectDB;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class LocSub extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerAdapter mAdapter;
    AdView adView;

    Realm realm;
    DBRepo dbRepo;
    //SubjectDB subjectDB = new SubjectDB();

    EditText searchSub;
    List<String> subNameList;
    ProgressDialog progressDialog;
    boolean isDataInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_sub);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);
        searchSub = findViewById(R.id.search);

        recyclerView = findViewById(R.id.sub_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        isDataInserted = SharePreferenceSingleton.getInstance(getApplicationContext()).getBoolean(Constants.DATA_INSERTED);

        if (!isDataInserted) {
            getAllDataFromFirebase();
        } else {
            loadSubjectList();
            showAds();
        }

        searchSub.findFocus();
        searchSub.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String key) {
        List<String> filteredList = new ArrayList<>();
        for (String s : subNameList) {
            if (s.toLowerCase().contains(key.toLowerCase())) {
                filteredList.add(s);
            }
        }

        mAdapter.filteredList(filteredList);
    }

    private void loadSubjectList() {
        TextView chooser = findViewById(R.id.chooser);
        chooser.setVisibility(View.VISIBLE);
        searchSub.setVisibility(View.VISIBLE);

        subNameList = dbRepo.getAllSubjectName();
        mAdapter = new RecyclerAdapter(subNameList);
        mAdapter.setOnItemClickListener((position, v) -> {
            SharePreferenceSingleton.getInstance(getApplicationContext()).saveString("subject", mAdapter.getItem(position));
            if (!common.isFirstTime) {
                Dashboard.dashBoard.finish();
            }

            startActivity(new Intent(LocSub.this, Dashboard.class));
            finish();
        });


        recyclerView.setAdapter(mAdapter);


    }

    int i = 0;

    public void getAllDataFromFirebase() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Setting things up, please wait...");
        progressDialog.show();


        realm.executeTransaction(realm -> {
            realm.where(SubjectDB.class).findAll().deleteAllFromRealm();
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();//.child("items");
        ref.keepSynced(true);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                for (DataSnapshot ds : dataSnapshot1.getChildren()) {
                    String name = ds.getKey();
                    SubjectDB subjectDB = new SubjectDB();
                    List<QuestionDB> questionDBList = new ArrayList<>();
                    List<FlashCardQuesDB> flashCardQuesDBList = new ArrayList<>();
                    Log.d("name", name);

                    GenericTypeIndicator<List<QuestionDB>> genericQuestionIndicator = new GenericTypeIndicator<List<QuestionDB>>() {
                    };
                    questionDBList = ds.child(FirebaseEndPoint.QUESTION).getValue(genericQuestionIndicator);

                    if (ds.child(FirebaseEndPoint.FLASHCARD).exists()) {
                        GenericTypeIndicator<List<FlashCardQuesDB>> genericFlashIndicator = new GenericTypeIndicator<List<FlashCardQuesDB>>() {
                        };
                        flashCardQuesDBList = ds.child(FirebaseEndPoint.FLASHCARD).getValue(genericFlashIndicator);
                    }

                    RealmList<QuestionDB> questionDBRealmList = new RealmList<>();
                    RealmList<FlashCardQuesDB> flashCardQuesDBRealmList = new RealmList<>();

                    questionDBRealmList.addAll(questionDBList);
                    flashCardQuesDBRealmList.addAll(flashCardQuesDBList);

                    //     Log.d("data count qsn", questionDBRealmList.size() + "");
                    // Log.d("data count", ds.child("questions").getChildrenCount()+"");
                    //     Log.d("data count", flashCardQuesDBRealmList.size() + "");
                    // Log.d("data count", ds.child("Flashcards").getChildrenCount()+"");


                    subjectDB.setSubName(name);
                    subjectDB.setQuestionDBRealmList(questionDBRealmList);
                    subjectDB.setFlashCardQuesDBRealmList(flashCardQuesDBRealmList);

                    realm.executeTransaction(realm -> {
                        realm.insert(subjectDB);
                        i++;
                    });

                    if (i == dataSnapshot1.getChildrenCount()) {
                        loadSubjectList();
                        SharePreferenceSingleton.getInstance(getApplicationContext()).saveBoolean(Constants.DATA_INSERTED, true);
                        progressDialog.dismiss();
                        i = 0;
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        };
        ref.addListenerForSingleValueEvent(eventListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void showAds() {
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        adView = findViewById(R.id.banner_ad);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("FBFB1CF2E4D9FD9AA66C45BEBAE661B2").build();
        adView.loadAd(adRequest);
    }
}
