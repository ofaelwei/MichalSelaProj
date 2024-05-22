package com.cyberark.selapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.net.Uri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraService extends Service implements SurfaceHolder.Callback {
    private Camera mCamera;
    private Parameters parameters;
    private SurfaceView sv;
    private SurfaceHolder sHolder;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private String currentPhotoPath;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Device doesn't have a camera
            Log.e(TAG, "This device does not have a camera.");
        }

        // Open the camera
        try {
            mCamera = Camera.open();
            // Configure camera parameters
            // Set up image capture
            mCamera.takePicture(null, null, pictureCallback);
        } catch (Exception e) {
            releaseCamera();
            Log.e(TAG, "Failed to connect to camera service: " + e.getMessage());
        }

        return START_NOT_STICKY;
    }

    private Camera.PictureCallback pictureCallback = (data, camera) -> {
        // Process the image data (e.g., save to storage)
        // Stop the service when done
        File pictureFile = null;
        try {
            pictureFile = createImageFile();
        } catch (IOException ex) {
            Log.e(TAG, "Error occurred while creating the File", ex);
            return;
        }

        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Device doesn't have a camera
            Log.e(TAG, "This device does not have a camera.");
        }

        try (FileOutputStream fos = new FileOutputStream(pictureFile)) {
            fos.write(data);
        } catch (IOException e) {
            Log.e(TAG, "Error accessing file", e);
        }
        galleryAddPic();
        stopSelf();
    };

//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        // Create a SurfaceView and set it as the content of our activity.
//        sv = new SurfaceView(getApplicationContext());
//        params = new WindowManager.LayoutParams(1, 1,
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Changed this line
//                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
//                PixelFormat.TRANSLUCENT);
//        windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
//        windowManager.addView(sv, params);
//        sHolder = sv.getHolder();
//        sHolder.addCallback(this);
//    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "aviapp1");
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            Log.d(TAG, "aviapp--- " + e.getMessage());
            throw new RuntimeException(e);
        }
        Log.d(TAG, "aviapp2");
        parameters = mCamera.getParameters();
        Log.d(TAG, "aviapp3");

        try {
            Log.d(TAG, "aviapp4");
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    File pictureFile = null;
                    try {
                        pictureFile = createImageFile();
                    } catch (IOException ex) {
                        Log.e(TAG, "Error occurred while creating the File", ex);
                        return;
                    }

                    try (FileOutputStream fos = new FileOutputStream(pictureFile)) {
                        fos.write(data);
                    } catch (IOException e) {
                        Log.e(TAG, "Error accessing file", e);
                    }
                    galleryAddPic();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release the camera
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

