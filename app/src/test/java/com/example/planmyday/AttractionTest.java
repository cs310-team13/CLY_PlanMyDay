package com.example.planmyday;

import static org.junit.Assert.*;
import org.junit.Test;
import android.os.Parcel;

public class AttractionTest {
    @Test
    public void testAttractionConstructorAndGetters() {
        Attraction attraction = new Attraction("USC", -118.285117, 34.0223519, 120, true);

        assertEquals("USC", attraction.getName());
        assertEquals(-118.285117, attraction.getLongitude(), 0.001);
        assertEquals(34.0223519, attraction.getLatitude(), 0.001);
        assertEquals(120, attraction.getTime());
        assertTrue(attraction.isAtUSC());
    }

    @Test
    public void testHaversineFormula() {
        double distance = Attraction.haversine(34.0223519, -118.285117, 34.068921, -118.4473698); // USC to UCLA
        assertEquals(15, distance / 1000, 1); // Allowing a 1 km margin of error
    }

    @Test
    public void testDistanceTo() {
        Attraction usc = new Attraction("USC", -118.285117, 34.0223519, 120, true);
        Attraction ucla = new Attraction("UCLA", -118.4473698, 34.068921, 90, false);

        // The distance should be approximately the same as the haversine test
        assertEquals(15, usc.distanceTo(ucla) / 1000, 1); // 1 km margin of error
    }

    @Test
    public void testSettersAndChangedState() {
        Attraction attraction = new Attraction("Original Name", 1.0, 1.0, 60, false);

        attraction.setName("USC");
        attraction.setLongitude(-118.285117);
        attraction.setLatitude(34.0223519);
        attraction.setTime(120);
        attraction.setAtUSC(true);

        assertEquals("USC", attraction.getName());
        assertEquals(-118.285117, attraction.getLongitude(), 0.001);
        assertEquals(34.0223519, attraction.getLatitude(), 0.001);
        assertEquals(120, attraction.getTime());
        assertTrue(attraction.isAtUSC());
    }
}