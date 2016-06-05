package com.soderstrand.martin.inlinestracker.controller;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.soderstrand.martin.inlinestracker.R;

import org.junit.Test;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-29
 *
 * Test all the content in FrontPageActivity
 */
public class FrontPageActivityTest extends ActivityInstrumentationTestCase2<FrontPageActivity> {

    FrontPageActivity frontPageActivity;
    public FrontPageActivityTest() {
        super(FrontPageActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        frontPageActivity = getActivity();
    }

    @Test
    public void testTextViewNotNull(){
        TextView map = (TextView) frontPageActivity.findViewById(R.id.map_button);
        TextView custom = (TextView) frontPageActivity.findViewById(R.id.custom_button);
        TextView startSkating = (TextView) frontPageActivity.findViewById(R.id.start_skating_button);
        TextView profile = (TextView) frontPageActivity.findViewById(R.id.profile_button);
        TextView tracks = (TextView) frontPageActivity.findViewById(R.id.tracks_button);
        assertNotNull(map);
        assertNotNull(custom);
        assertNotNull(startSkating);
        assertNotNull(profile);
        assertNotNull(tracks);
    }
}
