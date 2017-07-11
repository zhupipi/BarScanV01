package com.example.barscanv01.SaleLoad;

import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupipi on 2017/6/30.
 */

public class DeliveryBillSingleton {
    public String lastCustomerCode;
    public String lastCustomerName;

    public String getLastCustomerCode() {
        return lastCustomerCode;
    }

    public void setLastCustomerCode(String lastCustomerCode) {
        this.lastCustomerCode = lastCustomerCode;
    }

    public String getLastCustomerName() {
        return lastCustomerName;
    }

    public void setLastCustomerName(String lastCustomerName) {
        this.lastCustomerName = lastCustomerName;
    }

    public OutOrderBean outOrderBean;

    public OutOrderBean getOutOrderBean() {
        return outOrderBean;
    }


    public void setOutOrderBean(OutOrderBean outOrderBean) {
        this.outOrderBean = outOrderBean;
    }

    public List<OutOrderDetailBean> getOutOrderDetailBean() {
        return outOrderDetailBean;
    }

    public void setOutOrderDetailBean(List<OutOrderDetailBean> outOrderDetailBean) {
        this.outOrderDetailBean = outOrderDetailBean;
    }

    public List<OutOrderDetailBean> outOrderDetailBean;

    private static DeliveryBillSingleton instance = null;

    private DeliveryBillSingleton(){
        outOrderBean=new OutOrderBean();
        outOrderDetailBean=new ArrayList<OutOrderDetailBean>();
        lastCustomerCode=null;
        lastCustomerName=null;
    }

    public static DeliveryBillSingleton getInstance() {
        synchronized (DeliveryBillSingleton.class) {
            if (instance == null) {
                instance = new DeliveryBillSingleton();
            }
        }

        return instance;
    }
}
