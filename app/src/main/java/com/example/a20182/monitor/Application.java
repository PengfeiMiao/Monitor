package com.example.a20182.monitor;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

/**
 * Created by 20182 on 2018/3/30.
 */

public class Application {
    private String name;
    private Drawable icon;
    private ComponentName intent;
    private boolean isRun = false;
    private int runtime;
    private int limitime = 0;
    private String tips = "";
    private boolean isSelected;

    //无参构造函数
    public Application() {
    }

    //有参构造函数
    public Application(String name, Drawable icon, ComponentName intent, int runtime, boolean isSelected) {
        this.name = name;
        this.icon = icon;
        this.intent = intent;
        this.runtime = runtime;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public ComponentName getIntent() {
        return intent;
    }

    public void setIntent(ComponentName intent) {
        this.intent = intent;
    }

    public int getLimiTime() {
        return limitime;
    }

    public void setLimiTime(int limitime) {
        this.limitime = limitime;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getIsRun() {
        return isRun;
    }

    public void setIsRun(boolean isRun) {
        this.isRun = isRun;
    }
}
