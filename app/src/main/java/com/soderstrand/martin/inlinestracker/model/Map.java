package com.soderstrand.martin.inlinestracker.model;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-04-03
 *
 * A Google Map with methods for starting and loading old tracks.
 */
public class Map implements OnMapReadyCallback {
    private GoogleMap mMap;                                 //The google map
    private Context context;                                //The context the klass is called from
    Polyline line;                                          //Poly line for drawing lines on the map
    private boolean loadMap;                                //If you are loading a map
    private ArrayList<LatLng> points = new ArrayList<>();   //Points for old poly lines
    private ArrayList<LatLng> markers = new ArrayList<>();  //Points for old markers

    /**
     * Constructor for the Map class
     *
     * @param context What context it's calling from
     * @param loadMap If you are loading an old map or not
     */
    public Map(Context context, boolean loadMap) {
        this.context = context;
        this.loadMap = loadMap;
    }

    public Map() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertNoGps();
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        this.initializeMap();

        if(loadMap){
            drawLines();
            placeMarkers();
            if(points.size() > 0){
                zoomCamers(points.get(1));
            }
        }
    }

    /**
     * Initialize what sort of map you want.
     */
    public void initializeMap() {
        this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void alertNoGps(){
        new AlertDialog.Builder(context)
                .setTitle("Oh, no!")
                .setMessage("Turn on your gps to find your location")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    /**
     * Zooms the camera to a specific location.
     *
     * @param latlng
     */
    public void zoomCamers(LatLng latlng){
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16.0f));
    }

    /**
     * Draws a new line on the map
     *
     * @param newLatLng from Latlng
     * @param currentLatLng to LatLng
     */
    public void drawLine(LatLng newLatLng, LatLng currentLatLng){
        if(mMap != null){
            PolylineOptions options = new PolylineOptions().width(6).color(Color.BLUE).geodesic(true);
            options.add(newLatLng, currentLatLng);
            line = mMap.addPolyline(options);
        }
    }

    /**
     * Adds a marker on the map with your own title and description.
     *
     * @param latitude lat
     * @param longitude lng
     * @param title Title on the marker
     * @param description Description about the marker
     */
    public void addMarker(Double latitude, Double longitude, String title, String description){
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)).title(title)
                .snippet(description));
    }

    /**
     * converts a long string of , separated Json object into a list with Json objects.
     *
     * @param jsonString The string with Json objects
     * @return - List with Json objects.
     */
    public String[] toJsonArray(String jsonString){
        String jsonStringInput = jsonString;
        String [] jsonArray;
        int mod = 3;    //Every third comma
        int counter = 0;
        for (int i = 0; i < jsonStringInput.toCharArray().length; i++) {
            if(jsonStringInput.charAt(i) == ','){
                counter++;
            }
            if(counter%mod == 0 && counter != 0){
                StringBuilder sb = new StringBuilder(jsonStringInput);
                sb.deleteCharAt(i);
                sb.insert(i, "\n");
                jsonStringInput = sb.toString();
                counter = 0;
            }
        }
        jsonArray = jsonStringInput.split("\n");
        return jsonArray;
    }

    /**
     * Adds the points to the arrayList in the class.
     *
     * @param points LatLng positions.
     */
    public void addPoints(String[] points){
        for (int i = 0; i < points.length; i++) {
            JsonElement jElement = new JsonParser().parse(points[i]);
            JsonObject jObject = jElement.getAsJsonObject();
            LatLng point = new LatLng(Double.parseDouble(jObject.get("latitude").toString())
                    ,Double.parseDouble(jObject.get("longitude").toString()));

            this.points.add(point);
        }
    }

    /**
     * Adds the LatLng positions into the class variable ArrayList.
     *
     * @param markers - LatLng positions
     */
    public void addMarkers(String[] markers) {
        for (int i = 0; i < markers.length; i++) {
            JsonElement jelement = new JsonParser().parse(markers[i]);
            JsonObject  jobject = jelement.getAsJsonObject();
            LatLng mark = new LatLng(Double.parseDouble(jobject.get("latitude").toString())
                    ,Double.parseDouble(jobject.get("longitude").toString()));

            this.markers.add(mark);
        }
    }

    /**
     * Draws the lines from an old track on the map.
     */
    public void drawLines(){
        PolylineOptions options = new PolylineOptions().width(6).color(Color.BLUE).geodesic(true);
        for (LatLng l : this.points){
            options.add(l);
            System.out.println(l);
        }
        line = mMap.addPolyline(options);
    }

    /**
     * places the markers from an old track on the map.
     */
    public void placeMarkers(){
        for (LatLng l : this.markers){
            mMap.addMarker(new MarkerOptions()
                    .position(l).title("Fall")
                    .snippet("Fall speed:"));
        }
    }

    public void animateCamera(Location location){

    }

    /**
     * For searching for a location the map.
     *
     * @param searchInput The input from the search.
     * @param view The view
     */
    public void searchForLocation(String searchInput, View view){
        List<Address> addresses = null;
        Address address = null;
        if(searchInput != null && !searchInput.equals("")){
            Geocoder geo = new Geocoder(context);
            try {
                addresses = geo.getFromLocationName(searchInput, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses.get(0) != null){
                address = addresses.get(0);
            }

            //Animates the map that location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 12.0f));
            InputMethodManager inputMethodManager =(InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            //Add a marker on the location your searched for
            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(address.getLatitude(), address.getLongitude())).title(address.toString())
            );
        }
    }

    /**
     * Gets the map
     * @return - Google map.
     */
    public GoogleMap getMap() {
        return mMap;
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public ArrayList<LatLng> getMarkers() {
        return markers;
    }
}
