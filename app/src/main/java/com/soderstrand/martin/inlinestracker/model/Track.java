package com.soderstrand.martin.inlinestracker.model;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-26
 *
 * A data bean class to store the values from a single track in the application.
 */
public class Track {
    private int id;                     //Object id
    private String date;                //When the track is saved
    private double distance;            //The distance
    private double avgSpeed;            //Average speed
    private double maxSpeed;            //Max speed
    private String time;                //Duration
    private ArrayList<LatLng> points;   //The points to draw lines between
    private ArrayList<LatLng>  markers;  //The markers on the map when you save

    /**
     *
     * //@param date - The distance
     * //@param distance - Average speed
     * //@param avgSpeed - Max speed
     * //@param maxSpeed - Duration
     * @param time - The points to draw lines between
     */
    public Track(String date, double distance, double avgSpeed, double maxSpeed, String time, ArrayList<LatLng> points, ArrayList<LatLng> markers){
        this.points = points;
        this.markers = markers;
        this.date = date;
        this.distance = distance;
        this.avgSpeed = avgSpeed;
        this.time = time;
        this.maxSpeed = maxSpeed;
    }

    public Track(){
        points = new ArrayList<>();
        markers = new ArrayList<>();

    }

    public ArrayList<LatLng> getMarkers() {
        return markers;
    }

    public void clearMarkers(){
        markers.clear();
    }

    public void addMarker(LatLng marker) {
        markers.add(marker);
    }

    public void setMarkers(ArrayList<LatLng> markers) {
        this.markers = markers;
    }

    public void clearPoints(){
        markers.clear();
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public void addPoint(LatLng marker) {
        markers.add(marker);
    }

    public void setPoints(ArrayList<LatLng> points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        //Date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        this.date = dateFormat.format(cal.getTime());
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(int time) {
            float avgSpeed = ((float) distance*1000) / time;
            this.avgSpeed = avgSpeed*3.6;
    }


    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}
