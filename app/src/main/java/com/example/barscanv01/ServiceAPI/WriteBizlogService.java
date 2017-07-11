package com.example.barscanv01.ServiceAPI;

import java.util.Date;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/11.
 */

public interface WriteBizlogService {
    @FormUrlEncoded
    @POST("areaInController.do?saveBizLogforPDA")
    Call<ResponseBody> writeBizlog(@FieldMap Map<String,String> map1, @Field("date") String date);
}
