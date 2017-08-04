package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedCarResonInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/3.
 */

public interface GetCarResonService {
    /**
     * GetCarReson 是通过车牌照获取车辆进场事由，返回reson，10销售装车，20卸货
     */
    @FormUrlEncoded
    @POST("areaInController.do?checkResonbyPDA")
    Call<ReceivedCarResonInfo> getReson(@Field("plateNo") String plateNo);
}
