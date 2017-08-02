package com.example.barscanv01.Util;

import android.app.Activity;

import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.MyApp;
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
    private Activity activity;
    private MyApp myApp;
    public OutOrderBean outOrder;
    public OutOrderScanedUtil(OutOrderBean outOrder,Activity activity){
        this.outOrder=outOrder;
        this.activity=activity;
        myApp= (MyApp) activity.getApplication();
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
        Call<ResponseBody>call=areaInOutUpdateService.scanedUpdateService(outOrder.getPlateNo(),"5",myApp.getCurrentDepot().getDepotNo(),myApp.getCurrentAreaBean().getAreaNo());
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
