package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ParamBean;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * Created by zhulin on 2017/12/30.
 */

public interface GetParamService {
    @GET("outOrderController.do?getWeightingParamforPDA")
    Call<ParamBean> getParam();
}
