package com.example.barscanv01.Util;

import com.example.barscanv01.ServiceAPI.AreaInOutUpdateService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhupipi on 2017/7/17.
 */

public class AreaInOutUpdateUtil {

    public AreaInOutUpdateUtil(String carPlate,String status){
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        AreaInOutUpdateService areaInOutUpdateService=retrofit.create(AreaInOutUpdateService.class);
        Call<ResponseBody> call=areaInOutUpdateService.updateService(carPlate,status);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
