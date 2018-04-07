package com.example.a20182.monitor;

/**
 *The Class to discribe the application object,
 * use which to record the information.
 * Include some origin info and other new info used for the App Running.
 */

import android.content.ComponentName;
import android.graphics.drawable.Drawable;

public class Application {
    private String name;
    private Drawable icon;
    private ComponentName intent;
    private boolean isRun;
    private int runtime;
    private int limitime;
    private String tips;
    private boolean isSelected;

    //无参构造函数
    public Application(){}

    //有参构造函数
    public Application(String name,Drawable icon,ComponentName intent,boolean isRun,
                       int runtime,int limitime,String tips,boolean isSelected){
        this.name = name;
        this.icon = icon;
        this.intent = intent;
        this.isRun = isRun;
        this.runtime = runtime;
        this.limitime = limitime;
        this.tips = tips;
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

    public ComponentName getIntent () {
        return intent;
    }

    public void setIntent (ComponentName intent) {
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
