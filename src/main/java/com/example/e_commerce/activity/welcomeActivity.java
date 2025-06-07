package com.example.e_commerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityWelcomeBinding;


public class welcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding;
    Button button,button2;
    TextView textView3;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        textView3 = findViewById(R.id.textview3);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        sharedPreferences = getSharedPreferences("LoginFile",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getString("isLogin", "").equals("true")){
            startActivity(new Intent(welcomeActivity.this,MainActivity.class));
            finishAffinity();
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(welcomeActivity.this , loginActivity.class);
                startActivity(intent);

            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(welcomeActivity.this , MainActivity.class);
                startActivity(intent);



            }
        });
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(welcomeActivity.this, newaccount.class);
                startActivity(intent);



            }
        });

    }
}