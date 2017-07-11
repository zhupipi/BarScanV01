package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/6.
 */

public interface OutOrderProcessService {
    @FormUrlEncoded
    @POST("outOrderController.do?updateProcessforPDA")
    Call<ResponseBody> updateProcess(@Field("id") String id ,@Field("process") String process);
}
