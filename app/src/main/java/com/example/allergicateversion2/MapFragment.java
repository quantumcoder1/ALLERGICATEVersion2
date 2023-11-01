package com.example.allergicateversion2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    UserInfo userInfo;



    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static MapFragment newInstance(){
        return newInstance("", "");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            this.userInfo = getArguments().getParcelable("keyuser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // You can interact with the GoogleMap here
                // For example, add markers or set the camera position
//                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapInFragment);
                String locationKey = getArguments().getString("locationKey");
                findRestaurants(locationKey, googleMap);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                    Bundle incomingBundle = (Bundle) marker.getTag();

                    UserInfo userInfo = incomingBundle.getParcelable("keyuser");
                    String jsonObject = incomingBundle.getString("jsonobject");
                    RestaurantsInfoFragment fragmentRestaurantInfo = new RestaurantsInfoFragment();
                    MarkerInfoFragment markerInfoFragment = new MarkerInfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("latitude", marker.getPosition().latitude);
                    bundle.putDouble("longitude", marker.getPosition().longitude);
                    bundle.putString("title", marker.getTitle());
                    bundle.putString("snippet", marker.getSnippet());

                    bundle.putParcelable("keyuser", userInfo);
                    bundle.putString("jsonObject", jsonObject);
                    // Pass the Bundle to another fragment or activity
                     markerInfoFragment.setArguments(bundle);
                    Intent intent = new Intent(getContext(), RestaurantInformation.class);
//                    Intent intent = new Intent(getActivity().getApplicationContext(), TRestaurantInformation.class);
                    intent.putExtra("title", marker.getTitle());
                    intent.putExtra("jsonObject", jsonObject);
                    intent.putExtra("bundle", bundle);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return false;
                    }
                });

            }
        });
    }

    public void findRestaurants(String location, GoogleMap gMap) {
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        Geocoder geocoder = new Geocoder(this.getContext());
        LatLng latLng = null;
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                gMap.addMarker(new MarkerOptions().position(latLng).title(location.toUpperCase()));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        gMap.getUiSettings().setZoomControlsEnabled(true);
        stringBuilder.append("location=" + latLng.latitude + ',' + latLng.longitude);
        stringBuilder.append("&radius=" + 5000);
        stringBuilder.append("&keyword=" + "restaurant");
        stringBuilder.append("&key=" + "AIzaSyAXrKBr0Z5qfRC-F-eZH_Rxbbpb4IHuwo4");

        String url = stringBuilder.toString();

        Object dataTransfer[] = new Object[3];
        dataTransfer[0] = gMap;
        dataTransfer[1] = url;
        dataTransfer[2] = userInfo;

        GetNearbyPlace getNearbyPlace = new GetNearbyPlace();
        getNearbyPlace.execute(dataTransfer);
    }
}