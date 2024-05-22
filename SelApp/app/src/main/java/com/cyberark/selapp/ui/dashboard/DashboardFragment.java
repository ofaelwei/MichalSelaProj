package com.cyberark.selapp.ui.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.BroadcastReceiver;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cyberark.selapp.BackendAPI;
import com.cyberark.selapp.R;
import com.cyberark.selapp.ScreenEventReceiver;
import com.cyberark.selapp.UsageStatsHelper;
import com.cyberark.selapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private LinearLayout eventContainer;
    private BroadcastReceiver screenEventReceiver = new ScreenEventReceiver();
    private BackendAPI m_backendAPI = new BackendAPI();

    private Handler handler = new Handler();
    private Runnable runnable;

    private LocalBroadcastManager localBroadcastManager;

    private BroadcastReceiver eventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.example.screenevents.SCREEN_EVENT")) {
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
            } else if (intent.getAction().equals("com.example.screenevents.SESSION_EVENT_UPDATE")) {
                String userId = intent.getStringExtra("userId");
                String appName = intent.getStringExtra("appName");
                String startDateUserFriendly = intent.getStringExtra("userFriendlyStartTime");
                String startDateServer = intent.getStringExtra("startDateServer");
                String endTimeUserFriendly = intent.getStringExtra("userFriendlyEndTime");
                String endTimeServer = intent.getStringExtra("endTimeServer");

                addEventTextView(startDateUserFriendly + "-" + endTimeUserFriendly + ": used " + appName);

                new Thread() {
                    @Override
                    public void run() {
                        m_backendAPI.SendSessionDataToBackend(userId, appName, startDateServer, endTimeServer);
                    }
                }.start();
            }
        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        getActivity().registerReceiver(screenEventReceiver, intentFilter);

        eventContainer = root.findViewById(R.id.eventContainer);

        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter filter = new IntentFilter("com.example.screenevents.SCREEN_EVENT");
        filter.addAction("com.example.screenevents.SESSION_EVENT_UPDATE");
        localBroadcastManager.registerReceiver(eventReceiver, filter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!Settings.canDrawOverlays(getActivity())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, 1);
            }
        } else {
            if (!Settings.canDrawOverlays(getActivity())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, 1);
            }
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                UsageStatsHelper.logRecentAppLaunches(getActivity());
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(getActivity())) {
                    // Permission not granted, show a message to the user.
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        localBroadcastManager.unregisterReceiver(eventReceiver);
        handler.removeCallbacks(runnable);
        super.onDestroyView();

        binding = null;
    }

    private void addEventTextView(String eventTime) {
        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(eventTime);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextSize(16);
        eventContainer.addView(textView);
    }
}