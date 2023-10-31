package com.example.allergicateversion2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetNearbyPlace extends AsyncTask<Object,String,String> {

    GoogleMap gMap;
    String url;
    InputStream is;
    BufferedReader bufferedReader;
    StringBuffer stringBuffer;
    StringBuilder stringBuilder;
    String data;

    SearchResultsMap searchResultsMap;

    UserInfo userInfo;

    public GetNearbyPlace(SearchResultsMap searchResultsMap) {
        this.searchResultsMap = searchResultsMap;
    }
    public GetNearbyPlace() {
    }

    @Override
    protected String doInBackground(Object... params) {
        gMap = (GoogleMap) params[0];
        url = (String) params[1];
        userInfo = (UserInfo) params[2];

        try {
            URL myurl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)myurl.openConnection();
            httpURLConnection.connect();
            is = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));

            String line;
            stringBuilder = new StringBuilder();

            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onPostExecute(String a) {
        try {
            JSONObject parentObject = new JSONObject(a);
            JSONArray resultsArray = parentObject.getJSONArray("results");

            for(int i = 0; i<resultsArray.length();i++){
                JSONObject jsonObject = resultsArray.getJSONObject(i);
                JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");
                String latitude = locationObj.getString("lat");
                String longitude = locationObj.getString("lng");
                JSONObject nameObj = resultsArray.getJSONObject(i);
                String name_restaurant = nameObj.getString("name");
                String vicinityObj = nameObj.getString("vicinity");
                String rating = nameObj.getString("rating");
                String user_ratings_total = nameObj.getString("user_ratings_total");
                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(name_restaurant);
                markerOptions.snippet(vicinityObj + "\nRating: " + rating + " Stars \n" + user_ratings_total + " Google reviews");
                //markerOptions.snippet(vicinityObj);
                markerOptions.position(latLng);
                Marker marker = gMap.addMarker(markerOptions);
                Bundle bundle = new Bundle();
                bundle.putString("jsonobject", jsonObject.toString());
                bundle.putParcelable("keyuser", userInfo);

                //marker.setTag(jsonObject.toString());
                marker.setTag(bundle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
