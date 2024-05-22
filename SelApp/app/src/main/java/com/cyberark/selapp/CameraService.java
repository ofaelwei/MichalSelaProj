package com.cyberark.selapp;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.IBinder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.io.IOException;

public class CameraService extends Service implements SurfaceHolder.Callback {
    private Camera mCamera;
    private Parameters parameters;
    private SurfaceView sv;
    private SurfaceHolder sHolder;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a SurfaceView and set it as the content of our activity.
        sv = new SurfaceView(getApplicationContext());
        params = new WindowManager.LayoutParams(1, 1,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Changed this line
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.addView(sv, params);
        sHolder = sv.getHolder();
        sHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        parameters = mCamera.getParameters();

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    // Save the image data to a file here
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
