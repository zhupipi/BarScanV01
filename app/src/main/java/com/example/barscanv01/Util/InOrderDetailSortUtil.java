package com.example.barscanv01.Util;

import android.app.Activity;

import com.example.barscanv01.Bean.AreaBean;
import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.InOrderDetailBean;
import com.example.barscanv01.MyApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhulin on 2017/8/15.
 */

public class InOrderDetailSortUtil {
    private ArrayList<InOrderDetailBean> initalInOrderDetails;
    private ArrayList<InOrderDetailBean> finalInOrderDetails;
    private MyApp myApp;
    public InOrderDetailSortUtil(List<InOrderDetailBean> inOrderDetailList, Activity activity){
        this.initalInOrderDetails= (ArrayList<InOrderDetailBean>) inOrderDetailList;
        finalInOrderDetails=new ArrayList<InOrderDetailBean>();
        myApp= (MyApp) activity.getApplication();
        sortbyAreaandDepot(myApp.getCurrentAreaBean(),myApp.getCurrentDepot());
    }

    private void sortbyAreaandDepot(AreaBean currentAreaBean, DepotBean currentDepot) {
        ArrayList<InOrderDetailBean> temp = new ArrayList<InOrderDetailBean>();
        ArrayList<InOrderDetailBean> removeTemp = new ArrayList<InOrderDetailBean>();
        if (initalInOrderDetails.size() > 0) {
            for (InOrderDetailBean detail : initalInOrderDetails) {
                if (detail.getAreaNo().equals(currentAreaBean.getAreaNo())) {
                    if (detail.getDepotNo().equals(currentDepot.getDepotNo())) {
                        temp.add(detail);
                    }
                } else {
                    removeTemp.add(detail);
                }
            }
            if (removeTemp.size() > 0) {
                for (InOrderDetailBean removeDetail : removeTemp) {
                    initalInOrderDetails.remove(removeDetail);
                }
            }
            if (temp.size() > 0) {
                for (InOrderDetailBean tempDetail : temp) {
                    initalInOrderDetails.remove(tempDetail);
                }
            }
            finalInOrderDetails = temp;
            if(initalInOrderDetails.size()>0) {
                finalInOrderDetails.addAll(initalInOrderDetails);
            }
        }
    }

    public ArrayList<InOrderDetailBean> getFinalInOrderDetails() {
        return finalInOrderDetails;
    }
}
