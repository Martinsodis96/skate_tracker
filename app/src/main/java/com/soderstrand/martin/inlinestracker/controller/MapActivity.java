package com.soderstrand.martin.inlinestracker.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.soderstrand.martin.inlinestracker.model.Map;
import com.soderstrand.martin.inlinestracker.model.listener.MyLocationListener;
import com.soderstrand.martin.inlinestracker.R;

import java.util.regex.Pattern;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-25
 *
 * A activity to display old tracks that you have save earlier or just display an empty map.
 */
public class MapActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener {

    private Map map;
    private MyLocationListener locationListener;
    private String activity = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        this.map = new Map(this, true);
        this.locationListener = new MyLocationListener(this, map, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(map);

        //Get message from intent.
        Intent intent = getIntent();
        String points = intent.getStringExtra(TracksPageActivity.EXTRA_MESSAGE_POINTS);
        String markers = intent.getStringExtra(TracksPageActivity.EXTRA_MESSAGE_MARKERS);
        if(intent.getStringExtra(TracksPageActivity.EXTRA_MESSAGE_ACTIVITY) != null){
            activity = intent.getStringExtra(TracksPageActivity.EXTRA_MESSAGE_ACTIVITY);
        }

        //Remove [] from the string.
        if (points != null && !points.equals("[]")){
            String temp = points.replaceAll(Pattern.quote("["), "");
            String jsonArray = temp.replaceAll(Pattern.quote("]"), "");
            map.addPoints(map.toJsonArray(jsonArray));
        }
        if (markers != null && !markers.equals("[]")){
            String temp = markers.replaceAll(Pattern.quote("["), "");
            String jsonArray = temp.replaceAll(Pattern.quote("]"), "");
            System.out.println(jsonArray);
            map.addMarkers(map.toJsonArray(jsonArray));
        }

        //Initialize locationManager
        locationListener.initializeLocationManager();

        //Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationListener.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
        locationListener.getLocationManager().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);
    }

    /**
     * When the search button is clicked.
     *
     * @param view view
     */
    public void onSearch(View view){
        EditText searchInput = (EditText) findViewById(R.id.searchInput);
        map.searchForLocation(searchInput.getText().toString(), view);
    }

    public void onBackClicked(View view){
        if(activity.equals("Track")){
            Intent intent = new Intent(this, TracksPageActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, FrontPageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        this.overridePendingTransition(R.animator.slide_in_left_fast, R.animator.slide_out_right_fast);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Set the map type depending on what you choose in the spinner.
        switch (position){
            case 0:{
                map.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            }
            case 1:{
                map.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            }
            case 2:{
                map.getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            }
            case 3:{
                map.getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
