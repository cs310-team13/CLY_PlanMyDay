package com.example.planmyday;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.example.planmyday.Attraction;
import com.example.planmyday.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

public class ViewGoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    // Assume dailyPlans is a List of Lists of Attraction objects
    private List<List<Attraction>> dailyPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_google_map); // Set the layout for the activity

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Retrieve the dailyPlans from the Intent that started this activity
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawItineraryPaths();
        // TODO: Add code to display the paths for each day's itinerary
    }

    // TODO: Add methods to handle drawing paths on the map

    private void drawItineraryPaths() {
        if (dailyPlans == null) return;

        for (List<Attraction> dayPlan : dailyPlans) {
            PolylineOptions polylineOptions = new PolylineOptions();
            for (Attraction attraction : dayPlan) {
                LatLng point = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                polylineOptions.add(point);
            }
            mMap.addPolyline(polylineOptions);
        }
    }
}
