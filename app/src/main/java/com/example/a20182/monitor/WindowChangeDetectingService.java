package com.example.a20182.monitor;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;

import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class WindowChangeDetectingService extends AccessibilityService {
    public static int position = -1;

    final Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (position != -1) {
                if (MainActivity.mApps.get(position).getIsRun()) {
                    MainActivity.mApps.get(position).setRuntime(MainActivity.mApps.get(position).getRuntime() + 1);

                    if (MainActivity.flags_timer && MainActivity.mApps.get(position).getRuntime() % 10 == 0) {
                        handler_timer.sendEmptyMessage(0);
                    }
                    if (MainActivity.mApps.get(position).getRuntime() >= MainActivity.mApps.get(position).getLimiTime()
                            && MainActivity.mApps.get(position).getLimiTime() != 0) {
                        handler.sendEmptyMessage(0);
                        MainActivity.mApps.get(position).setIsRun(false);
                        if (MainActivity.flags_auto) {
                            MainActivity.mApps.get(position).setRuntime(0);
                        }
                    }
                }
            }
        }
    };


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        timer.schedule(task, 0, 1000);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

            ComponentName componentName = new ComponentName(
                    event.getPackageName().toString(),
                    event.getClassName().toString()
            );

            ActivityInfo activityInfo = tryGetActivity(componentName);
            boolean isActivity = activityInfo != null;
            if (isActivity) {
                for (int i = 0; i < MainActivity.mApps.size(); i++) {
                    if (componentName.getPackageName().contains("monitor")) {
                        MainActivity.mApps.get(i).setIsRun(false);
                    } else if (MainActivity.mApps.get(i).getIntent().getPackageName().equals(componentName.getPackageName())) {
                        MainActivity.mApps.get(i).setIsRun(true);
                        position = i;

                    } else if (i == MainActivity.mApps.size() - 1) {
                        position = -1;
                    }

                }
            }

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            CustomToast.showToast(getApplicationContext(), MainActivity.mApps.get(position).getTips());
        }
    };

    private Handler handler_timer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), toTime(MainActivity.mApps.get(position).getRuntime()), Toast.LENGTH_SHORT).show();
        }
    };

    private String toTime(int seconds) {

        int h = seconds / 3600;
        int m = (seconds % 3600) / 60;
        int s = (seconds % 3600) % 60;

        String startDateStr = String.format("%02d", h) + ":" + String.format("%02d", m) + ":" + String.format("%02d", s);

        return startDateStr;
    }

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {
    }

}
