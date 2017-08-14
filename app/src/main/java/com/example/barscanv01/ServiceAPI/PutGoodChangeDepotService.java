package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/14.
 */

public interface PutGoodChangeDepotService {
    @FormUrlEncoded
    @POST("goodsBarcodeController.do?putGoodChangeDepotforPDA")
    Call<ResponseBody> changeDepot(@Field("goodIds") String goodIds,@Field("depotNo") String depotNo, @Field("positionNo") String positionNo);
}
