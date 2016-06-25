package com.example.borch.surfaceviewcarboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends CardboardActivity implements SensorEventListener,
       // CardboardView.StereoRenderer,
        MediaPlayer.OnPreparedListener,
        SurfaceHolder.Callback{
    final static String TAG = "MainActivity";
    final static String USERNAME = "";
    final static String PASSWORD = "";
    //final static String RTSP_URL = "rtp://239.255.12.42:5004/";
//    final static String RTSP_URL = "rtsp://10.0.2.2:5004/live.sdp";

    AddressManager manager = AddressManager.getInstance();

    final static String LOCALHOST_EMULATOR_ADDRESS = "10.0.2.2";

//    final static String RTSP_URL = "rtp://192.168.1.49:5004/";
//    final static String RTSP_URL = "rtp://10.0.2.2:5004/";
    final static String GP3_URL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

//    final static String SrcPath = "rtsp://v5.cache1.c.youtube.com/CjYLENy73wIaLQnhycnrJQ8qmRMYESARFEIJbXYtZ29vZ2xlSARSBXdhdGNoYPj_hYjnq6uUTQw=/0/0/0/video.3gp";

    public static final int SEND_MILLISECONDS = 250;
    public static final int SHOW_MILLISECONDS = 2500;
    private float [] eulerAngles;
    private float [] orientation;
    private PositionSender sender;
    public static String sampleVideoUrl = "";

    private SensorManager mSensorManager;
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    float[] mGravity = new float[4];
    float[] mGeomagnetic = new float[4];

    Sensor accelerometer;
    Sensor magnetometer;
    public float azimut = 0f;
    public float pitch = 0f;
    public float roll= 0f;

    public float referenceAngle = 0f;

    private VideoView myVideoViewLeft;
    private VideoView myVideoViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "Starting app");

        sampleVideoUrl = "android.resource://" + this.getPackageName() + "/" + R.raw.sample_video;
        String sdpUri = "android.resource://" + this.getPackageName() + "/" + R.raw.cam1;

        setContentView(R.layout.activity_main);
//
        myVideoViewRight = (VideoView)findViewById(R.id.myvideoviewRight);
        myVideoViewLeft = (VideoView)findViewById(R.id.myvideoviewLeft);

        MediaController mc = new MediaController(this);
        myVideoViewLeft.setMediaController(mc);
        myVideoViewLeft.setVideoURI(Uri.parse(manager.getCameraLeft()));
        myVideoViewLeft.requestFocus();
        myVideoViewLeft.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                myVideoViewLeft.start();
            }
        });
        myVideoViewLeft.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                myVideoViewLeft.stopPlayback();
                myVideoViewLeft.setVideoURI(Uri.parse(manager.getCameraLeft()));
                myVideoViewLeft.requestFocus();
                myVideoViewLeft.start();
            }
        });

        myVideoViewRight.setMediaController(mc);
        myVideoViewRight.setVideoURI(Uri.parse(manager.getCameraRight()));
        myVideoViewRight.requestFocus();
        myVideoViewRight.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                myVideoViewRight.start();
            }
        });
        myVideoViewRight.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                myVideoViewRight.stopPlayback();
                myVideoViewRight.setVideoURI(Uri.parse(manager.getCameraRight()));
                myVideoViewRight.requestFocus();
                myVideoViewRight.start();
            }
        });

        myVideoViewLeft.bringToFront();
        myVideoViewRight.bringToFront();

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        try {
            sender = new PositionSender(manager.getControlServerAddress(),
                    manager.getControlServerPort());
        }
        catch (Exception e) {}


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        final Handler handler = new Handler();
//        final Runnable r = new Runnable() {
//            public void run() {
//
//                String message = String.format("%.2f", Math.toDegrees(azimut));
//                showToast(message);
//
//                if(manager.shouldSend())
//                handler.postDelayed(this, SHOW_MILLISECONDS);
//            }
//        };
//        handler.postDelayed(r, SHOW_MILLISECONDS);


        final Handler handler2 = new Handler();
        final Runnable r2 = new Runnable() {
            public void run() {
                try {
                    String message = String.format("%.2f", Math.toDegrees(azimut));
                    sender.send(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(manager.shouldSend())
                handler2.postDelayed(this, SEND_MILLISECONDS);
            }
        };
        handler2.postDelayed(r2, SEND_MILLISECONDS);

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            System.arraycopy(event.values, 0, mGravity, 0, 3);
            mLastAccelerometerSet = true;
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            System.arraycopy(event.values, 0, mGeomagnetic, 0, 3);
            mLastMagnetometerSet = true;
        }


        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            float Rot[] = new float[9];
            float I[] = new float[9];

            boolean success =
                    SensorManager.getRotationMatrix(Rot, null, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[4];
                float outR[] = new float[9];
                SensorManager.remapCoordinateSystem(Rot,
                        SensorManager.AXIS_X,SensorManager.AXIS_Z, outR);
                SensorManager.getOrientation(outR, orientation);
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
                pitch = orientation[1];
                roll = orientation[2];
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        _mediaPlayer = new MediaPlayer();
//        _mediaPlayer.setDisplay(_surfaceHolder);
//
//        Context context = getApplicationContext();
//        Map<String, String> headers = getRtspHeaders();
//        Uri source = Uri.parse(RTSP_URL);
//
//        try {
//            // Specify the IP camera's URL and auth headers.
//            _mediaPlayer.setDataSource(context, source);
//
//            // Begin the process of setting up a video stream.
//            _mediaPlayer.setOnPreparedListener(this);
//            _mediaPlayer.prepareAsync();
//        }
//        catch (Exception e) {}
    }

    private Map<String, String> getRtspHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        String basicAuthValue = getBasicAuthValue(USERNAME, PASSWORD);
        headers.put("Authorization", basicAuthValue);
        return headers;
    }

    private String getBasicAuthValue(String usr, String pwd) {
        String credentials = usr + ":" + pwd;
        int flags = Base64.URL_SAFE | Base64.NO_WRAP;
        byte[] bytes = credentials.getBytes();
        return "Basic " + Base64.encodeToString(bytes, flags);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onResume(){
        super.onResume();
        mLastAccelerometerSet = false;
        mLastMagnetometerSet = false;
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
//        _mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        manager.setSend(false);
        finish();
        startActivity(intent);
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public boolean onTouch(View v, MotionEvent event) {
        // cambiar ángulo
        return false;
    }
}
