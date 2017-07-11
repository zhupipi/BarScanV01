package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedGoodsBarcodeInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/3.
 */

public interface ScanBarcodeResultService {
    @FormUrlEncoded
    @POST("goodsBarcodeController.do?getGoodsBarcodeforPDAbyBarcode")
    Call<ReceivedGoodsBarcodeInfo> getGoodsBarcode(@Field("barcode") String barcode);
}
