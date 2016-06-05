package com.soderstrand.martin.inlinestracker.controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.soderstrand.martin.inlinestracker.R;
import com.soderstrand.martin.inlinestracker.model.GestureListener;
import com.soderstrand.martin.inlinestracker.model.Position;
import com.soderstrand.martin.inlinestracker.model.TracksReceiver;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-03-23
 *
 * A activity to display old tracks that you have save earlier.
 */
public class TracksPageActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_POINTS = "com.soderstrand.martin.inlinestracker.POINTS";
    public final static String EXTRA_MESSAGE_MARKERS = "com.soderstrand.martin.inlinestracker.MARKERS";
    public final static String EXTRA_MESSAGE_ACTIVITY = "com.soderstrand.martin.inlinestracker.ACTIVITY";
    private GestureDetectorCompat gestureDetectorCompat;
    ListView martinsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracks_page);
        final TracksReceiver tracks = new TracksReceiver(this);
        martinsListView = (ListView) findViewById(R.id.tracks);

        if(!tracks.getDates()[0].equals("")){
            ListAdapter adapter = new CustomAdapter(this, tracks.getDates(), tracks.getDistance(), tracks.getAvgSpeed(), tracks.getMaxSpeed(), tracks.getTime());
            martinsListView.setAdapter(adapter);
            martinsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onOldTrackClicked(position, tracks.getPoints(), tracks.getMarkers());
                }


            });

        }else{
            String[] value = {"No tracks found"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,value);
            martinsListView.setAdapter(adapter);
        }

        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureListener(this, Position.TRACKS));
    }

    /**
     *  If an element in the tracks ListView is clicked.
     *
     * @param position Position of the clicked element in the listView
     * @param points String Array of latlng points for lines.
     * @param markers String Array of latlng points for markers.
     */
    public void onOldTrackClicked(int position, String[] points, String[] markers){
        String pointJson = points[position];
        String markerJson = markers[position];
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(EXTRA_MESSAGE_POINTS, pointJson);
        intent.putExtra(EXTRA_MESSAGE_MARKERS, markerJson);
        intent.putExtra(EXTRA_MESSAGE_ACTIVITY, "Track");
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * If you click on the back button you will return to the front page.
     *
     * @param view
     */
    public void onBackTracksClicked(View view){
        Intent intent = new Intent(this, FrontPageActivity.class);
        //Clear all activitys and put this intent on top.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.overridePendingTransition(R.animator.slide_in_right_fast, R.animator.slide_out_left_fast);
    }
}
