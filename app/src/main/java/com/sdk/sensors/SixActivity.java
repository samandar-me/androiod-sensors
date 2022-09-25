package com.sdk.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;

public class SixActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textZ;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        textZ = findViewById(R.id.textView);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float zAxis = sensorEvent.values[2];
        textZ.setText(String.valueOf(zAxis));

        if (zAxis < -9.7) {
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        } else {
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}