package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedOutOrderDetailInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/12.
 */

public interface GetOutOrderDetailByIdService {
    @FormUrlEncoded
    @POST("outOrderController.do?getOutOrderDetailforPDAbyId")
    Call<ReceivedOutOrderDetailInfo> getOutOrderDetail(@Field("id") String id);
}
