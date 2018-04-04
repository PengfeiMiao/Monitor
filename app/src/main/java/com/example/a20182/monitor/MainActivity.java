package com.example.a20182.monitor;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private AppAdapter mAdapter;
    public static List<Application> mApps;
    public static List<Application> AppList = new ArrayList<Application>();
    public static boolean flags_auto = false;
    public static boolean flags_timer = false;
    public static boolean flags_refresh = false;

    Timer timer0 = new Timer();
    final TimerTask task0 = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mListView.setAdapter(mAdapter);
                    if (flags_refresh) {
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        flags_refresh = false;
                    }
                }
            });
        }
    };

    public static boolean isStartAccessibilityService(Context context, String name) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> serviceInfos = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : serviceInfos) {
            ;
            if (info.getId().contains(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isStartAccessibilityService(this, "monitor")) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }

        mListView =  findViewById(R.id.lv_monitor);
        mListView.setOnItemClickListener(this);
        if (AppList.isEmpty()) {
            loadAppInfomation(this);
        }
        mApps = getSelectList(AppList);
        mAdapter = new AppAdapter(this, mApps);
        timer0.schedule(task0, 0, 1000);

    }

    private void loadAppInfomation(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        Collections.sort(infos, new ResolveInfo.DisplayNameComparator(pm));
        if (infos != null) {
            AppList.clear();
            for (int i = 0; i < infos.size(); i++) {
                Application app = new Application();
                ResolveInfo info = infos.get(i);
                app.setName(info.loadLabel(pm).toString());
                app.setIcon(info.loadIcon(pm));
                app.setIntent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
                app.setRuntime(0);
                app.setSelected(false);
                AppList.add(app);
            }
        }
    }

    private List<Application> getSelectList(List<Application> AppList) {
        List<Application> selectList = new ArrayList<Application>();
        for (int i = 0; i < AppList.size(); i++) {
            if (AppList.get(i).getSelected()) {
                selectList.add(AppList.get(i));
            }
        }
        return selectList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void btnAdd(View view) {
        startActivity(new Intent(MainActivity.this, Select.class));
    }

    public void btnRenew(View view) {
        for (int i = 0; i < mApps.size(); i++) {
            mApps.get(i).setRuntime(0);
            mApps.get(i).setIsRun(false);
        }
        mListView.setAdapter(mAdapter);
    }

    public void btnAuto(View view) {
        flags_auto = !flags_auto;
        ImageView iv_Auto = findViewById(R.id.iv_auto);
        if (!flags_auto) {
            iv_Auto.setImageResource(R.drawable.auto);
        } else {
            iv_Auto.setImageResource(R.drawable.auto_press);
        }
    }

    public void btnTimer(View view) {
        flags_timer = !flags_timer;
        ImageView iv_Auto = findViewById(R.id.iv_timer);
        if (!flags_timer) {
            iv_Auto.setImageResource(R.drawable.timer);
        } else {
            iv_Auto.setImageResource(R.drawable.timer_press);
        }
    }
}

