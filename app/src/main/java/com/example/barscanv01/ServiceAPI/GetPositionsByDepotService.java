package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedPositionInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/6.
 */

public interface GetPositionsByDepotService {
    @FormUrlEncoded
    @POST("positionController.do?findPositionsByDepotforPDA")
    Call<ReceivedPositionInfo> getPositions(@Field("id") String depot_id);
}
