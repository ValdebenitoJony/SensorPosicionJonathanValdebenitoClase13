package com.example.sensorposicion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText posicion;
    private SensorManager sm;
    private Sensor sa;
    SensorEventListener sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        posicion = findViewById(R.id.editTextText);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sa = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sa == null) {
            Toast.makeText(this, "no hay Sensor", Toast.LENGTH_SHORT).show();

        }else {
            sel = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    float x = sensorEvent.values[0];
                    posicion.setText(Float.toString(x));
                    if (x > -5 && latigo ==0){
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.BLUE);

                    } else if (x >5 && latigo == 1) {
                        latigo++;
                        sonido();
                        latigo = 0;

                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };
        }
    }
    private void sonido(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.latigosheldong);
        mp.start();
    }
    public void iniciar(){
        sm.registerListener(sel, sa, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    protected void onResume(){
        iniciar();
        super.onResume();
    }
    public  void detener(){
        sm.unregisterListener(sel);
    }
    public void onStop(){
        detener();
        super.onStop();
    }
}