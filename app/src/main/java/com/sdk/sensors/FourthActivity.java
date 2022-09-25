package com.sdk.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;

public class FourthActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textX, textY, textZ;
    private SensorManager sensorManager;
    private Sensor sensor;
    private boolean isAccelerometer, isNotFirstTime;
    private float currX, currY, currZ, lastX, lastY, lastZ;
    private float xDiff, yDiff, zDiff;
    private float shakeThreshold = 5f;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        textZ = findViewById(R.id.textZ);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometer = true;
        } else {
            textX.setText("ACCELEROMETER is not available");
            isAccelerometer = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAccelerometer) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAccelerometer) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currX = sensorEvent.values[0];
        currY = sensorEvent.values[1];
        currZ = sensorEvent.values[2];

        textX.setText(currX + " m/s2");
        textY.setText(currY + " m/s2");
        textZ.setText(currZ + " m/s2");

        if (isNotFirstTime) {
            xDiff = Math.abs(lastX - currX);
            yDiff = Math.abs(lastY - currY);
            zDiff = Math.abs(lastZ - currZ);

            if ((xDiff > shakeThreshold && yDiff > shakeThreshold) || (xDiff > shakeThreshold && zDiff > shakeThreshold)
                    || (yDiff > shakeThreshold && zDiff > shakeThreshold)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(500);
                }
            }
        }

        lastX = currX;
        lastY = currY;
        lastZ = currZ;
        isNotFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}