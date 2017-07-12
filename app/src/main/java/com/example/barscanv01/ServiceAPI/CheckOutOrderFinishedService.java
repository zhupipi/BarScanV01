package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedCheckOutOrderFinishedInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/12.
 */

public interface CheckOutOrderFinishedService {
    @FormUrlEncoded
    @POST("outOrderController.do?checkOutOrderFinishedforPDA")
    Call<ReceivedCheckOutOrderFinishedInfo> checkOutOrderFinished(@Field("id") String id);
}
