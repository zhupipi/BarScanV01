package com.example.barscanv01;

import android.app.Activity;
import android.app.Application;

import com.example.barscanv01.Bean.AreaBean;
import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupipi on 2017/6/25.
 */

public class MyApp extends Application{
    private UserBean userBean;
    private List<DepotBean> depotlist;
    private DepotBean currentDepot;
    private AreaBean currentAreaBean;
    private List<Activity>  activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        userBean=new UserBean();
        depotlist=new ArrayList<DepotBean>();
        currentDepot=new DepotBean();
        activityList=new ArrayList<Activity>();
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

    public void setCurrentAreaBean(AreaBean currentAreaBean) {
        this.currentAreaBean = currentAreaBean;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }
    public void exit(){
        for(Activity activity:activityList){
            if(activity!=null){
                activity.finish();
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
