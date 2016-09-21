package com.soderstrand.martin.inlinestracker.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soderstrand.martin.inlinestracker.R;
import com.soderstrand.martin.inlinestracker.model.Track;

import java.util.ArrayList;

/**
 * @author  Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-30
 *
 * A custom layout for each element in a ListView.
 */
class CustomAdapter extends ArrayAdapter<Track> {
    private ArrayList<Track> tracks;
    public CustomAdapter(Context context, ArrayList<Track> tracks) {
        super(context, R.layout.activity_custom_row, tracks);
        this.tracks=tracks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_custom_row, parent, false);

        //Initialize
        TextView header = (TextView) customView.findViewById(R.id.date);
        TextView distance = (TextView) customView.findViewById(R.id.distance_value);
        TextView avgSpeed = (TextView) customView.findViewById(R.id.avg_speed_value);
        TextView maxSpeed = (TextView) customView.findViewById(R.id.max_speed_value);
        TextView time = (TextView) customView.findViewById(R.id.time_value);


        //Get single values for each row
        String singleDate = tracks.get(position).getDate();
        String singleTime = tracks.get(position).getTime();
        String singleDistance;
        String singleAvgSpeed;
        String singleMaxSpeed;

        //try if the have a value
        try{
            singleDistance = String.format("%2.02f", tracks.get(position).getDistance()) +"km/h" ;
            singleAvgSpeed = String.format("%2.01f", tracks.get(position).getAvgSpeed()) + "km/h";
            singleMaxSpeed = String.format("%2.01f", tracks.get(position).getMaxSpeed()) + "km/h";
        }catch (Exception e){
            e.printStackTrace();
            singleDistance = "0,00km";
            singleAvgSpeed = "0,0km/h";
            singleMaxSpeed = "0,0km/h";
        }

        //Set Text
        header.setText(singleDate);
        distance.setText(singleDistance);
        avgSpeed.setText(singleAvgSpeed);
        maxSpeed.setText(singleMaxSpeed);
        time.setText(singleTime);
        return customView;
    }
}
