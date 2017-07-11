package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/4.
 */

    public interface PutGoodLoadedService {
    @FormUrlEncoded
    @POST("goodsBarcodeController.do?putGoodLoadedforPDA")
    Call<ResponseBody> putGoodLoaded(@Field("id") String id);
    @FormUrlEncoded
    @POST("outOrderController.do?updateOutOrderDetailActCountforPDA")
    Call<ResponseBody> updateActCount(@Field("id") String id,@Field("act_weight") String actWeight,@Field("total_count") String count);
}
