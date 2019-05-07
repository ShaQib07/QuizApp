package com.shakib.bdlabit.pmpquizprep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.shakib.bdlabit.pmpquizprep.Adapter.ListAdapter;
import com.shakib.bdlabit.pmpquizprep.Adapter.MockAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class Chapter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.list_example);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        populateList();
    }

    private void populateList() {
        for (int i = 1; i <= 100; i++){
            final String itemName = "Item Name  "+i;
            arrayList.add(itemName);
        }



        ListAdapter listAdapter = new ListAdapter(getApplicationContext(), arrayList);

        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
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
            startActivity(new Intent(Chapter.this, FullMock.class));
        else if (id == R.id.menu_practice)
            startActivity(new Intent(Chapter.this, PracticeActivity.class));
        else if (id == R.id.menu_chapter)
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (id == R.id.menu_flashcard)
            startActivity(new Intent(Chapter.this, FlashCardActivity.class));
        else if (id == R.id.menu_previous_result)
            startActivity(new Intent(Chapter.this, PreviousResult.class));
        else if (id == R.id.menu_important_link)
            startActivity(new Intent(Chapter.this, ImportantLink.class));
        else if (id == R.id.menu_change_subject)
            startActivity(new Intent(Chapter.this, LocSub.class));
        else if (id == R.id.menu_go_pro)
            Toast.makeText(this, "Go Pro", Toast.LENGTH_SHORT).show();
        return true;
    }
}
