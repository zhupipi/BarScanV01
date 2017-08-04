package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedInOrderInfo;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/3.
 */

public interface GetInOrderforPDAbyPlateService {
    @FormUrlEncoded
    @POST("inOrderController.do?getInOrderforPDAbyPlate")
    Call<ReceivedInOrderInfo> getInOrder(@Field("plateNo") String plateNo);
}
