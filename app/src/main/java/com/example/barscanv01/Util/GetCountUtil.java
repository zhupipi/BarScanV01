package com.example.barscanv01.Util;

import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;

import java.util.List;

/**
 * Created by zhulin on 2017/7/27.
 */

public class GetCountUtil {
    private List<OutOrderDetailBean> detailList;
    private GoodsBarcodeBean good;
    public GetCountUtil(List<OutOrderDetailBean> detailList,GoodsBarcodeBean good){
        this.detailList=detailList;
        this.good=good;
    }
    public float getCount(){
        float count=0;
        for(OutOrderDetailBean detail:detailList){
            if(detail.getSpecificationModel().equals(good.getSpecificationModel())) {
                if (detail.getFinishStatus().equals("0")) {
                    count = count + detail.getCount();
                }
            }
        }
        return count;
    }
    public float getActCount(){
        float actCount=0;
        for(OutOrderDetailBean detail:detailList){
            if(detail.getSpecificationModel().equals(good.getSpecificationModel())){
                if(detail.getFinishStatus().equals("0")){
                    if(detail.getActCount()!=null){
                        actCount=actCount+Float.valueOf(detail.getActCount());
                    }else {
                        actCount=actCount+0;
                    }
                }
            }
        }
        return actCount;
    }
    public float getResultCount(List<GoodsBarcodeBean> resultList){
        float resultCount=0;
        for(GoodsBarcodeBean goodsBarcode:resultList){
            if(goodsBarcode.getSpecificationModel().equals(good.getSpecificationModel())){
                resultCount=resultCount+1;
            }
        }
        return resultCount;
    }
}
