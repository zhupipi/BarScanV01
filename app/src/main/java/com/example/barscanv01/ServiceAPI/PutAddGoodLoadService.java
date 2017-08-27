package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/27.
 */

public interface PutAddGoodLoadService {
    @FormUrlEncoded
    @POST("outOrderController.do?putAddGoodLoadforPDA")
    Call<ResponseBody> putAddGoodLoad(@Field("id") String id,@Field("goodIds") String ids,@Field("customerCode") String customerCode,@Field("customerName") String customerName,@Field("customerAddress") String address,@Field("scanUserName") String userName);
}
