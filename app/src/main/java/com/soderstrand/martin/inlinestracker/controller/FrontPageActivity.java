package com.soderstrand.martin.inlinestracker.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.soderstrand.martin.inlinestracker.R;
import com.soderstrand.martin.inlinestracker.model.GestureListener;
import com.soderstrand.martin.inlinestracker.model.Position;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-23
 *
 * The frontPage-activity to control what happens when the user is trying to navigate through the application.
 */
public class FrontPageActivity extends AppCompatActivity {

    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);

        //For swipe detection.
        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureListener(this, Position.FRONTPAGE));
    }

    /**
     * When the "Start skating" button is pressed.
     * @param view
     */
    public void onStartClicked(View view) {
        Intent intent = new Intent(this, StartSkatingActivity.class);
        startActivity(intent);
    }

    /**
     * When the "Tracks" button is pressed.
     * @param view
     */
    public void onTracksClicked(View view){
        Intent intent = new Intent(this, TracksPageActivity.class);
        startActivity(intent);

        //animation between activitys
        this.overridePendingTransition(R.animator.slide_in_left_fast, R.animator.slide_out_right_fast);
    }

    /**
     * When the "Tracks" button is pressed.
     * @param view
     */
    public void onMapClicked(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);

        //animation between activitys
        this.overridePendingTransition(R.animator.slide_in_right_fast, R.animator.slide_out_left_fast);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
