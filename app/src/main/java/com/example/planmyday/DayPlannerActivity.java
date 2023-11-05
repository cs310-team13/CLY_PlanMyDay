package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DayPlannerActivity extends Activity {
    public static ArrayList<Attraction> calculateShortestPath(ArrayList<Attraction> attractions) {
        if (attractions.size() <= 2) {
            return attractions;
        }

        final int n = attractions.size();
        final int END_STATE = (1 << n) - 1;
        double[][] dp = new double[1 << n][n];
        int[][] next = new int[1 << n][n];
        for (double[] d : dp) Arrays.fill(d, Double.MAX_VALUE);
        for (int[] arr : next) Arrays.fill(arr, -1);
        for (int i = 1; i < n; i++) {
            dp[1 << i][i] = attractions.get(0).distanceTo(attractions.get(i));
        }

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int end = 0; end < n; end++) {
                if ((mask & (1 << end)) != 0) {
                    int prevMask = mask ^ (1 << end);
                    for (int e = 0; e < n; e++) {
                        if ((prevMask & (1 << e)) != 0) {
                            double newDist = dp[prevMask][e] + attractions.get(e).distanceTo(attractions.get(end));
                            if (newDist < dp[mask][end]) {
                                dp[mask][end] = newDist;
                                next[mask][end] = e;
                            }
                        }
                    }
                }
            }
        }

        // Reconstruct path
        double minTourCost = Double.MAX_VALUE;
        int lastIndex = -1;
        for (int i = 1; i < n; i++) {
            if (dp[END_STATE][i] + attractions.get(i).distanceTo(attractions.get(0)) < minTourCost) {
                minTourCost = dp[END_STATE][i] + attractions.get(i).distanceTo(attractions.get(0));
                lastIndex = i;
            }
        }

        ArrayList<Attraction> path = new ArrayList<>();
        int state = END_STATE;
        while (state != 0) {
            path.add(attractions.get(lastIndex));
            int temp = lastIndex;
            lastIndex = next[state][lastIndex];
            state = state ^ (1 << temp);
        }
        Collections.reverse(path);
        return path;
    }

    // Compute route time
    public static int computeRouteTime(List<Attraction> attractions) {
        int time = 0;
        for (Attraction attraction : attractions) {
            time += attraction.getTime();
        }
        return time;
    }

    private List<List<Attraction>> planTrip(ArrayList<Attraction> attractions, int numDays) {
        List<List<Attraction>> tripPlan = new ArrayList<>();
        for (int i = 0; i < numDays; i++) {
            tripPlan.add(new ArrayList<>());
        }

        // Even distribution of attractions across the days
        for (int i = 0; i < attractions.size(); i++) {
            tripPlan.get(i % numDays).add(attractions.get(i));
        }

        return tripPlan;
    }



    private Spinner daySpinner;
    private List<List<Attraction>> dailyPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_planner);

        // Retrieve the selected choices from the Intent
        Intent intent = getIntent();
        ArrayList<Attraction> selected_attractions = intent.getParcelableArrayListExtra("attractions");
        int numDays = intent.getIntExtra("numDays", 1);

        // Calculate the shortest path and plan the trip
        ArrayList<Attraction> shortestPath = calculateShortestPath(selected_attractions);
        dailyPlans = planTrip(shortestPath, numDays);

        // Display the choices and plans
        TextView displayChoicesText = findViewById(R.id.displayChoices);
        displayChoicesText.setText(buildChoicesDisplay(dailyPlans, numDays));

        // Initialize the Spinner
        daySpinner = findViewById(R.id.daySpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, getDaySelectionList(numDays));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerAdapter);
        daySpinner.setSelection(0); // Default to day 1

        // Set up the buttons
        Button viewInGoogleMapsButton = findViewById(R.id.viewInGoogleMapsButton);
        Button viewDirectlyHereButton = findViewById(R.id.viewDirectlyHereButton);

        // Check if the trip is feasible
        if (!isFeasibleTrip(dailyPlans)) {
            viewInGoogleMapsButton.setEnabled(false);
            viewDirectlyHereButton.setEnabled(false);
            Toast.makeText(DayPlannerActivity.this, "Too many attractions in one day!", Toast.LENGTH_SHORT).show();
        } else {
            // Set up button to view in Google Maps
            viewInGoogleMapsButton.setOnClickListener(v -> {
                int selectedDayIndex = daySpinner.getSelectedItemPosition();
                Intent mapIntent = new Intent(DayPlannerActivity.this, ViewGoogleMapActivity.class);

                // Create a new Bundle for the selected day's attractions
                Bundle dayPlanBundle = new Bundle();
                dayPlanBundle.putParcelableArrayList("dayPlan", new ArrayList<Parcelable>(dailyPlans.get(selectedDayIndex)));

                // Create a new ArrayList of Bundles to pass to the Intent
                ArrayList<Bundle> selectedDayPlansBundle = new ArrayList<>();
                selectedDayPlansBundle.add(dayPlanBundle);

                // Put the ArrayList of one Bundle into the Intent
                mapIntent.putParcelableArrayListExtra("dailyPlansBundles", selectedDayPlansBundle);
                startActivity(mapIntent);
            });


            // Set up button to view directly here (within the app)
            viewDirectlyHereButton.setOnClickListener(v -> {
                Intent internalMapIntent = new Intent(DayPlannerActivity.this, InternalMapActivity.class);
                internalMapIntent.putParcelableArrayListExtra("dailyPlansBundles", bundleDailyPlans(dailyPlans));
                startActivity(internalMapIntent);
            });
        }
    }

    private List<String> getDaySelectionList(int numDays) {
        List<String> daySelections = new ArrayList<>();
        for (int i = 1; i <= numDays; i++) {
            daySelections.add("Day " + i);
        }
        return daySelections;
    }


    private void initializeSpinner(int numDays) {
        List<String> days = new ArrayList<>();
        for (int i = 1; i <= numDays; i++) {
            days.add("Day " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);
    }

    private boolean isFeasibleTrip(List<List<Attraction>> dailyPlans) {
        for (List<Attraction> dayPlan : dailyPlans) {
            if (computeRouteTime(dayPlan) >= 8 * 60) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Bundle> bundleDailyPlans(List<List<Attraction>> dailyPlans) {
        ArrayList<Bundle> dailyPlansBundles = new ArrayList<>();
        for (List<Attraction> dailyPlan : dailyPlans) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("dayPlan", new ArrayList<Parcelable>(dailyPlan));
            dailyPlansBundles.add(bundle);
        }
        return dailyPlansBundles;
    }

    private String buildChoicesDisplay(List<List<Attraction>> dailyPlans, int numDays) {
        StringBuilder choicesBuilder = new StringBuilder();
        choicesBuilder.append("\nNumber of days: ").append(numDays).append("\n\n");
        for (int day = 0; day < dailyPlans.size(); day++) {
            List<Attraction> dailyAttractions = dailyPlans.get(day);
            int routeTime = computeRouteTime(dailyAttractions);
            choicesBuilder.append("Day ").append(day + 1).append(":\n");
            for (Attraction attraction : dailyAttractions) {
                choicesBuilder.append(attraction.getName()).append("\n");
            }
            choicesBuilder.append("\nTotal time: ").append(routeTime).append(" minutes").append("\n\n");
        }
        return choicesBuilder.toString();
    }
}
