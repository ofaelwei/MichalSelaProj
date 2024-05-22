package com.cyberark.selapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

    private TextView text;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver eventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String eventTime = intent.getStringExtra("eventTime");
            Log.d(TAG, "onReceive: " + eventTime);
            if (eventTime != null) {
                addEventTextView(eventTime);
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
        //intentFilter.addAction(Intent.ACTIVITY);
        registerReceiver(screenEventReceiver, intentFilter);
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