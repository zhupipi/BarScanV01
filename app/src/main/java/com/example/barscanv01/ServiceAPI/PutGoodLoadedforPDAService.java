package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/7/28.
 */

public interface PutGoodLoadedforPDAService {
    @FormUrlEncoded
    @POST("outOrderController.do?putGoodLoadforPDA")
    Call<ResponseBody> putGoodsLoaded(@Field("id") String id,@Field("goodIds") String ids,@Field("areaNo") String areaNo,@Field("scanUserName") String userName);
}
