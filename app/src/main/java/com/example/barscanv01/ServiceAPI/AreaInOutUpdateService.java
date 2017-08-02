package com.example.barscanv01.ServiceAPI;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/11.
 */

public interface AreaInOutUpdateService {
    //需要增加库位
    @FormUrlEncoded
    @POST("areaInController.do?doUpdatebyPDA")
    Call<ResponseBody> updateService(@Field("plateNo") String plateNo, @Field("status") String status);
    @FormUrlEncoded
    @POST("areaInController.do?doUpdatebyPDA")
    Call<ResponseBody> scanedUpdateService(@Field("plateNo") String plateNo, @Field("status") String status,@Field(("depotNo")) String depotNo,@Field("areaNo") String areaNo);
}
