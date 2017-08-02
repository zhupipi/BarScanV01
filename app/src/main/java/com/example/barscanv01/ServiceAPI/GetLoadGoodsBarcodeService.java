package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedLoadGoodsBarcodeInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/2.
 */

public interface GetLoadGoodsBarcodeService {
    @FormUrlEncoded
    @POST("detailBarcodeController.do?doGetDetailGoodsBarcodesforPDA")
    Call<ReceivedLoadGoodsBarcodeInfo> getLoadedGoods(@Field("orderId") String orderId);
}
