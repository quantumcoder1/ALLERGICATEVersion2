package com.example.allergicateversion2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
//import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchResultsMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    LatLng latlngCurrent;
    LocationRequest request;
    GoogleApiClient client;
    FrameLayout map;
    Geocoder geocoder;
    Button btnZoomIn;
    Button btnZoomOut;
    Button backArrow2;
    ImageView backArrow3;
    private PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private List<Field> placeFields;
    private long lastClickTime = 0;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_map);
        map = findViewById(R.id.map);
        backArrow2 = findViewById(R.id.btnBack);
//        backArrow3 = findViewById(R.id.backArrow3);
        backArrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });
//        geocoder = new Geocoder(this, Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
//        Places.initialize(getApplicationContext(), "AIzaSyAXrKBr0Z5qfRC-F-eZH_Rxbbpb4IHuwo4");
//        placesClient = Places.createClient(this);
    }

    public void findRestaurants(View v) {
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        Geocoder geocoder = new Geocoder(this);
        String location = getIntent().getStringExtra("locationKey");
        LatLng latLng = null;
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                gMap.addMarker(new MarkerOptions().position(latLng).title(location.toUpperCase()));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error in finding the location", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        gMap.getUiSettings().setZoomControlsEnabled(true);
        stringBuilder.append("location=" + latLng.latitude + ',' + latLng.longitude);
        stringBuilder.append("&radius=" + 5000);
        stringBuilder.append("&keyword=" + "restaurant");
        stringBuilder.append("&key=" + "AIzaSyAXrKBr0Z5qfRC-F-eZH_Rxbbpb4IHuwo4");

        String url = stringBuilder.toString();

        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = gMap;
        dataTransfer[1] = url;

        GetNearbyPlace getNearbyPlace = new GetNearbyPlace(this);
        getNearbyPlace.execute(dataTransfer);

    }


//    private void checkLocationPermission() {
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            // You already have the permission, proceed with your location-related tasks
//            initializeLocationServices();
//        } else {
//            // You don't have the permission yet, request it from the user
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    LOCATION_PERMISSION_REQUEST_CODE);
//        }
//    }

//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // The user granted the location permission, proceed with your location-related tasks
//                onMapReady(gMap);
//            } else {
//                // The user denied the location permission, handle it gracefully
//                // For example, show an explanation or disable location-related functionality
//                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void initializeLocationServices() {
//        // Initialize FusedLocationProviderClient
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        // Do whatever location-related tasks you need
//        // For example, you can get the user's last known location
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationProviderClient.getLastLocation()
//                    .addOnSuccessListener(location -> {
//                        if (location != null) {
//                            // Use the last known location to update the map or other UI elements
//                            double latitude = location.getLatitude();
//                            double longitude = location.getLongitude();
//                            // ... Update UI with user's location
//                            LatLng latLng = new LatLng(latitude, longitude);
////                            searchForNearbyRestaurants(latLng);
//
//                            searchNearbyRestaurants(latLng);
////                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                        }
//                    })
//                    .addOnFailureListener(e -> {
//                        // Handle any errors that occurred while getting the last location
//                        Toast.makeText(this, "Error getting last location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    });
//        }
//    }

    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;
//        client = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this).addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this).build();
//        client.connect();
        gMap.getUiSettings().setZoomControlsEnabled(true);

        // Set a custom info window adapter to display your custom layout
        gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null; // Return null here if you want to use a custom layout for the entire info window
            }

            @Override
            public View getInfoContents(Marker marker) {
                @SuppressLint("ResourceType") View infoView = getLayoutInflater().inflate(R.drawable.custom_info_window, null);

                TextView titleTextView = infoView.findViewById(R.id.titleTextView);
                TextView descriptionTextView = infoView.findViewById(R.id.descriptionTextView);

                titleTextView.setText(marker.getTitle());
                descriptionTextView.setText(marker.getSnippet());

                return infoView;
            }
        });

        // Handle marker click events
        this.gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                long currentClickTime = System.currentTimeMillis();

                // Check if the time elapsed since the last click is within the double-click threshold
                if (currentClickTime - lastClickTime < 500) { // Adjust the threshold as needed (e.g., 500 milliseconds for a double-click)
                    // Double-click detected, start a new activity
                    Intent intent = new Intent(SearchResultsMap.this, RestaurantInfo.class);
                    startActivity(intent);
                    lastClickTime = 0; // Reset the last click time
                    return true; // Return true to consume the event
                } else {
                    // Single-click detected, store the current click time
                    lastClickTime = currentClickTime;
                    return false; // Return false to allow default marker behavior
                }
            }
        });

    }


