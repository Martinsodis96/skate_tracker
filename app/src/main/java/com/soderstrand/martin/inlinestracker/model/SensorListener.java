package com.soderstrand.martin.inlinestracker.model;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Martin Söderstrand
 * @version 1.0
 * Date: 2016-04-03
 *
 * A sensor for detecting fall
 */
public class SensorListener  implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Track track;
    private Map map;
    private Context context;
    private MyLocationListener locationListener;

    /**
     *
     * @param context The context you called from
     * @param track Object of the Track class
     * @param map Object of the Map class
     * @param locationListener Object from the MyLocationListener class.
     */
    public SensorListener(Context context, Track track, Map map, MyLocationListener locationListener){
        this.track = track;
        this.map = map;
        this.context = context;
        this.locationListener = locationListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            double xyz = Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));       //fall algorithm

            if(xyz >= 33){
                if(locationListener.getLocation() != null){
                    map.addMarker(locationListener.getLocation().getLatitude(), locationListener.getLocation().getLongitude(), "Fall", "Fall speed" + xyz + "m/s");
                    track.addMarker(new LatLng(locationListener.getLocation().getLatitude(), locationListener.getLocation().getLongitude()));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Initialize the sensor
     */
    public void initialize(){
        senSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, (int) SensorManager.STANDARD_GRAVITY);
    }

    public Sensor getSenAccelerometer() {
        return senAccelerometer;
    }

    public SensorManager getSenSensorManager() {
        return senSensorManager;
    }
}
