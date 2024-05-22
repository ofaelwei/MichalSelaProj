package com.cyberark.selapp;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsageStatsHelper {
    public static void logRecentAppLaunches(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

            Calendar calendar = Calendar.getInstance();
            long endTime = calendar.getTimeInMillis();
            calendar.add(Calendar.SECOND, -10);
            long startTime = calendar.getTimeInMillis();

            UsageEvents usageEvents = usageStatsManager.queryEvents(startTime, endTime);
            UsageEvents.Event event = new UsageEvents.Event();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat userFriendlyFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    Date date = new Date(event.getTimeStamp());
                    String userId = "user123";
                    String appName = event.getPackageName();

                    String startDateUserFriendly = userFriendlyFormatter.format(date);
                    String startDateServer = formatter.format(date);

                    String endTimeUserFriendly = userFriendlyFormatter.format(endTime + 60000);
                    String endTimeServer = formatter.format(endTime + 60000);

                    Intent localIntent = new Intent("com.example.screenevents.SESSION_EVENT_UPDATE");
                    localIntent.putExtra("userId", userId);
                    localIntent.putExtra("appName", appName);
                    localIntent.putExtra("userFriendlyStartTime", startDateUserFriendly);
                    localIntent.putExtra("startDateServer", startDateServer);
                    localIntent.putExtra("userFriendlyEndTime", endTimeUserFriendly);
                    localIntent.putExtra("endTimeServer", endTimeServer);

                    LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);

                    Log.d("UsageStatsHelper", "App launched: " + event.getPackageName() + " at " + date.toString());
                }
            }
        }
    }
}
