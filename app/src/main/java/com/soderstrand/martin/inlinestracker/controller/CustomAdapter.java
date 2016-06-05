package com.soderstrand.martin.inlinestracker.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soderstrand.martin.inlinestracker.R;

/**
 * @author  Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-30
 *
 * A custom layout for each element in a ListView.
 */
class CustomAdapter extends ArrayAdapter<String> {

    private final String[] distance;
    private final String[] avgSpeed;
    private final String[] maxSpeed;
    private final String[] time;
    public CustomAdapter(Context context, String[] date, String[] distance, String[] avgSpeed, String[] maxSpeed, String[] time) {
        super(context, R.layout.custom_row, date);
        this.distance = distance;
        this.avgSpeed = avgSpeed;
        this.maxSpeed = maxSpeed;
        this.time = time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        //Get single values for each row
        String singleDate = getItem(position);
        String singleDistance;
        String singleAvgSpeed;
        String singleMaxSpeed;

        //try if the have a value
        try{
            singleDistance = String.format("%2.02f", Double.valueOf(distance[position])) + "km";
            singleAvgSpeed = String.format("%2.01f", Double.valueOf(avgSpeed[position])) + "km/h";
            singleMaxSpeed = String.format("%2.01f", Double.valueOf(maxSpeed[position])) + "km/h";
        }catch (Exception e){
            singleDistance = "0,00km";
            singleAvgSpeed = "0,0km/h";
            singleMaxSpeed = "0,0km/h";
        }

        String singleTime = time[position];

        //Initialize
        TextView header = (TextView) customView.findViewById(R.id.date);
        TextView distance = (TextView) customView.findViewById(R.id.distance);
        TextView avgSpeed = (TextView) customView.findViewById(R.id.avgSpeed);
        TextView maxSpeed = (TextView) customView.findViewById(R.id.maxSpeed);
        TextView time = (TextView) customView.findViewById(R.id.time);

        //Set Text
        header.setText(singleDate);
        distance.setText(singleDistance);
        avgSpeed.setText(singleAvgSpeed);
        maxSpeed.setText(singleMaxSpeed);
        time.setText(singleTime);
        return customView;
    }
}
