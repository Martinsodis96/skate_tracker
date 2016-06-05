package com.soderstrand.martin.inlinestracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

/**
 * @author Martin Söderstrand
 * @version 9.0
 * Date: 2016-03-26
 *
 * A sqLite database to save values from each track in the application.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;              // Database Version
    private static final String DATABASE_NAME = "mapManager";   // Database Name
    private static final String TABLE_MAPS = "maps";            // Table name

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
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MAPS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + COLUMN_DATE + " TEXT ,"
                + COLUMN_DISTANCE + " TEXT ,"
                + COLUMN_AVGSPEED + " TEXT ,"
                + COLUMN_MAXSPEED + " TEXT ,"
                + COLUMN_TIME + " TEXT ,"
                + COLUMN_LATLNG + " TEXT ,"
                + COLUMN_MARKERS + " TEXT" +
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
     * Get a single column from th table in the database.
     *
     * @param columnIndex The name of the column you want the values from.
     * @return - The values from the database as a Json String.
     */
    public String getColumn(String columnIndex){
        String date = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MAPS + " WHERE 1";
        //Cursor point
        Cursor c = db.rawQuery(query, null);
        //Move to first row
        c.moveToFirst();

        //While loop to print put every value in the column
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(columnIndex)) != null) {
                date += c.getString(c.getColumnIndex(columnIndex));
                date += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return date;
    }

}
