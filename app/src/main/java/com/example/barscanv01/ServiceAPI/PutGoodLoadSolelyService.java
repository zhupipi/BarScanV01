package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/31.
 */

public interface PutGoodLoadSolelyService {
    @FormUrlEncoded
    @POST("outOrderController.do?putGoodLoadedSolelybyPDA")
    Call<ResponseBody> putGoodLoadSoley(@Field("id") String id, @Field("goodId") String goodId, @Field("detailId") String detailId, @Field("scanUserName") String scanUserName, @Field("areaName") String areaName, @Field("depotName") String depotName);
}
