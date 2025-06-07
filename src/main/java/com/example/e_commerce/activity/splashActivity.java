package com.example.e_commerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivitySplashBinding;

public class splashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

//        sharedPreferences = getSharedPreferences("LoginFile",MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        if (sharedPreferences.getString("isLogin", "").equals("true")){
//            startActivity(new Intent(splashActivity.this,MainActivity.class));
//            finishAffinity();
//        }

        Thread thread =new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(splashActivity.this , welcomeActivity.class);
                    startActivity(intent);
                    finish();


                }
            }
        };thread.start();
    }
}