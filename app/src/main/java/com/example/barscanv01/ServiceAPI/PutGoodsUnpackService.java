package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/9/18.
 */

public interface PutGoodsUnpackService {
    @FormUrlEncoded
    @POST("goodsBarcodeController.do?putGoodUnPackedforPDA")
    Call<ResponseBody> putGoodsUnpacked(@Field("status") String status,@Field("goodIds") String goodsIds);
}
