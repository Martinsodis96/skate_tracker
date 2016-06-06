package com.soderstrand.martin.inlinestracker.controller;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.soderstrand.martin.inlinestracker.R;

import org.junit.Test;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-29
 *
 * Test all the content in MapActivityTest
 */
public class MapActivityTest extends ActivityInstrumentationTestCase2<MapActivity> {

    MapActivity mapActivity;
    public MapActivityTest() {
        super(MapActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mapActivity = getActivity();
    }


    @Test
    public void testEditTextNotNull(){
        EditText searchInput = (EditText) mapActivity.findViewById(R.id.searchInput);
        assertNotNull(searchInput);
    }

    @Test
    public void testButtonNotNull(){
        Button search = (Button) mapActivity.findViewById(R.id.search);
        assertNotNull(search);
    }
}