//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//        if(location == null){
//            Toast.makeText(getApplicationContext(), "Location not found.", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            latlngCurrent = new LatLng(location.getLatitude(),location.getLongitude());
//
//            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlngCurrent,15);
//            gMap.animateCamera(update);
//
//            MarkerOptions options = new MarkerOptions();
//            options.position(latlngCurrent);
//            options.title("Current Location");
//            gMap.addMarker(options);
//        }
//
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        request = new LocationRequest().create();
//        request.setInterval(1000);
//        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
//
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    public void searchNearbyRestaurants(LatLng latLng) {
//        PlacesClient placesClient = Places.createClient(this);
//
//        // Define the fields you want to retrieve for each place
//        List<Place.Field> placeFields = Arrays.asList(
//                Place.Field.NAME,
//                Place.Field.RATING,
//                Place.Field.ADDRESS
//        );
//
//        // Check for location permissions and request them if not granted
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//            return;
//        }
//
//        // Create a FindCurrentPlaceRequest with the specified place fields
//        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();
//
//        // Use the Places API to find nearby places
//        Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
//        placeResponse.addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                FindCurrentPlaceResponse response = task.getResult();
//                for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
//                    Place restaurant = placeLikelihood.getPlace();
//                    // Process and display restaurant information
//                    String name = restaurant.getName();
//                    Double rating = restaurant.getRating();
//                    String address = restaurant.getAddress();
//                    // Display the information in your app's UI or log it
//                }
//            } else {
//                // Handle the error here, e.g., by showing an error message to the user
//                Exception exception = task.getException();
//                if (exception != null) {
//                    Log.e(TAG, "Place request failed: " + exception.getMessage());
//                }
//            }
//        });
//    }
//
//    //    @SuppressLint("MissingPermission")
//    public void searchForNearbyRestaurants(LatLng latLng) {
//        // Set the type of place you want to search for (restaurant in this case)
//        Place.Type placeType = Place.Type.RESTAURANT;
//
//        // Set the radius of the search (in meters)
//        int radius = 1000; // 1000 meters (1 km)
//
//        // Create the FindAutocompletePredictionsRequest for nearby restaurants
//        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
////                .setLocationRestriction(RectangularBounds.newInstance(
////                        new LatLng(latLng.latitude - 0.01, latLng.longitude - 0.01),
////                        new LatLng(latLng.latitude + 0.01, latLng.longitude + 0.01)
////                ))
//                .setQuery(String.valueOf(placeType))
//                .build();
//
//        ArrayList<Place> placeArrayList = new ArrayList<>();
//        // Use a list to track the pending fetch requests
//        List<Task<FetchPlaceResponse>> fetchTasks = new ArrayList<>();
//
//        // Perform the place search
//        placesClient.findAutocompletePredictions(request)
//                .addOnSuccessListener(response -> {
//                    List<AutocompletePrediction> predictions = response.getAutocompletePredictions();
//
////                    for (AutocompletePrediction prediction : predictions) {
////                        // Get information from the prediction
////                        String placeId = prediction.getPlaceId();
////
////                        gMap.addMarker(new MarkerOptions().position(latLng).title(location.toUpperCase()));
////                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
////                    }
//                    // Handle the list of autocomplete predictions returned in the response
////                    List<AutocompletePrediction> predictions = response.getAutocompletePredictions();
////
////                    // Display the restaurants as markers on the map
//                    for (AutocompletePrediction prediction : predictions) {
//                        // Get the place ID and fetch additional details if needed
//                        String placeId = prediction.getPlaceId();
//
//                        // Define the fields you want to retrieve for the place
//                        List<Field> placeFields = Arrays.asList(
//                                Field.ID, Field.NAME, Field.ADDRESS, Field.LAT_LNG, Field.RATING, Field.PHONE_NUMBER
//                        );
//
//                        FetchPlaceRequest placeRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
//
//                        // Add each fetch request task to the list
////                        placesClient.fetchPlace(placeRequest).addOnSuccessListener(responses -> {
////                            Place place = responses.getPlace();
////                            gMap.addMarker(new MarkerOptions().position(latLng).title(place.getName().toUpperCase()));
////                        }).addOnFailureListener(exception -> {
////                            // Handle any errors that occurred during the nearby search
////                            Toast.makeText(this, "Error fetching nearby restaurants", Toast.LENGTH_SHORT).show();
////                            Log.e("TAG", "Error fetching nearby places: " + exception.getMessage());
////                        });
//
//                        // Add each fetch request task to the list
//                        Task<FetchPlaceResponse> fetchTask = placesClient.fetchPlace(placeRequest);
//
//                        // Add the fetchTask to the list of fetchTasks
//                        fetchTasks.add(fetchTask);
//                    }
//
//                    // Use Tasks.whenAllComplete to handle the completion of all fetch tasks
//                    Tasks.whenAllComplete(fetchTasks)
//                            .addOnCompleteListener(task -> {
//                                for (Task<?> completedTask : task.getResult()) {
//                                    if (completedTask.isSuccessful()) {
//                                        // Handle the fetched place details
//                                        FetchPlaceResponse placeResponse = (FetchPlaceResponse) completedTask.getResult();
//                                        Place place = placeResponse.getPlace();
//                                        gMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toUpperCase()));
////                                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 20));
//                                    } else {
//                                        // Handle any errors that occurred during the fetch request
//                                        Exception exception = completedTask.getException();
//                                        Log.e("TAG", "Error fetching place details: " + exception.getMessage());
//                                    }
//                                }
//
//                                // Do whatever you want with the completed list of places
//                                // For example, you can pass it to another method for further processing
//                                // Or update your UI with the fetched places
//
//                                // Call a method or perform any action here with the fully populated placeArrayList
//                                // Example: updateUIWithPlaces(placeArrayList);
//                            });
//                })
//                .addOnFailureListener(exception -> {
//                    // Handle any errors that occurred during the nearby search
//                    Toast.makeText(this, "Error fetching nearby restaurants", Toast.LENGTH_SHORT).show();
//                    Log.e("TAG", "Error fetching nearby places: " + exception.getMessage());
//                });
//
//        return;
//    }



}
