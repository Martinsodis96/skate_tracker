package com.soderstrand.martin.inlinestracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * @author Martin Söderstrand
 * @version 9.0
 * Date: 2016-03-26
 *
 * A sqLite database to save values from each track in the application.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;              // Database Version
    private static final String DATABASE_NAME = "track_manager";   // Database Name
    private static final String TABLE_MAPS = "tracks";            // Table name

    // Columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE= "date";
    private static final String COLUMN_DISTANCE = "distance";
    private static final String COLUMN_AVGSPEED = "avgSpeed";
    private static final String COLUMN_MAXSPEED = "maxSpeed";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_MARKERS = "markers";
    private static final String COLUMN_LATLNG = "latlng";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MAPS + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " VARCHAR(100) NULL,"
                + COLUMN_DISTANCE + " DOUBLE NULL,"
                + COLUMN_AVGSPEED + " DOUBLE NULL, "
                + COLUMN_MAXSPEED + " DOUBLE NULL ,"
                + COLUMN_TIME + " VARCHAR(100) NULL ,"
                + COLUMN_LATLNG + " JSON NULL ,"
                + COLUMN_MARKERS + " JSON NULL" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAPS);
        onCreate(db);
    }

    /**
     * Inserts the values from the Track object to th database.
     * @param track A object of the Track class.
     */
    public void addTrack(Track track) {
        Gson gson = new Gson();
        //Arrays to Json
        String pointsString= gson.toJson(track.getPoints());
        String markersString= gson.toJson(track.getMarkers());

        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, track.getDate());
        values.put(COLUMN_DISTANCE, track.getDistance());
        values.put(COLUMN_AVGSPEED, track.getAvgSpeed());
        values.put(COLUMN_MAXSPEED, track.getMaxSpeed());
        values.put(COLUMN_TIME, track.getTime());
        values.put(COLUMN_LATLNG, pointsString);
        values.put(COLUMN_MARKERS, markersString);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MAPS, null, values);
        db.close();
    }

    /**
     *
     * @return
     */
    public ArrayList<Track> getAllTracks() {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<LatLng> points = null;
        ArrayList<LatLng> markers = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MAPS;

        //Cursor point
        Cursor c = db.rawQuery(query, null);
        //Move to first row
        c.moveToFirst();

        //While loop to print put every value in the column
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_ID)) != null) {

                try {
                    points = jsonStringToArray(c.getString(c.getColumnIndex(COLUMN_LATLNG)));
                    markers = jsonStringToArray(c.getString(c.getColumnIndex(COLUMN_MARKERS)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Track track = new Track(c.getString(c.getColumnIndex(COLUMN_DATE)),
                        c.getDouble(c.getColumnIndex(COLUMN_DISTANCE)),
                        c.getDouble(c.getColumnIndex(COLUMN_AVGSPEED)),
                        c.getDouble(c.getColumnIndex(COLUMN_MAXSPEED)),
                        c.getString(c.getColumnIndex(COLUMN_TIME)),
                        points,
                        markers
                        );

                tracks.add(track);
            }
            c.moveToNext();
        }
        db.close();
        return tracks;
    }

    private ArrayList<LatLng> jsonStringToArray(String jsonString) throws JSONException {

        ArrayList<LatLng> stringArray = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            String[] latLngString =  jsonArray.getString(i).split(",");
            String[] lat =  latLngString[0].split(":");
            String[] lng =  latLngString[1].split(":");
            double latitude = Double.parseDouble(lat[1]);
            double longitude = Double.parseDouble(lng[1]);
            LatLng latLng = new LatLng(latitude, longitude);

            stringArray.add(latLng);
        }

        return stringArray;
    }

    public String getTrack(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MAPS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        //Move to first row
        c.moveToFirst();

        db.close();
        return query;
    }

    public void removeTrack(int track){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_MAPS + " WHERE " + COLUMN_ID + " = " + track + 1;
        db.execSQL(query);
        db.close();
    }
}
