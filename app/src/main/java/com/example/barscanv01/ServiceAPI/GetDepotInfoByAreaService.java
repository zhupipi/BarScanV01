package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceiveDepotByAreaInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/8/14.
 */

public interface GetDepotInfoByAreaService {
    @FormUrlEncoded
    @POST("depotController.do?getDepotbyAreaIdforPDA")
    Call<ReceiveDepotByAreaInfo> getDepot(@Field("areaId") String id);
}
