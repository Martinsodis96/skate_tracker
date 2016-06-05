package com.soderstrand.martin.inlinestracker.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-04-03
 *
 * A location listener to locate your GPS position
 */
public class MyLocationListener implements LocationListener {

    private LocationManager locationManager;
    private String locationProvider;
    private Location location;
    private Track track;
    private Map map;
    private Context context;
    private boolean startTrack;

    /**
     * Call this constructor if you want to load an existing track.
     *
     * @param context The context you called from
     * @param track Object of the Track class
     * @param map Object of the Map class
     * @param startTrack if you are starting a new track or not
     */
    public MyLocationListener(Context context, Track track, Map map, boolean startTrack){
        this.track = track;
        this.map = map;
        this.context = context;
        this.startTrack = startTrack;
    }

    /**
     * Call this constructor if you don't want to load an existing track.
     *
     * @param context The context you called from
     * @param map Object of the Map class
     * @param startTrack if you are starting a new track or not
     */
    public MyLocationListener(Context context, Map map, boolean startTrack){
        this.map = map;
        this.context = context;
        this.startTrack = startTrack;
    }

    @Override
    public void onLocationChanged(Location location) {
        //If you want to start a new track
        if(startTrack){
            LatLng newLatlng = new LatLng(location.getLatitude(), location.getLongitude());
            track.addPoint(newLatlng);
            if(this.location != null) {
                LatLng currentLatlng = new LatLng(this.location.getLatitude(), this.location.getLongitude());
                if(!track.getPoints().contains(currentLatlng)){
                    track.addPoint(currentLatlng);
                }
                map.drawLine(newLatlng, currentLatlng);
                double distance = track.getDistance() + (this.location.distanceTo(location)) * 0.001;
                track.setDistance(distance);

            }

            if(location.getSpeed()*3.6 > track.getMaxSpeed()){
                track.setMaxSpeed(location.getSpeed()*3.6);
            }
            this.location = location;
        }
    }

    /**
     * Sets up the location manager
     */
    public void initializeLocationManager() {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //define the criteria
        Criteria criteria = new Criteria();

        this.locationProvider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.location = locationManager.getLastKnownLocation(locationProvider);

        //initialize the location
        if(location != null) {
            onLocationChanged(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public String getLocationProvider() {
        return locationProvider;
    }

    public Location getLocation() {
        return location;
    }
}
