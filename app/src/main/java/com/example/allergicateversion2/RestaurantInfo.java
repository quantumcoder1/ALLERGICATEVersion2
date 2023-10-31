package com.example.allergicateversion2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;


public class RestaurantInfo extends AppCompatActivity {
    private TextView txtRestaurantInfo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String restaurantTitle = getIntent().getStringExtra("restaurantTitle");
        setContentView(R.layout.activity_restaurant_info);
        txtRestaurantInfo = findViewById(R.id.txtRestaurantInfo);
        txtRestaurantInfo.append(restaurantTitle);
    }
}