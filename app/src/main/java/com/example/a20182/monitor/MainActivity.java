package com.example.a20182.monitor;

/*
 * Firstly I use the isStartAccessibilityService to juage if the AccessibilityService is open.
 * If not it will jump to the system setting dialog.
 * Then the loadAppInfomation function to load the all applications' information through PackageManager,
 * including application's name, icon, package name and so on.
 * The getSelectList function is to filter the selected application so as to display them in list.
 * And then deny the storedata and readdata function to store and read data for the applications.
 * At last to use a TimerTask to refresh the listview timely.
 */

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.provider.Settings;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    private ListView mListView;
    private AppAdapter mAdapter;

    private Spinner spinner;
    private SpinnerAdapter SpAdapter;

    private int[] mToolicon = {R.drawable.add,R.drawable.renew,R.drawable.auto,R.drawable.timer};
    private String[] mStrList = {"add","clear","auto","timer"};

    public static List<Application> mApps;
    public static List<Application> AppList = new ArrayList<Application>();
    public static List<StoreInfo> pStoreInfo = new ArrayList<StoreInfo>();
    public static boolean flags_refresh = false;
    private int flag_teach = -1;

    Timer timer0=new Timer();
    final TimerTask task0 = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mListView.setAdapter(mAdapter);
                    if(flags_refresh) {
                        startActivity(new Intent(MainActivity.this, MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        flags_refresh = false;
                    }

                    switch (SpinnerAdapter.flags_position){
                        case 0:
                            startActivity(new Intent(MainActivity.this, Select.class));
                            SpinnerAdapter.flags_position = -1;
                            break;
                        case 1:
                            for(int i=0;i<mApps.size();i++) {
                                mApps.get(i).setRuntime(0);
                                mApps.get(i).setIsRun(false);
                            }
                            mListView.setAdapter(mAdapter);
                            SpinnerAdapter.flags_position = -1;
                            break;
                    }

                    if(!AppList.isEmpty())storageData();
                }
            });
        }
    };

    public static boolean isStartAccessibilityService(Context context, String name){
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> serviceInfos = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : serviceInfos)
        {
            if (info.getId().contains(name)) {
                return true;
            }
        } return false;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_teach);
        /*
        TextView tv_Next = findViewById(R.id.tv_next);
        tv_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_teach==-1) {
                    ImageView iv_step = findViewById(R.id.iv_teachstep);
                    iv_step.setImageResource(R.drawable.teachstep1);
                    flag_teach = 0;
                }else
                if(flag_teach==0) {
                    setContentView(R.layout.activity_main);
                }
            }
        });
        */
        //if(flag_teach==0) {
            setContentView(R.layout.activity_main);
            if (!isStartAccessibilityService(this, "monitor") && AppList.isEmpty()) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }

            mListView = findViewById(R.id.lv_monitor);

            if (AppList.isEmpty()) loadAppInfomation(this);

            readData();
            if (!pStoreInfo.isEmpty()) AppList = infoToApp(pStoreInfo);

            mApps = getSelectList(AppList);
            mAdapter = new AppAdapter(this, mApps);
            timer0.schedule(task0, 0, 1000);

            spinner = findViewById(R.id.sp_tool);
            SpAdapter = new SpinnerAdapter(this, mToolicon, mStrList);
            spinner.setAdapter(SpAdapter);
        //}
    }

    private void loadAppInfomation(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        Collections.sort(infos, new ResolveInfo.DisplayNameComparator(pm));
        if(infos != null) {
            AppList.clear();
            for(int i=0; i<infos.size(); i++) {
                ResolveInfo info = infos.get(i);
                Application app = new Application(info.loadLabel(pm).toString(),info.loadIcon(pm),
                        new ComponentName(info.activityInfo.packageName, info.activityInfo.name),
                        false,0,0,"",false);
                AppList.add(app);
            }
        }
    }

    private List<Application> getSelectList(List<Application> AppList) {
        List<Application> selectList = new ArrayList<Application>();
        for(int i=0;i<AppList.size();i++)
        {
            if(AppList.get(i).getSelected())
            {
                selectList.add(AppList.get(i));
            }
        }
        return selectList;
    }

    private List<StoreInfo> appToInfo(List<Application> AppList){
        pStoreInfo.clear();
        for(int i=0;i<AppList.size();i++)
        {
            StoreInfo info = new StoreInfo(AppList.get(i).getName(),
                    AppList.get(i).getIsRun(),
                    AppList.get(i).getRuntime(),
                    AppList.get(i).getLimiTime(),
                    AppList.get(i).getTips(),
                    AppList.get(i).getSelected());
            pStoreInfo.add(info);
        }
        return pStoreInfo;
    }

    private List<Application> infoToApp(List<StoreInfo> StoreInfo){
        for(int i=0;i<AppList.size();i++)
        {
            AppList.get(i).setIsRun(StoreInfo.get(i).getIsRun());
            AppList.get(i).setRuntime(StoreInfo.get(i).getRuntime());
            AppList.get(i).setLimiTime(StoreInfo.get(i).getLimiTime());
            AppList.get(i).setTips(StoreInfo.get(i).getTips());
            AppList.get(i).setSelected(StoreInfo.get(i).getSelected());
        }
        return AppList;
    }

    public void storageData(){
        FileOutputStream fos = null;
        pStoreInfo = appToInfo(AppList);
        try {
            fos=openFileOutput("application.txt", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String jsonstr = gson.toJson(pStoreInfo);
            fos.write(jsonstr.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readData(){
        FileInputStream fis=null;
        try {
            Gson gson = new Gson();
            fis=openFileInput("application.txt");
            byte[] outByte=new byte[fis.available()];
            fis.read(outByte);
            if(!new String(outByte).equals("[]")&&!new String(outByte).equals("")){
                pStoreInfo = gson.fromJson(new String(outByte), new TypeToken<List<StoreInfo>>(){}.getType());
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSpinner(View view){
        spinner.performClick();
    }

    @Override
    public void finish() {
        super.finish();
        if(isTaskRoot()){
            storageData();
        }
    }
}

