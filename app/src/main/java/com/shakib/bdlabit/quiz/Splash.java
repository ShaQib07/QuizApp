package com.shakib.bdlabit.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.shakib.bdlabit.quiz.Utils.NetworkAvailable;
import com.shakib.bdlabit.quiz.Utils.SharePreferenceSingleton;
import com.shakib.bdlabit.quiz.Utils.ShowNetworkError;

import io.fabric.sdk.android.Fabric;

public class Splash extends AppCompatActivity {
    ImageView splash;
    NetworkAvailable networkAvailable = new NetworkAvailable() ;
    ShowNetworkError showNetworkError = new ShowNetworkError();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash);

        splash = findViewById(R.id.splash);
        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.fade_animation);
        splash.startAnimation(myAnim);


        new Handler().postDelayed(() -> {
            if (networkAvailable.isNetworkAvailable(Splash.this)){
                if(SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject").equals("0")){
                    startActivity(new Intent(Splash.this,LocSub.class));
                    finish();
                } else {
                    startActivity(new Intent(Splash.this,Dashboard.class));
                    finish();
                }
            } else {
                showNetworkError.ShowNetworkError(Splash.this);
            }
        },2000);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (networkAvailable.isNetworkAvailable(Splash.this)){
//            if(SharePreferenceSingleton.getInstance(getApplicationContext()).getString("subject").equals("0")){
//                startActivity(new Intent(Splash.this,LocSub.class));
//                finish();
//            } else {
//                startActivity(new Intent(Splash.this,Dashboard.class));
//                finish();
//            }
//        } else {
//            showNetworkError.ShowNetworkError(Splash.this);
//        }
//    }

}
