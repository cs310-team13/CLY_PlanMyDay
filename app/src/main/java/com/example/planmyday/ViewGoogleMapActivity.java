package com.example.planmyday;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//TODO: EVERY coordinate in this file is FLIPPED due to incorrect attraction class data members.
public class ViewGoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<List<Attraction>> dailyPlans;
    private final int[] COLORS = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN};

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
        if (!dailyPlans.isEmpty() && !dailyPlans.get(0).isEmpty()) {
            // Move the camera to the first point of the first day plan
            Attraction firstAttraction = dailyPlans.get(0).get(0);
            LatLng initialPosition = new LatLng(firstAttraction.getLongitude(), firstAttraction.getLatitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 8));
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
                new FetchURL(dayIndex).execute(url); // Pass the day index to the AsyncTask
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

    private class FetchURL extends AsyncTask<String, Void, String> {

        private int dayIndex;

        // Constructor to receive the day index
        public FetchURL(int dayIndex) {
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
            new ParserTask(dayIndex).execute(result); // Pass the day index to the ParserTask
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private int dayIndex;

        // Constructor to receive the day index
        public ParserTask(int dayIndex) {
            this.dayIndex = dayIndex;
        }
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    Toast.makeText(getApplicationContext(), "lat: " + lat + ", lng: " + lng, Toast.LENGTH_LONG).show();
                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(10); // Increased line width for visibility
                lineOptions.color(COLORS[dayIndex % COLORS.length]);
            }

            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }
}
