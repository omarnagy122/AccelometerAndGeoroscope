package com.student.appaccelematerandgeoroscope;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // ── Accelerometer UI ─────────────────────────────────────────
    TextView tvAccX, tvAccY, tvAccZ, tvAccMagnitude, tvAccStatus;

    // ── Gyroscope UI ───────────────────────────────────────f──────
    TextView tvGyroX, tvGyroY, tvGyroZ, tvGyroMagnitude, tvGyroStatus;

    // ── Sensors ───────────────────────────────────────────────────
    SensorManager sensorManager;
    Sensor accelerometer;
    Sensor gyroscope;

    // ── Thresholds ────────────────────────────────────────────────
    static final float GYRO_THRESHOLD = 0.5f;

    // ─────────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ── Link accelerometer UI ────────────────────────────────
        tvAccX         = findViewById(R.id.tvAccX);
        tvAccY         = findViewById(R.id.tvAccY);
        tvAccZ         = findViewById(R.id.tvAccZ);
        tvAccMagnitude = findViewById(R.id.tvAccMagnitude);
        tvAccStatus    = findViewById(R.id.tvAccStatus);

        // ── Link gyroscope UI ────────────────────────────────────
        tvGyroX         = findViewById(R.id.tvGyroX);
        tvGyroY         = findViewById(R.id.tvGyroY);
        tvGyroZ         = findViewById(R.id.tvGyroZ);
        tvGyroMagnitude = findViewById(R.id.tvGyroMagnitude);
        tvGyroStatus    = findViewById(R.id.tvGyroStatus);

        // ── Get sensor service ───────────────────────────────────
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // ── Get sensors ──────────────────────────────────────────
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope     = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // ── Check if sensors exist ───────────────────────────────
        if (accelerometer == null) tvAccStatus.setText("Accelerometer not found!");
        if (gyroscope == null)     tvGyroStatus.setText("Gyroscope not found!");
    }

    // ─────────────────────────────────────────────────────────────
    @Override
    public void onSensorChanged(SensorEvent event) {

        // ── ACCELEROMETER ────────────────────────────────────────
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float magnitude = (float) Math.sqrt(x*x + y*y + z*z);

            tvAccX.setText        ("X axis:    " + String.format("%.4f", x) + " m/s²");
            tvAccY.setText        ("Y axis:    " + String.format("%.4f", y) + " m/s²");
            tvAccZ.setText        ("Z axis:    " + String.format("%.4f", z) + " m/s²");
            tvAccMagnitude.setText("Magnitude: " + String.format("%.4f", magnitude) + " m/s²");

            float gravityRemoved = Math.abs(magnitude - SensorManager.GRAVITY_EARTH);
            String status;
            if      (gravityRemoved < 0.3f) status = "⬛ STILL";
            else if (gravityRemoved < 3.0f) status = "🚶 WALKING";
            else if (gravityRemoved < 6.0f) status = "🏃 RUNNING";
            else                            status = "📳 SHAKING";
            tvAccStatus.setText("Status: " + status);
        }

        // ── GYROSCOPE ────────────────────────────────────────────
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float rotX = event.values[0];
            float rotY = event.values[1];
            float rotZ = event.values[2];
            float magnitude = (float) Math.sqrt(rotX*rotX + rotY*rotY + rotZ*rotZ);

            tvGyroX.setText        ("Rotation X: " + String.format("%.4f", rotX) + " rad/s");
            tvGyroY.setText        ("Rotation Y: " + String.format("%.4f", rotY) + " rad/s");
            tvGyroZ.setText        ("Rotation Z: " + String.format("%.4f", rotZ) + " rad/s");
            tvGyroMagnitude.setText("Magnitude:  " + String.format("%.4f", magnitude) + " rad/s");

            String status;
            if      (magnitude < GYRO_THRESHOLD) status = "🔲 NOT rotating";
            else if (magnitude < 2.0f)           status = "🔄 SLOW rotation";
            else if (magnitude < 5.0f)           status = "🔄 FAST rotation";
            else                                 status = "💫 SPINNING rapidly!";
            tvGyroStatus.setText("Status: " + status);
        }
    }

    // ─────────────────────────────────────────────────────────────
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    // ── Start listening when app is visible ──────────────────────
    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        if (gyroscope != null)
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
    }

    // ── Stop listening to save battery ───────────────────────────
    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }
}