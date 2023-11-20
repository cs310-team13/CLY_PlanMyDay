package com.example.planmyday;

import static org.junit.Assert.assertEquals;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DirectionsJSONParserTest {
    @Test
    public void testDecodePoly() {
        // Suppose we have the following encoded polyline string: "gfo}EtohhU".
        // This is a short, simple encoded string for demonstration purposes
        String polyline = "gfo}Etsfk*^($*&U";
        ArrayList<LatLng> points = (ArrayList<LatLng>) DirectionsJSONParser.decodePoly(polyline);
        ArrayList<LatLng> expected_points = new ArrayList<>(4);
        expected_points.add(new LatLng(52.529077, 13.403038));
        expected_points.add(new LatLng(52.529075, 13.403211));
        expected_points.add(new LatLng(52.529074, 13.403153));
        expected_points.add(new LatLng(52.529078, 13.403096));
        assertEquals(points.size(), expected_points.size());
    }
}
