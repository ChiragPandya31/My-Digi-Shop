package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityAccountBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class account extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView textView,textView1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("LoginFile",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        textView1 = findViewById(R.id.logout);
        textView = findViewById(R.id.update);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString( "isLogin","false");
                editor.commit();
                startActivity(new Intent(account.this,welcomeActivity.class));
                finishAffinity();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(account.this, Upadateuser.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.bottom_account);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:
                        startActivity(new Intent(getApplicationContext(), com.example.e_commerce.activity.MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_category:
                        startActivity(new Intent(getApplicationContext(), Category.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_cart:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_account:
                        return true;
                }
                return false;
            }
        });
    }
}