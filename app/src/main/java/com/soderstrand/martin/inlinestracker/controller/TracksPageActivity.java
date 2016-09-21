package com.soderstrand.martin.inlinestracker.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.soderstrand.martin.inlinestracker.model.DataBaseHandler;
import com.soderstrand.martin.inlinestracker.model.Track;
import com.soderstrand.martin.inlinestracker.model.listener.GestureListener;
import com.soderstrand.martin.inlinestracker.model.Position;

import java.util.ArrayList;

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
    private ArrayList<Track> tracks;
    private Context context = this;
    ListView martinsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        final DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        tracks = dataBaseHandler.getAllTracks();

        martinsListView = (ListView) findViewById(R.id.tracks_button);

        if(!tracks.isEmpty()){
            ListAdapter adapter = new CustomAdapter(this, tracks);
            martinsListView.setAdapter(adapter);
            martinsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //onOldTrackClicked(tracks.get(position).getPoints(), tracks.get(position).getMarkers());
                    //TODO: Make the click on track work again!
                }


            });

            martinsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    dataBaseHandler.removeTrack(position);
                    System.out.println("Removed track " + position);
                   /* final int itemPosition = position;
                    new AlertDialog.Builder(context)
                            .setTitle("Delete this track?")
                            .setMessage("Are you sure you want to Delete track?")
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dataBaseHandler.removeTrack(itemPosition);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();*/

                    return false;
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
     *
     * @param point
     * @param marker
     */
    public void onOldTrackClicked(String point, String marker){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(EXTRA_MESSAGE_POINTS, point);
        intent.putExtra(EXTRA_MESSAGE_MARKERS, marker);
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
