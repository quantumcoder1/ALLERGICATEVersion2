package com.example.allergicateversion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    TextInputEditText search_bar;
    TextView welcomeTextView;
    Button btnSearch;
    TextInputEditText cusine_bar;
    UserInfo user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.restaurantinformationmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    //    @SuppressLint("WrongViewCast")
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        welcomeTextView = findViewById(R.id.welcomeText);
        btnSearch = findViewById(R.id.btnSearch);
        search_bar = findViewById(R.id.search_bar);
        this.user = getIntent().getParcelableExtra("keyuser");
        if (user != null) {
            welcomeTextView.append(" " + user.getUserName());
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location;
                location = String.valueOf(search_bar.getText());

                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(HomeScreen.this, "Enter location", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(HomeScreen.this, SearchResultsMap.class);
                intent.putExtra("locationKey", location);
                intent.putExtra("keyuser", user);
                startActivity(intent);
                finish();
            }
        });
    }
}
