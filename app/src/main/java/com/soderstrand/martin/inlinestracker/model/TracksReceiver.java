package com.soderstrand.martin.inlinestracker.model;

import android.content.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-04-03
 *
 * Loads all the tracks from the database and store the values in String arrays.
 */
public class TracksReceiver {

    private DataBaseHandler db;   //The sqList database
    private String[] dates;       //dates
    private String[] distance;    //distance
    private String[] avgSpeed;    //average speed
    private String[] maxSpeed;    //max speed
    private String[] time;        //time
    private String[] points;      //points
    private String[] markers;     //Markers

    public TracksReceiver(Context context){
        db = new DataBaseHandler(context);
        dates = makeToArray(db.getColumn("date"));           //dates
        distance = makeToArray(db.getColumn("distance"));    //distance
        avgSpeed = makeToArray(db.getColumn("avgSpeed"));    //average speed
        maxSpeed = makeToArray(db.getColumn("maxSpeed"));    //max speed
        time = makeToArray(db.getColumn("time"));            //time
        points = makeToArray(db.getColumn("latlng"));        //points
        markers = makeToArray(db.getColumn("markers"));      //Markers
    }

    public TracksReceiver(){

    }

    /**
     * To turn the String from the database to an Array and make it into reverse order.
     *
     * @param data The string you want to make to an array.
     * @return - Array of strings.
     */
    public String[] makeToArray(String data){
        String[] dataArray;
        dataArray = data.split("\n");
        List<String> reverseList = Arrays.asList(dataArray);
        Collections.reverse(reverseList);
        dataArray = (String[]) reverseList.toArray();
        return dataArray;
    }

    public String[] getDistance() {
        return distance;
    }

    public String[] getTime() {
        return time;
    }

    public String[] getPoints() {
        return points;
    }

    public String[] getMaxSpeed() {
        return maxSpeed;
    }

    public String[] getMarkers() {
        return markers;
    }

    public String[] getDates() {
        return dates;
    }

    public String[] getAvgSpeed() {
        return avgSpeed;
    }

}
