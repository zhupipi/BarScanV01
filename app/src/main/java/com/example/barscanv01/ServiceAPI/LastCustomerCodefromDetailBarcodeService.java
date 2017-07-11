package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedLastCustomerCodeInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/10.
 */

public interface LastCustomerCodefromDetailBarcodeService {
    @FormUrlEncoded
    @POST("detailBarcodeController.do?doGetDetailBarcodesforPDA")
    Call<ReceivedLastCustomerCodeInfo> getlastCustomerCode(@Field("orderId") String orderId);
}
