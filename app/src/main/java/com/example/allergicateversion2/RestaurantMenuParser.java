package com.example.allergicateversion2;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RestaurantMenuParser {

    String jsonFile;
    JSONObject jsonObject;

    public RestaurantMenuParser(Context context, String jsonFile){
        this.jsonFile = jsonFile;

        try {
            // Open the JSON file in the assets folder
            InputStream inputStream = context.getAssets().open(jsonFile);

            // Read the JSON data from the input stream
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");


            jsonObject = new JSONObject(json);
            // Now you have the JSON data in the "json" variable
            // You can parse it using a JSON library, e.g., org.json
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONArray getRestaurants() throws JSONException {
        return this.jsonObject.getJSONArray("restaurants");
    }

    public JSONObject getRestaurantMenu(int restaurantIndex) throws JSONException {

        JSONArray restaurants = this.jsonObject.getJSONArray("restaurants");
        JSONObject restaurant = restaurants.getJSONObject(restaurantIndex);
        JSONObject menu = restaurant.getJSONObject("menu");
        return menu;
    }

    public int getMenuItemCount(JSONObject menuItems) throws JSONException {
        int menuItemsCount = 0;
        for (String menuType : new String[]{"appetizers", "lunch", "dinner"}) {
            if (menuItems != null) {
                JSONArray item = menuItems.getJSONArray(menuType);
                menuItemsCount += item.length();
            }
        }
        return menuItemsCount;
    }

    public JSONArray queryMenuItemsWithoutAllergens(ArrayList<String> allergensToExclude, JSONObject menuItems) throws JSONException {
        JSONArray filteredMenu = new JSONArray();

        for (String menuType : new String[]{"appetizers", "lunch", "dinner"}) {
            if (menuItems.has(menuType)) {
                JSONArray menuItemsArray = menuItems.getJSONArray(menuType);

                for (int i = 0; i < menuItemsArray.length(); i++) {
                    JSONObject item = menuItemsArray.getJSONObject(i);
                    JSONArray allergens = item.optJSONArray("allergens");

                    boolean hasAllergen = false;
                    if (allergens != null) {
                        for (String allergen : allergensToExclude) {
                            for (int k = 0; k < allergens.length(); k++) {
                                if (allergens.getString(k).equals(allergen)) {
                                    hasAllergen = true;
                                    break;
                                }
                            }
                            if (hasAllergen) {
                                break;
                            }
                        }
                    }

                    if (!hasAllergen) {
                        JSONObject filteredItem = new JSONObject();
                        filteredItem.put("item_name", item.getString("name"));
                        filteredMenu.put(filteredItem);
                    }
                }
            }
        }

        return filteredMenu;
    }

//    public JSONArray queryMenuItemsWithoutAllergens(ArrayList<String> allergensToExclude, JSONObject menuItems) throws JSONException {
//        JSONArray filteredMenu = new JSONArray();
//        JSONArray restaurants = this.jsonObject.getJSONArray("restaurants");
//
//        for (String menuType : new String[]{"appetizers", "lunch", "dinner"}) {
//            if (menuItems != null) {
//                JSONObject item = menuItems.getJSONObject(menuType);
//                JSONArray allergens = item.optJSONArray("allergens");
//
//                boolean hasAllergen = false;
//                if (allergens != null) {
//                    for (String allergen : allergensToExclude) {
//                        for (int k = 0; k < allergens.length(); k++) {
//                            if (allergens.getString(k).equals(allergen)) {
//                                hasAllergen = true;
//                                break;
//                            }
//                        }
//                        if (hasAllergen) {
//                            break;
//                        }
//                    }
//                }
//
//                if (!hasAllergen) {
//                    JSONObject filteredItem = new JSONObject();
//                    filteredItem.put("menu_type", menuType);
//                    filteredItem.put("item_name", item.getString("name"));
//                    filteredItem.put("item_price", item.getDouble("price"));
//                    filteredItem.put("item_description", item.getString("description"));
//                    filteredItem.put("allergens", allergens != null ? allergens : new JSONArray());
//                    filteredMenu.put(filteredItem);
//                }
//            }
//        }
//
//        return filteredMenu;
//    }

    public JSONArray queryMenuWithoutAllergens(String[] allergensToExclude) throws JSONException {
        JSONArray filteredMenu = new JSONArray();
        JSONArray restaurants = this.jsonObject.getJSONArray("restaurants");

        for (int i = 0; i < restaurants.length(); i++) {
            JSONObject restaurant = restaurants.getJSONObject(i);
            String restaurantName = restaurant.getString("name");
            String cuisine = restaurant.getString("cuisine");

            for (String menuType : new String[]{"appetizers", "lunch", "dinner"}) {
                JSONArray menuItems = restaurant.getJSONObject("menu").optJSONArray(menuType);

                if (menuItems != null) {
                    for (int j = 0; j < menuItems.length(); j++) {
                        JSONObject item = menuItems.getJSONObject(j);
                        JSONArray allergens = item.optJSONArray("allergens");

                        boolean hasAllergen = false;
                        if (allergens != null) {
                            for (String allergen : allergensToExclude) {
                                for (int k = 0; k < allergens.length(); k++) {
                                    if (allergens.getString(k).equals(allergen)) {
                                        hasAllergen = true;
                                        break;
                                    }
                                }
                                if (hasAllergen) {
                                    break;
                                }
                            }
                        }

                        if (!hasAllergen) {
                            JSONObject filteredItem = new JSONObject();
                            filteredItem.put("restaurant", restaurantName);
                            filteredItem.put("cuisine", cuisine);
                            filteredItem.put("menu_type", menuType);
                            filteredItem.put("item_name", item.getString("name"));
                            filteredItem.put("item_price", item.getDouble("price"));
                            filteredItem.put("item_description", item.getString("description"));
                            filteredItem.put("allergens", allergens != null ? allergens : new JSONArray());
                            filteredMenu.put(filteredItem);
                        }
                    }
                }
            }
        }

        return filteredMenu;
    }

}
