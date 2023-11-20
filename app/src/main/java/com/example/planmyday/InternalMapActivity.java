package com.example.planmyday;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class InternalMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner daySpinner;
    private List<List<Attraction>> dailyPlans;
    private DirectionsParser directionsParser, publicTransportDirectionsParser;
    private final int[] RED = new int[]{Color.RED}; // color for route
    private final int[] BLUE = new int[]{Color.BLUE}; // color for public transport route

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_map);

        // Initialize the spinner and map fragment
        daySpinner = findViewById(R.id.day_spinner);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        ArrayList<Bundle> dailyPlansBundles = intent.getParcelableArrayListExtra("dailyPlansBundles");
        dailyPlans = new ArrayList<>();

        if (dailyPlansBundles != null) {
            for (Bundle bundle : dailyPlansBundles) {
                ArrayList<Attraction> dayPlan = bundle.getParcelableArrayList("dayPlan");
                if (dayPlan != null) {
                    dailyPlans.add(dayPlan);
                }
            }
        }

        // Set up the spinner with a hardcoded number of days
        setUpSpinner();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // Set the default map type

        directionsParser = new DirectionsParser(mMap, RED);
        publicTransportDirectionsParser = new DirectionsParser(mMap, BLUE);

        // Check if the daily plans are not empty and the first day has attractions
        if (!dailyPlans.isEmpty() && !dailyPlans.get(0).isEmpty()) {
            // Move the camera to the first point of the first day plan
            Attraction firstAttraction = dailyPlans.get(0).get(0);
            LatLng initialPosition = new LatLng(firstAttraction.getLongitude(), firstAttraction.getLatitude());
            int zoomFactor = firstAttraction.isAtUSC() ? 15 : 11;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, zoomFactor));
            }

        // If there's a selected day in the spinner, draw the paths for that day
        if (daySpinner.getSelectedItemPosition() >= 0) {
            drawItineraryPaths(daySpinner.getSelectedItemPosition());
        }
    }

    private void setUpSpinner() {
        List<String> days = new ArrayList<>();
        for (int i = 0; i < dailyPlans.size(); i++) {
            days.add("Day " + (i + 1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mMap != null) {
                    drawItineraryPaths(position); // Draw the paths for the selected day
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing to do here
            }
        });
    }

    private void drawItineraryPaths(int dayIndex) {

        if (dailyPlans == null || dailyPlans.isEmpty() || dailyPlans.get(dayIndex).isEmpty()) return;
        List<Attraction> dayPlan = dailyPlans.get(dayIndex);
        mMap.clear(); // Clear the previous routes


        // If the day plan has attractions, move the camera to the first point of the selected day plan
        if (!dayPlan.isEmpty()) {
            Attraction firstAttraction = dayPlan.get(0);
            LatLng initialPosition = new LatLng(firstAttraction.getLongitude(), firstAttraction.getLatitude());
            int zoomFactor = firstAttraction.isAtUSC() ? 15 : 11;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, zoomFactor));
        }

        for (int i = 0; i < dayPlan.size() - 1; i++) {
            Attraction origin = dayPlan.get(i);
            Attraction destination = dayPlan.get(i + 1);

            // Put a marker for the origin
            LatLng originLatLng = new LatLng(origin.getLongitude(), origin.getLatitude());
            mMap.addMarker(new MarkerOptions().position(originLatLng).title(origin.getName()));


            // For driving routes
            String drivingUrl = getDirectionsUrl(origin, destination, "driving");
            new FetchURL(directionsParser, dayIndex).execute(drivingUrl);

            // For public transport routes
            String transitUrl = getDirectionsUrl(origin, destination, "transit");
            new FetchURL(publicTransportDirectionsParser, dayIndex).execute(transitUrl);
        }

        // Add a marker for the last attraction if it hasn't been added yet
        Attraction lastAttraction = dayPlan.get(dayPlan.size() - 1);
        LatLng lastPosition = new LatLng(lastAttraction.getLongitude(), lastAttraction.getLatitude());
        mMap.addMarker(new MarkerOptions().position(lastPosition).title(lastAttraction.getName()));
    }

    private String getDirectionsUrl(Attraction origin, Attraction destination, String mode) {
        String str_origin = "origin=" + origin.getLongitude() + "," + origin.getLatitude();
        String str_dest = "destination=" + destination.getLongitude() + "," + destination.getLatitude();
        String key = "key=AIzaSyADh3bvt5TjbXh74eK-lkKBH7gaQhbHh5c"; //TODO: replace this
        // String parameters = str_origin + "&" + str_dest + "&" + key;
        String parameters = str_origin + "&" + str_dest + "&mode=" + mode + "&" + key;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    // Update the FetchURL class to use DirectionsParser
    private class FetchURL extends AsyncTask<String, Void, String> {
        private DirectionsParser directionsParser;
        private int dayIndex;

        public FetchURL(DirectionsParser directionsParser, int dayIndex) {
            this.directionsParser = directionsParser;
            this.dayIndex = dayIndex;
        }

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = new HttpHandler().makeServiceCall(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            directionsParser.parseDirections(result, dayIndex); // Use the DirectionsParser here
        }
    }
}
