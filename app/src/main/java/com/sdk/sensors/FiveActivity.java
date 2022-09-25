package com.sdk.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class FiveActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textStepCounter, textStepDetector;
    private SensorManager sensorManager;
    private Sensor sensorCounter, sensorDetector;
    private boolean isStepAvailable, isDetectorAvailable;
    private int counterStep = 0, counterDetect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        textStepCounter = findViewById(R.id.stepCounter);
        textStepDetector = findViewById(R.id.stepDetector);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            isStepAvailable = true;
            sensorCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        } else {
            isStepAvailable = false;
            textStepCounter.setText("Not available");
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isDetectorAvailable = true;
        } else {
            isDetectorAvailable = false;
            textStepDetector.setText("Not available");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == sensorCounter) {
            counterStep = (int) sensorEvent.values[0];
            textStepCounter.setText(String.valueOf(counterStep));
        }
        if (sensorEvent.sensor == sensorDetector) {
            counterDetect = (int) (counterDetect + sensorEvent.values[0]);
            textStepDetector.setText(String.valueOf(counterDetect));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Toast.makeText(this, sensor.getName() + "\n" + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStepAvailable) {
            sensorManager.registerListener(this, sensorCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(isDetectorAvailable) {
            sensorManager.registerListener(this, sensorDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isStepAvailable) {
            sensorManager.unregisterListener(this);
        }
        if (isDetectorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}