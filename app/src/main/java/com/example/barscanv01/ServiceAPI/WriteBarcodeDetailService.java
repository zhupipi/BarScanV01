package com.example.barscanv01.ServiceAPI;

import java.math.BigDecimal;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/5.
 */

public interface WriteBarcodeDetailService {
    @FormUrlEncoded
    @POST("detailBarcodeController.do?doAdd")
    Call<ResponseBody> addBarcodeDetail(@FieldMap Map<String,String> map1, @Field("weight") BigDecimal weight, @Field("scanDate") String date);
}
