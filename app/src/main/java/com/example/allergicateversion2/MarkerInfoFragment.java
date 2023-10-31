package com.example.allergicateversion2;

import static com.example.allergicateversion2.R.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerInfoFragment extends Fragment {
    String title;
    String snippet;
    double latitude;
    double longitude;
    LatLng position;

    TextView tvRestaurantTitle;
    public MarkerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(layout.fragment_restaurants_info, container, false);

        // Customize the view to display marker-related information
        // You can access the view's elements and set their content as needed
        Bundle bundle = getArguments();
        if (bundle != null) {
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            title = bundle.getString("title");
            snippet = bundle.getString("snippet");

            position = new LatLng(latitude, longitude);



        }
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvRestaurantTitle = view.findViewById(id.tvRestaurantTitle);
        tvRestaurantTitle.append("" + title);
    }
}

