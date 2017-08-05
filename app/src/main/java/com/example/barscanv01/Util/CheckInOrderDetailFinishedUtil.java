package com.example.barscanv01.Util;

import android.app.Activity;
import android.widget.Toast;

import com.example.barscanv01.Bean.InOrderBean;
import com.example.barscanv01.Bean.ReceivedInOrderDetailFinishedInfo;
import com.example.barscanv01.ServiceAPI.CheckOrderFinishedService;
import com.example.barscanv01.ServiceAPI.UpdateInOrderProcessService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhulin on 2017/8/5.
 */

public class CheckInOrderDetailFinishedUtil {
    private Activity activity;
    private InOrderBean inOrder;
    public CheckInOrderDetailFinishedUtil(InOrderBean inOrder,Activity activity){
        this.activity=activity;
        this.inOrder=inOrder;
    }
    public void checkOrderFinished(){
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        CheckOrderFinishedService checkOrderFinishedService=retrofit.create(CheckOrderFinishedService.class);
        Call<ReceivedInOrderDetailFinishedInfo> call=checkOrderFinishedService.checkInOrderFinished(inOrder.getId());
        call.enqueue(new Callback<ReceivedInOrderDetailFinishedInfo>() {
            @Override
            public void onResponse(Call<ReceivedInOrderDetailFinishedInfo> call, Response<ReceivedInOrderDetailFinishedInfo> response) {
                boolean result=response.body().getAttributes().getResult();
                if(result){
                    Retrofit retrofit1=new RetrofitBuildUtil().getRetrofit();
                    UpdateInOrderProcessService updateInOrderProcessService=retrofit1.create(UpdateInOrderProcessService.class);
                    Call<ResponseBody> call1=updateInOrderProcessService.updateInOrderProcess(inOrder.getId(),"5");
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(activity,"该卸货单货品卸货完成",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ReceivedInOrderDetailFinishedInfo> call, Throwable t) {

            }
        });
    }
}
