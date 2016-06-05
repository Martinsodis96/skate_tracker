package com.soderstrand.martin.inlinestracker.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.soderstrand.martin.inlinestracker.model.DataBaseHandler;
import com.soderstrand.martin.inlinestracker.model.Map;
import com.soderstrand.martin.inlinestracker.model.MyLocationListener;
import com.soderstrand.martin.inlinestracker.R;
import com.soderstrand.martin.inlinestracker.model.SensorListener;
import com.soderstrand.martin.inlinestracker.model.Track;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-24
 *
 * A activity to start a new track.
 */
public class StartSkatingActivity extends FragmentActivity {
    private TextView time;
    private TextView distance;
    private DataBaseHandler db;
    private int count = 1;      //counter for the timer.
    private Map map;
    private Track track;
    private MyLocationListener locationListener;
    private SensorListener sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        map = new Map(this, false);
        track = new Track();
        locationListener = new MyLocationListener(this, track, map, true);
        sensor = new SensorListener(this, track, map, locationListener);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(map);
        timer();

        //initialize
        time = (TextView) findViewById(R.id.time);
        distance = (TextView) findViewById(R.id.distance);
        db = new DataBaseHandler(this);

        locationListener.initializeLocationManager();
        sensor.initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationListener.getLocationManager().requestLocationUpdates(locationListener.getLocationProvider(), 0, 10, locationListener);
        sensor.getSenSensorManager().registerListener(sensor, sensor.getSenAccelerometer(), SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * If the save button is clicked this displays an alert box.
     *
     * @param view view
     */
    public void onSavePressed(View view){
        new AlertDialog.Builder(this)
                .setTitle("Save track")
                .setMessage("Are you sure you want to Save this track?")
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        endAndSave();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    /**
     * If the delete button is clicked this displays an alert box.
     *
     * @param view view
     */
    public void onDeletePressed(View view){
        new AlertDialog.Builder(this)
                .setTitle("Delete track")
                .setMessage("Are you sure you want to Delete this track?")
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        endWithoutSaving();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    /**
     * Ends the track, cleans the map but doesn't save it.
     */
    public void endWithoutSaving(){
        //clear map
        track.clearPoints();
        track.clearMarkers();

        //start intent
        this.finish();
    }

    @Override
    public void onBackPressed() {

        //Alert a box with options to delete, save och cancel if you try to leave the activity.
        new AlertDialog.Builder(this)
                .setTitle("Leave track")
                .setMessage("Do you want to save this track?")
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        endAndSave();
                    }
                })
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        endWithoutSaving();
                    }
                })
                .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }


    /**
     * End and save this track and returns to home screen.
     */
    public void endAndSave(){
        //Set track values
        track.setAvgSpeed(count);
        track.setDate();
        track.setTime(time.getText().toString());

        //Add track to DataBase
        db.addTrack(this.track);

        //clear map
        track.clearPoints();
        track.clearMarkers();

        //start intent
        this.finish();
    }

    /**
     * Timer for the clock for each track.
     */
    public void timer(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int seconds = count;
                        int minutes = seconds / 60;
                        seconds = seconds % 60;
                        time.setText(String.format("%02d:%02d", minutes, seconds));
                        count++;
                        distance.setText(String.format("%2.02f", track.getDistance()) + "km");
                    }
                });
            }
        }, 1000, 1000);
    }
}
