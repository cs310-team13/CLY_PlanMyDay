package com.example.planmyday;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TripTest {
    DayPlannerActivity dayPlannerActivity = new DayPlannerActivity();

    Attraction crypto = new Attraction("Crypto.com Arena", 34.043239725397534, -118.26715754465302, 30, false);
    Attraction veniceBeach = new Attraction("Venice Beach", 33.99415852939198, -118.4810175097266, 60, false);
    Attraction disney = new Attraction("Disneyland Park", 33.812323543075266, -117.91904930604437, 180, false);
    Attraction getty = new Attraction("The Getty", 34.078124637386516, -118.47403103115997, 120, false);
    Attraction universal = new Attraction("Universal Studios Hollywood", 34.13823221460428, -118.35322810046722, 240, false);
    Attraction grove = new Attraction("The Grove", 34.07672370541953, -118.35865922045846, 60, false);

    @Test
    public void testFeasibleTrip() {
        List<List<Attraction>> desiredTrip = new ArrayList<List<Attraction>>();
        desiredTrip.add(new ArrayList<Attraction>());
        desiredTrip.get(0).add(crypto);
        desiredTrip.get(0).add(veniceBeach);

        assertEquals("Trip feasibility", true, dayPlannerActivity.isFeasibleTrip(desiredTrip));
    }

    @Test
    public void testInfeasibleTrip() {
        List<List<Attraction>> desiredTrip = new ArrayList<List<Attraction>>();
        desiredTrip.add(new ArrayList<Attraction>());
        desiredTrip.get(0).add(crypto);
        desiredTrip.get(0).add(veniceBeach);
        desiredTrip.get(0).add(disney);
        desiredTrip.get(0).add(getty);
        desiredTrip.get(0).add(universal);
        desiredTrip.get(0).add(grove);

        assertEquals("Trip feasibility", false, dayPlannerActivity.isFeasibleTrip(desiredTrip));
    }

    @Test
    public void testShortestPath() {
        ArrayList<Attraction> attractions = new ArrayList<Attraction>();
        attractions.add(veniceBeach);
        attractions.add(disney);
        attractions.add(getty);
        attractions.add(universal);

        ArrayList<Attraction> expected = new ArrayList<Attraction>();
        expected.add(getty);
        expected.add(veniceBeach);
        expected.add(universal);
        expected.add(disney);

        assertEquals("Test: Shortest path", expected, dayPlannerActivity.calculateShortestPath(attractions));
    }

    @Test
    public void testPlanTrip() {
        ArrayList<Attraction> attractions = new ArrayList<Attraction>();
        attractions.add(crypto);
        attractions.add(grove);
        List<List<Attraction>> output = dayPlannerActivity.planTrip(attractions, 2);

        assertEquals("Test: Plan Trip", 2, output.size());
    }

    @Test
    public void testCalculateRouteTime() {
        ArrayList<Attraction> attractions = new ArrayList<Attraction>();
        attractions.add(crypto);
        attractions.add(disney);

        assertEquals("Test: Calculate Route Time", 210, dayPlannerActivity.computeRouteTime(attractions));
    }

}
