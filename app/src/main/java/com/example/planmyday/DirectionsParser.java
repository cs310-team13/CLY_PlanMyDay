package com.example.planmyday;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsParser {

    private GoogleMap mMap;
    private int[] mColors;

    public DirectionsParser(GoogleMap map, int[] colors) {
        this.mMap = map;
        this.mColors = colors;
    }

    public void parseDirections(String jsonData, int dayIndex) {
        ParserTask parserTask = new ParserTask(mMap, mColors, dayIndex);
        parserTask.execute(jsonData);
    }

    private static class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private GoogleMap mMap;
        private int[] mColors;
        private int mDayIndex;

        public ParserTask(GoogleMap map, int[] colors, int dayIndex) {
            this.mMap = map;
            this.mColors = colors;
            this.mDayIndex = dayIndex;
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
                Log.e("DirectionsParser", "Error parsing JSON data", e);
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
                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(mColors[mDayIndex % mColors.length]);

                if (lineOptions != null && mMap != null) {
                    mMap.addPolyline(lineOptions);
                }
            }
        }
    }
}
