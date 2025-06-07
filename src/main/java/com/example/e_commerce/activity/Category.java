package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_commerce.R;
import com.example.e_commerce.activity.Cart;
import com.example.e_commerce.adapters.CategoryAdapter;
import com.example.e_commerce.adapters.ProductAdapter;
import com.example.e_commerce.databinding.ActivityCategoryBinding;
import com.example.e_commerce.databinding.ActivityMainBinding;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Category extends AppCompatActivity {
    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<com.example.e_commerce.model.Category> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.bottom_category);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:
                        startActivity(new Intent(getApplicationContext(), com.example.e_commerce.activity.MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_category:
                        return true;
                    case R.id.bottom_cart:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_account:
                        startActivity(new Intent(getApplicationContext(), account.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });
        initCategories();

        }


    void initCategories(){
        categories = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(this, categories);

        getCategories();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);

    }
    void getCategories() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, (Response.Listener<String>) response -> {
            try {
                JSONObject mainobj = new JSONObject(response);
                if (mainobj.getString("status").equals("success")) {
                    JSONArray categoriesArray = mainobj.getJSONArray("categories");
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject object = categoriesArray.getJSONObject(i);
                        com.example.e_commerce.model.Category category = new com.example.e_commerce.model.Category(
                                object.getString("name"),
                                Constants.CATEGORIES_IMAGE_URL + object.getString("icon"),
                                object.getString("color"),
                                object.getString("brief"),
                                object.getInt("id")
                        );
                        categories.add(category);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
    }

