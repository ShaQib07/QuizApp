package com.shakib.bdlabit.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.shakib.bdlabit.quiz.Adapter.PracticeAdapter;
import com.shakib.bdlabit.quiz.Model.PracticeListItem;
import com.shakib.bdlabit.quiz.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.quiz.database.DBRepo;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;

public class PracticeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private  FirebaseAnalytics mFirebaseAnalytics;

    private ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    PracticeAdapter practiceAdapter;

    ArrayList<PracticeListItem> arrayList;
    DBRepo dbRepo;
    Realm realm;
    String subName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_mock);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);
        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        setTitle(subName);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.mock_list);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);

        arrayList = new ArrayList<>();

        populateGrid();

        showAds();

    }

    private void showAds() {
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        AdView adView = findViewById(R.id.banner_ad);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("FBFB1CF2E4D9FD9AA66C45BEBAE661B2").build();
        adView.loadAd(adRequest);
    }

    boolean isCompleted = false;

    private void populateGrid() {

        RealmList<String> praclist = dbRepo.getAllPreviousPracName(subName);

        for (int i = 1; i <= 100; i++){
            final String pracNo = "Practice "+i;
            isCompleted = praclist.contains(pracNo);

            PracticeListItem practiceListItem = new PracticeListItem();
            practiceListItem.setCompleted(isCompleted);
            practiceListItem.setPracticeName(pracNo);

            arrayList.add(practiceListItem);
        }



        practiceAdapter = new PracticeAdapter(this, arrayList);

        recyclerView.setAdapter(practiceAdapter);
        practiceAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.menu_fullmock)
            startActivity(new Intent(PracticeActivity.this, FullMock.class));
        else if (id == R.id.menu_practice)
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (id == R.id.menu_chapter)
            startActivity(new Intent(PracticeActivity.this, Chapter.class));
        else if (id == R.id.menu_flashcard)
            startActivity(new Intent(PracticeActivity.this, FlashCardActivity.class));
        else if (id == R.id.menu_previous_result)
            startActivity(new Intent(PracticeActivity.this, PreviousResult.class));
        else if (id == R.id.menu_important_link)
            startActivity(new Intent(PracticeActivity.this, ImportantLink.class));
        else if (id == R.id.menu_change_subject){
            startActivity(new Intent(PracticeActivity.this, LocSub.class));}
        else if (id == R.id.menu_go_pro)
            Toast.makeText(this, "Go Pro", Toast.LENGTH_SHORT).show();

        return true;
    }
}
