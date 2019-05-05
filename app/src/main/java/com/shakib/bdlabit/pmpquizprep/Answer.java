package com.shakib.bdlabit.pmpquizprep;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shakib.bdlabit.pmpquizprep.Adapter.SeeAnswerAdapter;
import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.MockDB;
import com.shakib.bdlabit.pmpquizprep.database.QuestionMarkDB;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;

public class Answer extends AppCompatActivity {

    String mockupNo;
    String subName;
    Realm realm;

    SeeAnswerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        realm = Realm.getDefaultInstance();
        mockupNo = getIntent().getStringExtra("mockupNo");
        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");

        recyclerView = findViewById(R.id.answer_list);
        adapter = new SeeAnswerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

       MockDB realmModel = realm.where(MockDB.class)
                .equalTo("subjectName", subName)
                .equalTo("mockupName", mockupNo)
                .findFirst();


        adapter.setData(realmModel.getQuestionMarkDBS());

    }
}
