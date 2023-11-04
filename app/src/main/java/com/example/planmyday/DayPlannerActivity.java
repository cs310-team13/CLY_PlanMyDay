package com.example.planmyday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_planner);

        // Retrieve the selected choices from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<Attraction> selected_attractions = intent.getParcelableArrayListExtra("attractions");
            Button submitButton = findViewById(R.id.submitButton);

            int numDays = intent.getIntExtra("numDays", 1);
            Boolean isFeasibleTrip = true;
            // Calculate the shortest path
            ArrayList<Attraction> shortestPath = calculateShortestPath(selected_attractions);
            List<List<Attraction>> dailyPlans = planTrip(shortestPath, numDays);

            TextView displayChoicesText = findViewById(R.id.displayChoices);
            StringBuilder choicesBuilder = new StringBuilder();

            choicesBuilder.append("\nNumber of days: ").append(numDays).append("\n\n");

            for (int day = 0; day < dailyPlans.size(); day++) {
                List<Attraction> dailyAttractions = dailyPlans.get(day);
                int routeTime = computeRouteTime(dailyAttractions);
                if (routeTime >= 8 * 60) {
                    isFeasibleTrip = false;
                }
                choicesBuilder.append("Day ").append(day + 1).append(":\n");
                for (Attraction attraction : dailyAttractions) {
                    choicesBuilder.append(attraction.getName()).append("\n");
                }
                choicesBuilder.append("\nTotal time: ").append(routeTime).append(" minutes").append("\n\n");
            }

            displayChoicesText.setText(choicesBuilder.toString());

            if (!isFeasibleTrip) {
                submitButton.setEnabled(false); // Disable the submit button
                Toast.makeText(DayPlannerActivity.this, "Too many attractions in one day!", Toast.LENGTH_SHORT).show();
            } else {
                submitButton.setEnabled(true);
            }
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(DayPlannerActivity.this, MapsActivity.class);
                    // pass dailyPlans to MapsActivity
                    ArrayList<Bundle> dailyPlansBundles = new ArrayList<>();
                    for (List<Attraction> dailyPlan : dailyPlans) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("dayPlan", new ArrayList<Parcelable>(dailyPlan));
                        dailyPlansBundles.add(bundle);
                    }
                    mapIntent.putParcelableArrayListExtra("dailyPlans", dailyPlansBundles);
                }
            });
        }
    }
}
