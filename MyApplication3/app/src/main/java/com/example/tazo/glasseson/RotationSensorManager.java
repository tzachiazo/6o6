package com.example.tazo.glasseson;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by tazo on 15/03/2017.
 */

public class RotationSensorManager implements SensorEventListener {

    private final int SENSITIVITY_IN_DEGREE = 1;
    public interface OrientationChangeListener{
        void onOrientationChanged(int degree);
    }

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private OrientationChangeListener mListener;
    private float lastDegree = 0;

    public RotationSensorManager(SensorManager sensorManager, OrientationChangeListener listener){
        mListener = listener;
        mSensorManager = sensorManager ;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(this, mSensor, 10000);

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            //If rotation is more than 1 degree
            float currentDegree = event.values[2];
            if(Math.abs(lastDegree - currentDegree) > SENSITIVITY_IN_DEGREE){
                lastDegree = currentDegree;
                mListener.onOrientationChanged((int)currentDegree);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void close(){
        mSensorManager.unregisterListener(this);
    }
}
