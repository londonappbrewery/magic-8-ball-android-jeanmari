package com.londonappbrewery.magiceightball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView imageBall;
    private int[] ballArray = {
            R.drawable.ball1,
            R.drawable.ball2,
            R.drawable.ball3,
            R.drawable.ball4,
            R.drawable.ball5
    };
    private SensorManager sm;
    private float acelVal; //currennt acceleration and gravity
    private float acelLast; //last accelaration value and gravity
    private float shake; // accelaration value differ from gravity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageBall = (ImageView)findViewById(R.id.imageBall);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;


    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast = acelVal;
            acelVal = (float)Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake*0.9f + delta;

            if (shake > 12){
                askBall();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void askBall(){
        Random rand = new Random();
        int randomNum = rand.nextInt(5);
        imageBall.setImageResource(ballArray[randomNum]);
        final Handler handler = new Handler();

    }

}
