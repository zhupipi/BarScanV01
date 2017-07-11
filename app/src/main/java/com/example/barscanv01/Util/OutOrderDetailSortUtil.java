package com.example.barscanv01.Util;

import android.app.Activity;
import android.content.Context;

import com.example.barscanv01.Bean.AreaBean;
import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.MyApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupipi on 2017/7/10.
 */

public class OutOrderDetailSortUtil {
    private ArrayList<OutOrderDetailBean> initalOutOrderDetails;
    private ArrayList<OutOrderDetailBean> finalOutOrderDetails;
    private MyApp myApp;
    public OutOrderDetailSortUtil(List<OutOrderDetailBean> initalOutOrderDetails, Activity activity){
        this.initalOutOrderDetails= (ArrayList<OutOrderDetailBean>) initalOutOrderDetails;
        finalOutOrderDetails=new ArrayList<OutOrderDetailBean>();
        myApp=(MyApp) activity.getApplication();
        sortbyAreaandDepot(myApp.getCurrentAreaBean(),myApp.getCurrentDepot());
    }

    private void sortbyAreaandDepot(AreaBean currentAreaBean, DepotBean currentDepot) {
        ArrayList<OutOrderDetailBean> temp=new ArrayList<OutOrderDetailBean>();
        ArrayList<OutOrderDetailBean> removeTemp=new ArrayList<OutOrderDetailBean>();
        if(initalOutOrderDetails.size()>0) {
            for (OutOrderDetailBean detail : initalOutOrderDetails){
                if(detail.getAreaNo().equals(currentAreaBean.getAreaNo())){
                    if(detail.getDepotNo().equals(currentDepot.getDepotNo())){
                        temp.add(detail);
                    }
                }else{
                    removeTemp.add(detail);
                }
            }
            if(removeTemp.size()>0){
                for(OutOrderDetailBean removeDetail:removeTemp){
                    initalOutOrderDetails.remove(removeDetail);
                }
            }
            if(temp.size()>0){
                for(OutOrderDetailBean tempDetail:temp){
                    initalOutOrderDetails.remove(tempDetail);
                }
            }
            finalOutOrderDetails=temp;
            if(initalOutOrderDetails.size()>0) {
                finalOutOrderDetails.addAll(initalOutOrderDetails);
            }
        }
    }

    public ArrayList<OutOrderDetailBean> getFinalOutOrderDetails() {
        return finalOutOrderDetails;
    }
}
