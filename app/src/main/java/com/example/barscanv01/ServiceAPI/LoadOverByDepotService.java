package com.example.barscanv01.ServiceAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/7/26.
 */

public interface LoadOverByDepotService {
    @FormUrlEncoded
    @POST("outOrderController.do?loadOverbyDepotforPDA")
    Call<ResponseBody> loadedByDepot(@Field("id") String id,@Field("depotNo") String depotNo);
}
