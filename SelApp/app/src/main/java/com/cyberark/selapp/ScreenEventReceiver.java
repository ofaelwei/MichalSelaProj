package com.cyberark.selapp;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenEventReceiver extends BroadcastReceiver {
    public static final String TAG = "selApp - ScreenEventReceiver";
    private static final int REQUEST_CODE = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        long currentTimeMillis = System.currentTimeMillis();
        String actionString = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat userFriendlyFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();

        Log.d(TAG, "onReceive");

        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            actionString = "screen ON";
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            actionString = "screen OFF";
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            actionString = "device unlocked";
            Intent i = new Intent(context, CameraService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(i);
            } else {
                context.startService(i);
            }
        }

        // Broadcast the event locally within the app
        Intent localIntent = new Intent("com.example.screenevents.SCREEN_EVENT");
        localIntent.putExtra("eventName", actionString);
        localIntent.putExtra("userFriendlyTime", userFriendlyFormatter.format(date));
        localIntent.putExtra("eventTime", formatter.format(date));

        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
    }
}
