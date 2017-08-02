package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/1.
 */

public interface PutGoodLoadNoDetailService {
    @FormUrlEncoded
    @POST("outOrderController.do?putGoodLoadNoDetailforPDA")
    Call<ResponseBody> putGoodLoadNoDetail(@Field("id") String id,@Field("goodIds") String Ids,@Field("areaNo") String areaNo,@Field("scanUserName") String userName);
}
