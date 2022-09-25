package com.sdk.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

// ALL SENSORS // FINDING IS AVAILABLE SENSOR IN YOUR ANDROID PHONE

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private SensorManager sensorManager;
    private List<Sensor> sensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder stringBuilder = new StringBuilder();

        for (Sensor sensor: sensorList) {
            stringBuilder.append(sensor.getName()).append("\n");
        }

        String specificSensorIsAvailable =
                (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) ? "Available" : "Not available";

        textView.setText(specificSensorIsAvailable);
    }
}