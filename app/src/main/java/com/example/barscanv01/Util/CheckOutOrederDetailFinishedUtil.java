package com.example.barscanv01.Util;

import android.app.Activity;
import android.widget.Toast;

import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.ReceivedCheckOutOrderFinishedInfo;
import com.example.barscanv01.SaleLoad.DeliveryBillSingleton;
import com.example.barscanv01.ServiceAPI.CheckOrderFinishedService;
import com.example.barscanv01.ServiceAPI.OutOrderProcessService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhupipi on 2017/7/12.
 */

public class CheckOutOrederDetailFinishedUtil {
    private OutOrderBean outOrder;
    private Activity activity;

    public CheckOutOrederDetailFinishedUtil(OutOrderBean outOrder,Activity activity){
        this.outOrder=outOrder;
        this.activity=activity;
    }
    public void checkOutOrderFinished(){
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        CheckOrderFinishedService checkOutOrderFinishedService=retrofit.create(CheckOrderFinishedService.class);
        Call<ReceivedCheckOutOrderFinishedInfo> call2=checkOutOrderFinishedService.checkOutOrderFinished(outOrder.getId());
        call2.enqueue(new Callback<ReceivedCheckOutOrderFinishedInfo>() {
            @Override
            public void onResponse(Call<ReceivedCheckOutOrderFinishedInfo> call, Response<ReceivedCheckOutOrderFinishedInfo> response) {
                boolean result=response.body().getAttributes().isCheckResult();
                if(result){
                    outOrderFinised();
                }
            }

            @Override
            public void onFailure(Call<ReceivedCheckOutOrderFinishedInfo> call, Throwable t) {

            }
        });
        //WriteBizlogUtil writeBizlogUtil=new WriteBizlogUtil(detail,activity);
        //writeBizlogUtil.writeOutOrderFinishedLog();
        //AreaInOutUpdateUtil areaInOutUpdate=new AreaInOutUpdateUtil(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo(),"6");

    }

    public void outOrderFinised() {
        Retrofit retrofit1 = new RetrofitBuildUtil().getRetrofit();
        OutOrderProcessService outOrderProcessService = retrofit1.create(OutOrderProcessService.class);
        Call<ResponseBody> call3 = outOrderProcessService.updateProcess(outOrder.getId(), "5");
        call3.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                AreaInOutUpdateUtil areaInOutUpdate = new AreaInOutUpdateUtil(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo(), "6");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        Toast.makeText(activity, "该发货单装车完成", Toast.LENGTH_SHORT).show();
        WriteBizlogUtil writeBizlog=new WriteBizlogUtil(activity);
        writeBizlog.writeOutOrderFinishedLog();
    }


}
