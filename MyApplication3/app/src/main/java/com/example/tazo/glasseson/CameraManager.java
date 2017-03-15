package com.example.tazo.glasseson;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Environment;
import android.view.TextureView;

import java.io.FileOutputStream;
import java.io.IOException;

import static java.io.File.separator;

/**
 * Created by tazo on 15/03/2017.
 */

public class CameraManager implements CameraInterface {

    interface CameraListener{
        void onCaptured(String filePath);
    }

    private final String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + separator + "GLASS_ON.jpeg";
    private TextureView mTextureView;
    private Camera mCamera;
    private CameraListener mCameraListener;

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            writeToFile(data);
            mCameraListener.onCaptured(FILE_PATH);
        }
    };


    public CameraManager(TextureView textureView , CameraListener cameraListener) {
        mTextureView = textureView;
        mCameraListener = cameraListener;
        mTextureView.setSurfaceTextureListener(new surfaceListener());
    }

    @Override
    public void stopCamera() {
        mCamera.stopPreview();
    }

    @Override
    public void capture(ContentResolver contentResolver , Context context) {
        mCamera.takePicture(null, null, null , mPictureCallback);
    }

    public class surfaceListener implements TextureView.SurfaceTextureListener {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            mCamera.setDisplayOrientation(90);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(1280, 720);
            mCamera.setParameters(parameters);

            try {
                mCamera.setPreviewTexture(surface);
                mCamera.startPreview();
            } catch (IOException ioe) {
                // Something bad happened
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }


        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    }


    public void writeToFile(byte[] array) {
        try {
            String path = Environment.getExternalStorageDirectory().getPath() + separator + "GLASS_ON.jpeg";
            FileOutputStream stream = new FileOutputStream(path);
            stream.write(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
