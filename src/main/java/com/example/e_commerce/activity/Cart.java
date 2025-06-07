package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.CartAdapter;
import com.example.e_commerce.databinding.ActivityCart2Binding;
import com.example.e_commerce.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class Cart extends AppCompatActivity {
    ActivityCart2Binding binding;
    CartAdapter adapter;
    ArrayList<Product> products;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.bottom_cart);
        sharedPreferences = getSharedPreferences("LoginFile",MODE_PRIVATE);

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
                        return true;

                    case R.id.bottom_account:
                        startActivity(new Intent(getApplicationContext(), account.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });
        binding = ActivityCart2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        products = new ArrayList<>();

        com.hishd.tinycart.model.Cart cart = TinyCartHelper.getCart();

        for (Map.Entry<Item,Integer>item : cart.getAllItemsWithQty().entrySet()){
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);

            products.add(product);
        }


        adapter = new CartAdapter(this, products, new CartAdapter.CartListnear() {
            @Override
            public void onQuantityChanged() {
                binding.subTotal.setText(String.format("₹ %.2f",cart.getTotalPrice()));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.cartlist.setLayoutManager(layoutManager);
        binding.cartlist.addItemDecoration(itemDecoration);
        binding.cartlist.setAdapter(adapter);

        binding.subTotal.setText(String.format("₹ %.2f",cart.getTotalPrice()));

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cart.this, CheckoutActivity.class));

            }
        });
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}