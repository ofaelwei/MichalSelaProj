package com.cyberark.selapp;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.widget.TextView;


import com.cyberark.selapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SelApp";

    private ActivityMainBinding binding;

    private LinearLayout eventContainer;

    private BackendAPI m_backendAPI = new BackendAPI();

    private TextView text;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver eventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String eventType = intent.getStringExtra("eventName");
            String userFriendlyTime = intent.getStringExtra("userFriendlyTime");
            String eventTime = intent.getStringExtra("eventTime");
            addEventTextView(userFriendlyTime + ": " + eventType);
            new Thread() {
                @Override
                public void run() {
                    m_backendAPI.SendDataToBackend(eventType, eventTime);
                }
            }.start();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Check if the permission is not granted
                if (!Settings.canDrawOverlays(context)) {
                    // If not, create an Intent to request this permission
                    Intent in = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + context.getPackageName()));
                    // Start the activity
                    startActivityForResult(in, 1);
                }
            }
        }
    };

    private BroadcastReceiver screenEventReceiver = new ScreenEventReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter("com.example.screenevents.SCREEN_EVENT");
        localBroadcastManager.registerReceiver(eventReceiver, filter);

        eventContainer = findViewById(R.id.eventContainer);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenEventReceiver, intentFilter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1);
            }
        } else {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // Permission not granted, show a message to the user.
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(eventReceiver);
        super.onDestroy();
    }

    private void addEventTextView(String eventTime) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(eventTime);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextSize(16);
        eventContainer.addView(textView);
    }
}