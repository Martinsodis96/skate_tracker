package com.soderstrand.martin.inlinestracker.model;

import android.test.AndroidTestCase;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-29
 *
 * Test class to test the methods i created myself in the TracksReceiver class.
 */
public class TracksReceiverTest extends AndroidTestCase {

    TracksReceiver tracksReceiver;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tracksReceiver = new TracksReceiver();
    }

    @Test
    public void testToJsonArray() {
        String test = "test\ntest\ntest\ntest";
        String[] answer = {
                "test",
                "test",
                "test",
                "test"
        };
        String[] result = tracksReceiver.makeToArray(test);

        assertArrayEquals(result, answer);
    }
}
