package com.example.barscanv01.SaleLoad;

import com.example.barscanv01.Bean.DetailBarcodeBean;
import com.example.barscanv01.Bean.GoodsManageDetailBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupipi on 2017/6/30.
 */

public class DeliveryBillSingleton {
    public OutOrderBean outOrderBean;

    public List<OutOrderDetailBean> outOrderDetailBean;

    public List<GoodsManageDetailBean> goodsManageDetailList;

    // public List<DetailBarcodeBean> loadedDetailBarcodeList;

    private static DeliveryBillSingleton instance = null;

    private DeliveryBillSingleton() {
        outOrderBean = new OutOrderBean();
        outOrderDetailBean = new ArrayList<OutOrderDetailBean>();
        goodsManageDetailList = new ArrayList<GoodsManageDetailBean>();
        //loadedDetailBarcodeList = new ArrayList<DetailBarcodeBean>();
    }

    public static DeliveryBillSingleton getInstance() {
        synchronized (DeliveryBillSingleton.class) {
            if (instance == null) {
                instance = new DeliveryBillSingleton();
            }
        }

        return instance;
    }

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

    public List<GoodsManageDetailBean> getGoodsManageDetailList() {
        return goodsManageDetailList;
    }

    public void setGoodsManageDetailList(List<GoodsManageDetailBean> goodsManageDetailList) {
        this.goodsManageDetailList = goodsManageDetailList;
    }

}
