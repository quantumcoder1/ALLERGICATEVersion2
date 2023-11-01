package com.example.allergicateversion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantInformation extends AppCompatActivity {

    TextView textViewTitle;
    String strJsonObject;
    ImageView ivRestaurantPhoto;

    JSONObject jsonObject;
    UserInfo userInfo;

    TextView textViewAllergyIndexValue;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.restaurantinformationmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuReviews) {
            Intent intent = new Intent(getApplicationContext(), ReviewsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_information);
        textViewTitle = findViewById(R.id.textViewTitle);
        //textViewTitle.append(getIntent().getStringExtra("title"));
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null){
             this.strJsonObject = bundle.getString("jsonObject");
            try {
                this.jsonObject = new JSONObject(this.strJsonObject);

                textViewTitle.append(this.jsonObject.getString("name"));

                ivRestaurantPhoto = findViewById(R.id.imageViewRestaurantPhoto);
                String photos = this.jsonObject.getString("photos");
                JSONArray photosArray = new JSONArray(photos);
                JSONObject photo = photosArray.getJSONObject(0);
                String photoReference = photo.getString("photo_reference");
                PhotoFetcher photoFetcher = new PhotoFetcher(ivRestaurantPhoto);
                photoFetcher.fetchAndSetPhoto(photoReference, 400, 400);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            userInfo = bundle.getParcelable("keyuser");
            RestaurantMenuParser menuParser = new RestaurantMenuParser(this,"restaurantMenu.json");

            try {
                JSONArray restaurants = menuParser.getRestaurants();
                ArrayList<String> allergyList = userInfo.getAllergyList();
                int restaurantIndex = this.jsonObject.getString("name").toString().length() % restaurants.length();
                JSONObject menuItems = menuParser.getRestaurantMenu(restaurantIndex);
                JSONArray filteredMenuItems = menuParser.queryMenuItemsWithoutAllergens(allergyList, menuItems);
                int menuItemCount = menuParser.getMenuItemCount(menuItems);
                int filterdMenuItemCount = filteredMenuItems.length();
                float restaurantAllergyIndex = (float) (filterdMenuItemCount * 100.0/menuItemCount);
                RatingBar ratingBarAlergy = findViewById(R.id.ratingBarAllergy);
                ratingBarAlergy.setRating(restaurantAllergyIndex);

                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                GridLayoutManager layoutManager = new GridLayoutManager(this, 1); // 2 columns
                recyclerView.setLayoutManager(layoutManager);

                List<MenuItem> menuItemsTemp = new ArrayList<>();
//                for (int i = 0; i < 20; i++) {
//                    menuItemsTemp.add(new MenuItem("Item " + i));
//                }
// Add more menu items here

//                MenuItemAdapter adapter = new MenuItemAdapter(this, menuItemsTemp);
//                recyclerView.setAdapter(adapter);

                for (String menuType : new String[]{"appetizers", "lunch", "dinner"}) {
                    if (menuItems.has(menuType)) {
                        JSONArray menuItemsArray = menuItems.getJSONArray(menuType);

                        for (int i = 0; i < menuItemsArray.length(); i++) {
                            JSONObject item = menuItemsArray.getJSONObject(i);
                            String name = item.getString("name");
                            String description = item.getString("description");
                            String price = item.getString("price");
                            String ingredients = item.optJSONArray("ingredients").toString();
                            JSONArray allergensArray = item.optJSONArray("allergens");

                            ArrayList<Integer> allergens = new ArrayList<>();

                            for (int k = 0; k < allergensArray.length(); k++){
                                allergens.add(getIconIds(allergensArray.getString(k)));
                            }

                            menuItemsTemp.add(new MenuItem(name, description, allergens));
//                            TextView textViewName = new TextView(this);
//                            textViewName.setText(name);
//                            textViewName.setTextSize(12);
//                            textViewName.setTextColor(Color.WHITE);
//                            textViewName.setLayoutParams(new LinearLayout.LayoutParams(
//                                    LinearLayout.LayoutParams.MATCH_PARENT,
//                                    LinearLayout.LayoutParams.WRAP_CONTENT));
//                            linearLayout.addView(textView);
//
//                            TextView textView = new TextView(this);
//                            textView.setText(name);
//                            textView.setTextSize(12);
//                            textView.setTextColor(Color.WHITE);
//                            textView.setLayoutParams(new LinearLayout.LayoutParams(
//                                    LinearLayout.LayoutParams.MATCH_PARENT,
//                                    LinearLayout.LayoutParams.WRAP_CONTENT));
//                            linearLayout.addView(textView);

                        }
                    }
                }

                MenuItemAdapter adapter = new MenuItemAdapter(this, menuItemsTemp);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getIconIds(String allergen){

            switch (allergen){
                case "Egg": {
                    return R.drawable.egg;
                }
                case "Fish":{
                    return R.drawable.fish;
                }
                case "Wheat":{
                    return R.drawable.gluten;
                }
                case "Peanut":{
                    return R.drawable.peanut;
                }
                default:{
                    return R.drawable.egg;
                }
            }
    }
}