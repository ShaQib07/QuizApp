package com.shakib.bdlabit.pmpquizprep;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.shakib.bdlabit.pmpquizprep.Adapter.SeeAnswerAdapter;
import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.MockDB;
import com.shakib.bdlabit.pmpquizprep.database.QuestionMarkDB;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;

public class Answer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle drawerToggle;
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
        realm = Realm.getDefaultInstance();
        mockupNo = getIntent().getStringExtra("mockupNo");
        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");

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

        back.setOnClickListener(v -> finish());

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
}

