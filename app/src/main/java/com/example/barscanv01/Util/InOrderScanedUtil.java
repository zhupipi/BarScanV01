package com.example.barscanv01.Util;

import android.app.Activity;
import android.widget.Toast;

import com.example.barscanv01.Bean.InOrderBean;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.ServiceAPI.AreaInOutUpdateService;
import com.example.barscanv01.ServiceAPI.UpdateInOrderProcessService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhulin on 2017/8/5.
 */

public class InOrderScanedUtil {
    private InOrderBean inOrder;
    private Activity activity;
    private MyApp myApp;

    public InOrderScanedUtil(InOrderBean inOrder, Activity activity) {
        this.inOrder = inOrder;
        this.activity = activity;
        myApp = (MyApp) activity.getApplication();
    }

    public void upDateInOrder() {
        if (inOrder.getProcess().equals("3") && inOrder.getProcess().equals("4")) {
            String id = inOrder.getId();
            Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
            UpdateInOrderProcessService updateInOrderProcessService = retrofit.create(UpdateInOrderProcessService.class);
            Call<ResponseBody> call = updateInOrderProcessService.updateInOrderProcess(id, "4");
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

    public void upDateAreaInOut() {
        if (!inOrder.getProcess().equals("5")) {
            Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
            AreaInOutUpdateService areaInOutUpdateService = retrofit.create(AreaInOutUpdateService.class);
            Call<ResponseBody> call = areaInOutUpdateService.scanedUpdateService(inOrder.getPlateNo(), "5", myApp.getCurrentDepot().getDepotNo(), myApp.getCurrentAreaBean().getAreaNo());
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
}
