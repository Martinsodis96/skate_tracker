package com.soderstrand.martin.inlinestracker.controller;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.soderstrand.martin.inlinestracker.R;

import org.junit.Test;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-29
 *
 * Test all the content in TracksPageActivityTest
 */
public class TracksPageActivityTest extends ActivityInstrumentationTestCase2<TracksPageActivity> {
    TracksPageActivity tracksPageActivity;
    public TracksPageActivityTest() {
        super(TracksPageActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tracksPageActivity = getActivity();
    }

    @Test
    public void testListViewNotNull(){
        ListView tracks = (ListView) tracksPageActivity.findViewById(R.id.tracks_button);
        assertNotNull(tracks);
    }
}
