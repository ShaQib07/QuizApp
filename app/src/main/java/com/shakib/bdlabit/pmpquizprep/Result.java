package com.shakib.bdlabit.pmpquizprep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.navigation.NavigationView;
import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.MockDB;
import com.shakib.bdlabit.pmpquizprep.database.PracticeDB;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.Realm;

public class Result extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView correctAns, wrongAns, status;
    int right = 0, wrong = 0;
    PieChart pieChart;
    CardView seeAnswer;
    String mockupNo;
    public static void startResult(Activity activity, int right, int wrong, String mockUpName){

        Intent intent = new Intent(activity, Result.class);
        intent.putExtra("RIGHT", right);
        intent.putExtra("WRONG", wrong);
        intent.putExtra("mockupNo", mockUpName);
        activity.startActivity(intent);
        activity.finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        right = getIntent().getIntExtra("RIGHT", 0);
        wrong = getIntent().getIntExtra("WRONG", 0);
        mockupNo = getIntent().getStringExtra("mockupNo");


        correctAns = findViewById(R.id.correct_ans);
        wrongAns = findViewById(R.id.wrong_ans);
        status = findViewById(R.id.status);
        seeAnswer = findViewById(R.id.see_answer);
        pieChart = findViewById(R.id.pie_chart);

        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(20f);
        pieChart.setTransparentCircleRadius(40f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(right, "Correct"));
        yValues.add(new PieEntry(wrong, "Wrong"));

        pieChart.animateY(1000, Easing.EasingOption.EaseInExpo);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(getResources().getColor(R.color.right),getResources().getColor(R.color.wrong));

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(R.color.white);

        pieChart.setData(data);

        correctAns.setText("Correct Answer: " + right);
        wrongAns.setText("Wrong Answer: " + wrong);

        if (right < 4) {
            status.setText("FAILED");
        } else {
            status.setText("PASSED");
        }

        if (mockupNo.contains("Practice")){
            seeAnswer.setVisibility(View.GONE);
        }

        seeAnswer.setOnClickListener(v -> {
            startActivity(new Intent(Result.this, Answer.class).putExtra("mockupNo", mockupNo));
            finish();
        });

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
            startActivity(new Intent(Result.this, FullMock.class));
        else if (id == R.id.menu_practice)
            startActivity(new Intent(Result.this, PracticeActivity.class));
        else if (id == R.id.menu_chapter)
            startActivity(new Intent(Result.this, Chapter.class));
        else if (id == R.id.menu_flashcard)
            startActivity(new Intent(Result.this, FlashCardActivity.class));
        else if (id == R.id.menu_previous_result)
            startActivity(new Intent(Result.this, PreviousResult.class));
        else if (id == R.id.menu_important_link)
            startActivity(new Intent(Result.this, ImportantLink.class));
        else if (id == R.id.menu_change_subject)
            startActivity(new Intent(Result.this, LocSub.class));
        else if (id == R.id.menu_go_pro)
            Toast.makeText(this, "Go Pro", Toast.LENGTH_SHORT).show();
        return true;
    }
}


