package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedGoodsManageInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by zhulin on 2017/8/21.
 */

public interface GetGoodsManageDetailService {
    @GET("goodsmanageController.do?getGoodsManageforPDA")
    Call<ReceivedGoodsManageInfo> getGoodsManageDetail();
}
