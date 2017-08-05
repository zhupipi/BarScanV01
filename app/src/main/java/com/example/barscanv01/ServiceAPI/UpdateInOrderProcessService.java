package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/5.
 */

public interface UpdateInOrderProcessService {
    @FormUrlEncoded
    @POST("inOrderController.do?updateInOrderProcessforPDA")
    Call<ResponseBody> updateInOrderProcess(@Field("id") String id,@Field("process") String process);
}
