package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/9/7.
 */

public interface PutSticksLoadedService {
    @FormUrlEncoded
    @POST("outOrderController.do?putSticksLoadforPDA")
    Call<ResponseBody> putStickLoad(@Field("id") String id,@Field("detailId") String detailId,@Field("number") int number,@Field("scanUserName") String scanUserName);
}
