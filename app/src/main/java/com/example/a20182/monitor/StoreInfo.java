package com.example.a20182.monitor;

/*
 *The Class specially used for store the information in location
 * Donnot directly use the Application Class because some variables cannot be transformed to
 * json file(Drawable, ComponentName)
 */

public class StoreInfo {
    private String name;
    private boolean isRun;
    private int runtime;
    private int limitime;
    private String tips;
    private boolean isSelected;

    //无参构造函数
    public StoreInfo(){}

    //有参构造函数
    public StoreInfo(String name, boolean isRun,
                       int runtime, int limitime, String tips, boolean isSelected){
        this.name = name;
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
