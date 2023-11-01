package com.example.allergicateversion2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RatingBar;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allergicateversion2.databinding.ActivityReviewsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.restaurantinformationmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewReview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1); // 2 columns
        recyclerView.setLayoutManager(layoutManager);


        RestaurantMenuParser menuParser = new RestaurantMenuParser(this,"restaurantMenu.json");

        try {
            int restaurantIndex = 0;
            JSONArray reviewItemsArray = menuParser.getRestaurantReviews(restaurantIndex);
            List<ReviewItem> reviewItemsTemp = new ArrayList<>();

            for (int i = 0; i < reviewItemsArray.length(); i++) {
                JSONObject item = reviewItemsArray.getJSONObject(i);
                String user = item.getString("user");
                int rating = item.getInt("rating");
                String comment = item.getString("comment");
                reviewItemsTemp.add(new ReviewItem(user, rating, comment));
            }

            ReviewItemAdapter adapter = new ReviewItemAdapter(this, reviewItemsTemp);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
//
//        List<ReviewItem> reviewItemsTemp = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            reviewItemsTemp.add(new ReviewItem("Item " + i, "description", 5));
//        }
//        ReviewItemAdapter adapter = new ReviewItemAdapter(this, reviewItemsTemp);
//        recyclerView.setAdapter(adapter);

    }
}