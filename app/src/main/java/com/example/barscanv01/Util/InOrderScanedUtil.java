package com.example.barscanv01.Util;

import com.example.barscanv01.Bean.InOrderBean;
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
    public InOrderScanedUtil(InOrderBean inOrder){
        this.inOrder=inOrder;
    }
    public void upDateInOrder(){
        String id=inOrder.getId();
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        UpdateInOrderProcessService updateInOrderProcessService=retrofit.create(UpdateInOrderProcessService.class);
        Call<ResponseBody> call=updateInOrderProcessService.updateInOrderProcess(id,"4");
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
