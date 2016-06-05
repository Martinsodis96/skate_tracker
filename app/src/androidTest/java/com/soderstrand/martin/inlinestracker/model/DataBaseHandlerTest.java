package com.soderstrand.martin.inlinestracker.model;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;


/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-29
 *
 * Test to add and get data from the DataBase
 */
public class DataBaseHandlerTest extends AndroidTestCase {
    DataBaseHandler db;

    @Override
    public void setUp() throws Exception {
        //@author http://stackoverflow.com/questions/8499554/android-junit-test-for-sqliteopenhelper
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new DataBaseHandler(context);
    }

    @Override
    public void tearDown() throws Exception {
        //@author http://stackoverflow.com/questions/8499554/android-junit-test-for-sqliteopenhelper
        db.close();
        super.tearDown();
    }

    @Test
    public void testGetColumn() {
        ArrayList<LatLng> points = new ArrayList<>();
        ArrayList<LatLng> markers = new ArrayList<>();
        points.add(new LatLng(2,2));
        markers.add(new LatLng(2,2));

        Track track1 = new Track(points, markers, "test", 0.0, 0.0,0.0,"test");
        Track track2 = new Track(points, markers, "test", 0.0, 2.2,0.0,"test");
        db.addTrack(track1);
        db.addTrack(track2);

        String answer = "0.0\n2.2\n";
        String result = db.getColumn("avgSpeed");

        assertEquals(result, answer);
    }

    @Test
    public void testAddTrack() {
        ArrayList<LatLng> points = new ArrayList<>();
        ArrayList<LatLng> markers = new ArrayList<>();
        points.add(new LatLng(2,2));
        markers.add(new LatLng(2, 2));

        Track track1 = new Track(points, markers, "test", 0.0, 0.0,0.0,"test");
        db.addTrack(track1);
    }
}
