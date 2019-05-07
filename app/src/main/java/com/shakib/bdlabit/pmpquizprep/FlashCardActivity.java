package com.shakib.bdlabit.pmpquizprep;

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

import com.google.android.material.navigation.NavigationView;
import com.shakib.bdlabit.pmpquizprep.Adapter.FlashCardAdapter;
import com.shakib.bdlabit.pmpquizprep.Adapter.MockAdapter;
import com.shakib.bdlabit.pmpquizprep.Model.FlashCardListItem;
import com.shakib.bdlabit.pmpquizprep.Model.MockListItem;
import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.DBRepo;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;

public class FlashCardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FlashCardAdapter flashCardAdapter;

    ArrayList<FlashCardListItem> arrayList;
    DBRepo dbRepo;
    Realm realm;
    String subName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_mock);

        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);
        subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");

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

    }

    boolean isCompleted = false;

    private void populateGrid() {

        RealmList<String> flashList = dbRepo.getAllPreviousFlashName(subName);

        for (int i = 1; i <= 100; i++){
            final String flashNo = "FlashCard "+i;
            isCompleted = flashList.contains(flashNo);

            FlashCardListItem flashCardListItem = new FlashCardListItem();
            flashCardListItem.setCompleted(isCompleted);
            flashCardListItem.setFlashCardName(flashNo);

            arrayList.add(flashCardListItem);
        }



        flashCardAdapter = new FlashCardAdapter(this, arrayList);

        recyclerView.setAdapter(flashCardAdapter);
        flashCardAdapter.notifyDataSetChanged();
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
            startActivity(new Intent(FlashCardActivity.this, FullMock.class));
        else if (id == R.id.menu_practice)
            startActivity(new Intent(FlashCardActivity.this, PracticeActivity.class));
        else if (id == R.id.menu_chapter)
            startActivity(new Intent(FlashCardActivity.this, Chapter.class));
        else if (id == R.id.menu_flashcard)
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (id == R.id.menu_previous_result)
            startActivity(new Intent(FlashCardActivity.this, PreviousResult.class));
        else if (id == R.id.menu_important_link)
            startActivity(new Intent(FlashCardActivity.this, ImportantLink.class));
        else if (id == R.id.menu_change_subject){
            startActivity(new Intent(FlashCardActivity.this, LocSub.class));}
        else if (id == R.id.menu_go_pro)
            Toast.makeText(this, "Go Pro", Toast.LENGTH_SHORT).show();

        return true;
    }
}
