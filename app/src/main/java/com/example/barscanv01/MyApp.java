package com.example.barscanv01;

import android.app.Activity;
import android.app.Application;
import android.os.Build;

import com.example.barscanv01.Bean.AreaBean;
import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupipi on 2017/6/25.
 */

public class MyApp extends Application {
    private UserBean userBean;
    private List<DepotBean> depotlist;
    private List<AreaBean> arealist;
    private DepotBean currentDepot;
    private AreaBean currentAreaBean;
    private List<Activity> activityList;
    private String deviceBrand;
    private float param;

    @Override
    public void onCreate() {
        super.onCreate();
        userBean = new UserBean();
        depotlist = new ArrayList<DepotBean>();
        arealist = new ArrayList<AreaBean>();
        currentDepot = new DepotBean();
        activityList = new ArrayList<Activity>();
        deviceBrand = Build.BRAND;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public List<DepotBean> getDepotlist() {
        return depotlist;
    }

    public void setDepotlist(List<DepotBean> depotlist) {
        this.depotlist = depotlist;
    }

    public DepotBean getCurrentDepot() {
        return currentDepot;
    }

    public void setCurrentDepot(DepotBean currentDepot) {
        this.currentDepot = currentDepot;
    }

    public AreaBean getCurrentAreaBean() {
        return currentAreaBean;
    }

    public List<AreaBean> getArealist() {
        return arealist;
    }

    public void setArealist(List<AreaBean> arealist) {
        this.arealist = arealist;
    }

    public void setCurrentAreaBean(AreaBean currentAreaBean) {
        this.currentAreaBean = currentAreaBean;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public float getParam() {
        return param;
    }

    public void setParam(float param) {
        this.param = param;
    }
}
