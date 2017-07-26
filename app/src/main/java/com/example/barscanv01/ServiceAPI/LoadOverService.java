package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/7/26.
 */

public interface LoadOverService {
    @FormUrlEncoded
    @POST("outOrderController.do?loadOverforPDA")
    Call<ResponseBody> loadOver(@Field("id") String id);
}
