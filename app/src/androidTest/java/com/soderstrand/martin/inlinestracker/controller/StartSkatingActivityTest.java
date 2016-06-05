package com.soderstrand.martin.inlinestracker.controller;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soderstrand.martin.inlinestracker.R;

import org.junit.Test;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-29
 *
 * Test all the content in StartSkatingActivityTest
 */
public class StartSkatingActivityTest extends ActivityInstrumentationTestCase2<StartSkatingActivity> {

    StartSkatingActivity startSkatingActivity;
    public StartSkatingActivityTest() {
        super(StartSkatingActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        startSkatingActivity = getActivity();
    }

    @Test
    public void testRelativeLayoutNotNull(){
        RelativeLayout bottom = (RelativeLayout) startSkatingActivity.findViewById(R.id.bottom);
        assertNotNull(bottom);
    }
    @Test
    public void testTextViewNotNull(){
        TextView save = (TextView) startSkatingActivity.findViewById(R.id.save_button);
        TextView delete = (TextView) startSkatingActivity.findViewById(R.id.delete_button);
        TextView distance = (TextView) startSkatingActivity.findViewById(R.id.distance);
        TextView distanceText = (TextView) startSkatingActivity.findViewById(R.id.distanceText);
        TextView time = (TextView) startSkatingActivity.findViewById(R.id.time_value);
        TextView timeText = (TextView) startSkatingActivity.findViewById(R.id.timetext);
        assertNotNull(save);
        assertNotNull(delete);
        assertNotNull(distance);
        assertNotNull(distanceText);
        assertNotNull(time);
        assertNotNull(timeText);
    }
}
