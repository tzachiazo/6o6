package com.example.tazo.glasseson;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CaptureActivity extends AppCompatActivity implements RotationSensorManager.OrientationChangeListener , CameraManager.CameraListener{

    private TextureView mTextureView;
    private CameraManager mCameraManager;
    private Button mCaptureButton;
    private RotationSensorManager mRotationSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        mTextureView = (TextureView) findViewById(R.id.image_preview);
        mCaptureButton = (Button)findViewById(R.id.button_capture);
        mCameraManager = new CameraManager(mTextureView , this);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mRotationSensorManager = new RotationSensorManager(sensorManager, this);


        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraManager.capture(getContentResolver() , CaptureActivity.this);
            }
        });

    }

    @Override
    public void onOrientationChanged(int degree ) {
        mTextureView.setRotation(degree);
        mCaptureButton.setVisibility((degree==0)? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        mRotationSensorManager.close();
        mCameraManager.stopCamera();
        super.onDestroy();
    }

    @Override
    public void onCaptured(String filePath) {
        Toast.makeText(this, filePath, Toast.LENGTH_LONG).show();
        mRotationSensorManager.close();
        mCaptureButton.setVisibility(View.INVISIBLE);
        mCameraManager.stopCamera();
    }
}
