package com.example.realestateproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.realestateproject.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        boolean autoLogin = sharedPreferences.getBoolean("autoLogin",true);
//        if(autoLogin){
//            startActivity(new Intent(this, HomeActivity.class));
//            finish();
//        }
//        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, SignInActivity.class));
                }
            }, 1500);
//        }
    }
}
