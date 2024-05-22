package com.cyberark.selapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenEventReceiver extends BroadcastReceiver {
    public static final String TAG = "selApp - ScreenEventReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        long currentTimeMillis = System.currentTimeMillis();
        String actionString = "";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        Log.d(TAG, "onReceive");

        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            actionString = formatter.format(date) + ": screen ON";
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            actionString = formatter.format(date) + ": screen OFF";
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            actionString = formatter.format(date) + ": device unlocked successfully";
        }

        // Broadcast the event locally within the app
        Intent localIntent = new Intent("com.example.screenevents.SCREEN_EVENT");
        localIntent.putExtra("eventTime", actionString);
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
    }
}
