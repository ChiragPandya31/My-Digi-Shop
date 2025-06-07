package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_commerce.R;
import com.example.e_commerce.adapters.CategoryAdapter;
import com.example.e_commerce.adapters.ProductAdapter;
import com.example.e_commerce.databinding.ActivityMainBinding;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;
    MaterialSearchBar searchBar;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("LoginFile",MODE_PRIVATE);




        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:
                        return true;
                    case R.id.bottom_category:
                        startActivity(new Intent(getApplicationContext(), com.example.e_commerce.activity.Category.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_cart:
                        if (sharedPreferences.getString("isLogin", "").equals("true")) {
                            // User is logged in, open the cart activity
                            startActivity(new Intent(getApplicationContext(), Cart.class));
                        } else {
                            // User is not logged in, show a message and redirect to login activity
                            Toast.makeText(getApplicationContext(), "Please login to access the Cart", Toast.LENGTH_SHORT).show();

                        }
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bottom_account:
                        if (sharedPreferences.getString("isLogin", "").equals("true")) {
                            // User is logged in, open the cart activity
                            startActivity(new Intent(getApplicationContext(), account.class));
                        } else {
                            // User is not logged in, show a message and redirect to login activity
                            Toast.makeText(getApplicationContext(), "Please login to access the Account", Toast.LENGTH_SHORT).show();
                            finish();

                        }                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {


            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        initCategories();
        initProducts();
        initslider();
    }

    private void initslider() {
      getRecentoffers();
    }
    void initCategories(){
        categories = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(this, categories);

        getCategories();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);

    }
    void getCategories(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, (Response.Listener<String>) response -> {
            try {
                JSONObject mainobj = new JSONObject(response);
                if(mainobj.getString("status").equals("success")){
                    JSONArray categoriesArray = mainobj.getJSONArray("categories");
                    for (int i =0; i< categoriesArray.length(); i++){
                        JSONObject object = categoriesArray.getJSONObject(i);
                        Category category = new Category(
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
            }catch (JSONException e) {
                e.printStackTrace();
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
    void getRecentProducts(){
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Constants.GET_PRODUCTS_URL + "?count=8";
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("success")){
                        JSONArray productsArray = object.getJSONArray("products");
                        for (int i =0; i< productsArray.length(); i++){
                            JSONObject childobject = productsArray.getJSONObject(i);
                            Product product = new Product(
                                    childobject.getString("name"),
                                  Constants.PRODUCTS_IMAGE_URL +  childobject.getString("image"),
                                    childobject.getString("status"),
                                    childobject.getDouble("price"),
                                    childobject.getDouble("price_discount"),
                                    childobject.getInt("stock"),
                                    childobject.getInt("id")
                            );
                            products.add(product);
                        }
                        productAdapter.notifyDataSetChanged();
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
    void initProducts(){
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this,products);

        getRecentProducts();

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }
    void getRecentoffers(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")){
                    JSONArray offerArray = object.getJSONArray("news_infos");
                    for(int i=0; i<offerArray.length(); i++) {
                        JSONObject childobj = offerArray.getJSONObject(i);
                        binding.carousel.addData(
                                new CarouselItem(
                                        Constants.NEWS_IMAGE_URL + childobj.getString("image"),
                                        childobj.getString("title")
                                )
                        );
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error ->{});
        queue.add(request);
    }

}