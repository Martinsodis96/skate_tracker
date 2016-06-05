package com.soderstrand.martin.inlinestracker.model;

import android.test.AndroidTestCase;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;


/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-29
 *
 * Test class to test the methods i created myself in the Map class.
 */
public class MapTest extends AndroidTestCase {

    Map map;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        map = new Map();
    }
    @Test
    public void testToJsonArray() {
        String test = "{test,test,test},{test,test,test},{test,test,test}";
        String[] answer = {
                "{test,test,test}",
                "{test,test,test}",
                "{test,test,test}"
        };
        String[] result = map.toJsonArray(test);

        assertArrayEquals(result, answer);
    }

    @Test
    public void testAddPoints() {
        ArrayList<LatLng> points = new ArrayList<>();
        points.add(new LatLng(2,2));

        String[] point = {
                "{latitude:2, longitude:2}"
        };
        map.addPoints(point);

        assertEquals(map.getPoints().get(0), points.get(0));
    }

    @Test
    public void testAddMarkers() {
        ArrayList<LatLng> points = new ArrayList<>();
        points.add(new LatLng(2, 2));

        String[] point = {
                "{latitude:2, longitude:2}"
        };
        map.addMarkers(point);

        assertEquals(map.getMarkers().get(0), points.get(0));
    }
}
