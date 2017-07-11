package com.example.barscanv01.Util;

import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.ServiceAPI.AreaInOutUpdateService;
import com.example.barscanv01.ServiceAPI.OutOrderProcessService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhupipi on 2017/7/10.
 */

public class OutOrderScanedUtil {
    public OutOrderBean outOrder;
    public OutOrderScanedUtil(OutOrderBean outOrder){
        this.outOrder=outOrder;
    }
    public void updateOutOrderProcess(){
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        OutOrderProcessService outOrderProcessService=retrofit.create(OutOrderProcessService.class);
        Call<ResponseBody> call=outOrderProcessService.updateProcess(outOrder.getId(),"4");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void updateAreaInOut(){
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        AreaInOutUpdateService areaInOutUpdateService=retrofit.create(AreaInOutUpdateService.class);
        Call<ResponseBody>call=areaInOutUpdateService.updateService(outOrder.getPlateNo(),"5");
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
