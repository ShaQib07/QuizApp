package com.shakib.bdlabit.pmpquizprep;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.shakib.bdlabit.pmpquizprep.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.pmpquizprep.database.DBRepo;

import io.realm.Realm;

public class Dashboard extends AppCompatActivity {
     CardView fullMock, practice, chapter, flashCard, previousResult, importantLink, goPro;
     ImageButton share, rate;
     Realm realm;
     DBRepo dbRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        realm = Realm.getDefaultInstance();
        dbRepo = new DBRepo(realm);

        String subName = SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject");
        Toast.makeText(this, ""+subName, Toast.LENGTH_SHORT).show();
        setUpOnClickListener();

    }

    private void setUpOnClickListener() {
        fullMock = findViewById(R.id.fullmock);
        fullMock.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,FullMock.class)));

        practice = findViewById(R.id.practice);
        practice.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,Practice.class)));

        chapter = findViewById(R.id.chapter);
        chapter.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,Chapter.class)));

        flashCard = findViewById(R.id.flashcard);
        flashCard.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,FlashCard.class)));

        previousResult = findViewById(R.id.previous_result);
        previousResult.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,PreviousResult.class)));

        importantLink = findViewById(R.id.important_link);
        importantLink.setOnClickListener(v -> startActivity(new Intent(Dashboard.this,ImportantLink.class)));

        goPro = findViewById(R.id.go_pro);
        goPro.setOnClickListener(v -> {

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

    }


}
