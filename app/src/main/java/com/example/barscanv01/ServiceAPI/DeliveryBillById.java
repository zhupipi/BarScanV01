package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedDelivieryBillInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/2.
 */

public interface DeliveryBillById {
    @FormUrlEncoded
    @POST("outOrderController.do?getOutOrderforPDAbyId")
    Call<ReceivedDelivieryBillInfo> getDeliveryBillById(@Field("id") String id);
}
