package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/4.
 */

public interface PutGoodsUnLoadService {
    @FormUrlEncoded
    @POST("inOrderController.do?putGoodUnLoadforPDA")
    Call<ResponseBody> putGoodsUnload(@Field("id") String id,@Field("outOrderNo") String outOrderNo,@Field("goodIds") String goodids,@Field("userName") String userName);
}