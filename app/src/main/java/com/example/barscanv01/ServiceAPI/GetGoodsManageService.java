package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedGoodsManageInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/21.
 */

public interface GetGoodsManageService {
    @POST("goodsmanageController.do?getListforPDA")
    Call<ReceivedGoodsManageInfo> getGoodList();
}
