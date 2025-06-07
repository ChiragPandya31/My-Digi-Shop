package com.example.e_commerce.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityNewaccountBinding;

import java.util.HashMap;
import java.util.Map;

public class newaccount extends AppCompatActivity {
    ActivityNewaccountBinding binding;
    String url = "https://myadipanel001.000webhostapp.com/login/insertdata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewaccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(newaccount.this, loginActivity.class));
                finish();
            }
        });


        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (binding.newName.getText().toString().isEmpty()) {
                        binding.newName.setError("Please Enter Username");
                    } else if (binding.newEmail.getText().toString().isEmpty()) {
                        binding.newEmail.setError("Please enter Email");
                    } else if (binding.newPassword.getText().toString().isEmpty()) {
                        binding.newPassword.setError("please Enter password");
                    }else if (binding.newNumber.getText().toString().isEmpty()&&binding.newNumber.getText().toString().length() != 10) {
                        binding.newNumber.setError("please Enter Mobile Number");
                    }
                    else {
                        insertData(binding);
                    }

            }
        });
    }
    private void insertData(ActivityNewaccountBinding binding) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(newaccount.this, loginActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(newaccount.this, "Enter Valid Email-Address", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                String username = binding.newName.getText().toString();
                String email = binding.newEmail.getText().toString();
                String mnumber = binding.newNumber.getText().toString();
                String password = binding.newPassword.getText().toString();

                // Check for email validity
                if (email.isEmpty() || !email.contains("@")) {
                    binding.newEmail.setError("Please enter a valid email address");
                    return null;
                }

                // Check for unique email
                if (!isEmailUnique(email)) {
                    binding.newEmail.setError("This email address is already in use");
                    return null;
                }

                map.put("username", username);
                map.put("email", email);
                map.put("mnumber", mnumber);
                map.put("password", password);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private boolean isEmailUnique(String email) {

        return !email.equals("example@example.com");
    }
}
