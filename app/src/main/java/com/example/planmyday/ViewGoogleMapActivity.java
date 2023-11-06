package com.example.planmyday;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ViewGoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<List<Attraction>> dailyPlans;
    private final int[] COLORS = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN};
    private DirectionsParser directionsParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_google_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        directionsParser = new DirectionsParser(mMap, COLORS); // Initialize the DirectionsParser here

        if (!dailyPlans.isEmpty() && !dailyPlans.get(0).isEmpty()) {
            // Move the camera to the first point of the first day plan
            Attraction firstAttraction = dailyPlans.get(0).get(0);
            LatLng initialPosition = new LatLng(firstAttraction.getLongitude(), firstAttraction.getLatitude());
            int zoomFactor = firstAttraction.isAtUSC() ? 15 : 11;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, zoomFactor));
        }
        drawItineraryPaths();
    }

    private void drawItineraryPaths() {
        if (dailyPlans == null) return;

        for (int dayIndex = 0; dayIndex < dailyPlans.size(); dayIndex++) {
            List<Attraction> dayPlan = dailyPlans.get(dayIndex);
            for (int i = 0; i < dayPlan.size() - 1; i++) {
                Attraction origin = dayPlan.get(i);
                Attraction destination = dayPlan.get(i + 1);
                String url = getDirectionsUrl(origin, destination);
                new FetchURL(directionsParser, dayIndex).execute(url); // Use the DirectionsParser here
            }
        }
    }

    private String getDirectionsUrl(Attraction origin, Attraction destination) {
        String str_origin = "origin=" + origin.getLongitude() + "," + origin.getLatitude();
        String str_dest = "destination=" + destination.getLongitude() + "," + destination.getLatitude();
        String key = "key=AIzaSyADh3bvt5TjbXh74eK-lkKBH7gaQhbHh5c"; //TODO: replace this
        String parameters = str_origin + "&" + str_dest + "&" + key;
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

    // Remove the ParserTask class as it's now handled by DirectionsParser
}
