package com.example.barscanv01.Unload;

import com.example.barscanv01.Bean.InOrderBean;
import com.example.barscanv01.Bean.InOrderDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhulin on 2017/8/3.
 */

public class InOrderSingleton {
    public InOrderBean inOrder;
    public List<InOrderDetailBean> inOrderDetailList;

    private static InOrderSingleton instance = null;

    private InOrderSingleton() {
        inOrder=new InOrderBean();
        inOrderDetailList=new ArrayList<InOrderDetailBean>();
    }

    public static InOrderSingleton getInstance() {
        synchronized (InOrderSingleton.class) {
            if (instance == null) {
                instance = new InOrderSingleton();
            }
        }
        return instance;
    }

    public InOrderBean getInOrder() {
        return inOrder;
    }

    public void setInOrder(InOrderBean inOrder) {
        this.inOrder = inOrder;
    }

    public List<InOrderDetailBean> getInOrderDetailList() {
        return inOrderDetailList;
    }

    public void setInOrderDetailList(List<InOrderDetailBean> inOrderDetailList) {
        this.inOrderDetailList = inOrderDetailList;
    }
}
