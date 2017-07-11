package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/6.
 */

public interface UpdatePositionService {
    @FormUrlEncoded
    @POST("goodsBarcodeController.do?updateGoodDepotforPDA")
    Call<ResponseBody> updatePosition(@Field("id") String id,@Field("positionNo") String positionNo);
}
