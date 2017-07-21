package com.example.barscanv01.Util;

import com.example.barscanv01.Bean.OutOrderDetailBean;

import java.util.Comparator;

/**
 * Created by zhupipi on 2017/7/20.
 */

public class OutOrderDetailComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        OutOrderDetailBean detail1 = (OutOrderDetailBean) o1;
        OutOrderDetailBean detail2 = (OutOrderDetailBean) o2;
        if (!detail1.getFinishStatus().equals(detail2.getFinishStatus())) {
            return detail1.getFinishStatus().compareTo(detail2.getFinishStatus());
        } else {
            if (!detail1.getSpecificationModel().equals(detail2.getSpecificationModel())) {
                return detail1.getSpecificationModel().compareTo(detail2.getSpecificationModel());
            } else {
                return detail1.getCustomerCode().compareTo(detail2.getCustomerCode());
            }
        }
    }
}
