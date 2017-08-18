package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedDetailBarcodeInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/4.
 */

public interface GetDetailBarcodeService {
    @FormUrlEncoded
    @POST("detailBarcodeController.do?doGetDetailBarcodesforPDAbyOrderNo")
    Call<ReceivedDetailBarcodeInfo> getDetailBarcodes(@Field("orderNo") String orderNo);

    @FormUrlEncoded
    @POST("detailBarcodeController.do?doGetDetailBarcodesforPDA")
    Call<ReceivedDetailBarcodeInfo> getDetailBarcodesById(@Field("orderId") String orderId);
}
