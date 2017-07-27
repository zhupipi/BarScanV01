package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedDetailTotalWeightBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/7/27.
 */

public interface GetOrderDetailWeightService {
    @FormUrlEncoded
    @POST("detailBarcodeController.do?doGetDetailWeightforPDA")
    Call<ReceivedDetailTotalWeightBean> getTotalWeight(@Field("orderId") String orderId);
}
