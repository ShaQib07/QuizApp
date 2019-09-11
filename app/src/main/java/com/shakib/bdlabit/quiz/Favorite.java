package com.shakib.bdlabit.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.shakib.bdlabit.quiz.Adapter.FavouriteAdapter;
import com.shakib.bdlabit.quiz.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.quiz.database.DBRepo;
import com.shakib.bdlabit.quiz.database.Favourite;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;

public class Favorite extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAnalytics mFirebaseAnalytics;

    private ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Realm realm;
    DBRepo dbRepo;
    String subName;
    RealmList<Favourite> favourites;
    RecyclerView fav_list;
    FavouriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_result);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);
        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        setTitle(subName);
        favourites = new RealmList<>();

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        fav_list = findViewById(R.id.previous_list);
        adapter = new FavouriteAdapter(this);
        fav_list.setLayoutManager(new LinearLayoutManager(this));
        fav_list.setAdapter(adapter);

        favourites = dbRepo.getAllFavourite(subName);
        adapter.setData(favourites);

        showAds();

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
            startActivity(new Intent(Favorite.this, FullMock.class));
        else if (id == R.id.menu_practice)
            startActivity(new Intent(Favorite.this, PracticeActivity.class));
        else if (id == R.id.menu_chapter)
            startActivity(new Intent(Favorite.this, Chapter.class));
        else if (id == R.id.menu_flashcard)
            startActivity(new Intent(Favorite.this, FlashCardActivity.class));
        else if (id == R.id.menu_previous_result)
            startActivity(new Intent(Favorite.this, PreviousResult.class));
        else if (id == R.id.menu_important_link)
            startActivity(new Intent(Favorite.this, ImportantLink.class));
        else if (id == R.id.menu_change_subject)
            startActivity(new Intent(Favorite.this, LocSub.class));
        else if (id == R.id.menu_go_pro)
            Toast.makeText(this, "Go Pro", Toast.LENGTH_SHORT).show();
        return true;
    }
}
