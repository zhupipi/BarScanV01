package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/11.
 */

public interface AreaInOutUpdateService {
    @FormUrlEncoded
    @POST("areInController.do?doUpdatebyPDA")
    Call<ResponseBody> updateService(@Field("plateNo") String plateNo,@Field("status") String status);
}
