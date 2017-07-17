package com.example.barscanv01.Util;

import android.app.Activity;
import android.widget.Toast;

import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedCheckOutOrderFinishedInfo;
import com.example.barscanv01.Bean.ReceivedOutOrderDetailInfo;
import com.example.barscanv01.SaleLoad.DeliveryBillSingleton;
import com.example.barscanv01.ServiceAPI.CheckOutOrderFinishedService;
import com.example.barscanv01.ServiceAPI.GetOutOrderDetailByIdService;
import com.example.barscanv01.ServiceAPI.OutOrderDetailProcessService;
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
    private OutOrderDetailBean detail;
    private Activity activity;

    public CheckOutOrederDetailFinishedUtil(OutOrderDetailBean detail,Activity activity){
        this.detail=detail;
        this.activity=activity;
    }
    public void checkDetailFinished(){
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        GetOutOrderDetailByIdService getOutOrderDetailByIdService=retrofit.create(GetOutOrderDetailByIdService.class);
        Call<ReceivedOutOrderDetailInfo> call=getOutOrderDetailByIdService.getOutOrderDetail(detail.getId());
        call.enqueue(new Callback<ReceivedOutOrderDetailInfo>() {
            @Override
            public void onResponse(Call<ReceivedOutOrderDetailInfo> call, Response<ReceivedOutOrderDetailInfo> response) {
                OutOrderDetailBean detailGetted=response.body().getAttributes().getOutOrderDetail();
                if((float)detailGetted.getCount()==Float.valueOf(detailGetted.getActCount())){
                    Toast.makeText(activity,detailGetted.getCustomerName()+"的"+detailGetted.getSpecificationModel()+"规格货品已经装车完成",Toast.LENGTH_SHORT).show();
                    Retrofit retrofit = new RetrofitBuildUtil().retrofit;
                    OutOrderDetailProcessService outOrderDetailProcessService = retrofit.create(OutOrderDetailProcessService.class);
                    Call<ResponseBody> call1 = outOrderDetailProcessService.updateProcess(detailGetted.getId());
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                checkOutOrderFinished();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    WriteBizlogUtil writeBizlogUtil=new WriteBizlogUtil(detail,activity);
                    writeBizlogUtil.writeLoadFinishLog();
                }
            }

            @Override
            public void onFailure(Call<ReceivedOutOrderDetailInfo> call, Throwable t) {

            }
        });

    }
    public void checkOutOrderFinished(){
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        CheckOutOrderFinishedService checkOutOrderFinishedService=retrofit.create(CheckOutOrderFinishedService.class);
        Call<ReceivedCheckOutOrderFinishedInfo> call2=checkOutOrderFinishedService.checkOutOrderFinished(detail.getOutOrderId());
        call2.enqueue(new Callback<ReceivedCheckOutOrderFinishedInfo>() {
            @Override
            public void onResponse(Call<ReceivedCheckOutOrderFinishedInfo> call, Response<ReceivedCheckOutOrderFinishedInfo> response) {
                boolean result=response.body().getAttributes().isCheckResult();
                if(result){
                    Retrofit retrofit1=new RetrofitBuildUtil().getRetrofit();
                    OutOrderProcessService outOrderProcessService=retrofit1.create(OutOrderProcessService.class);
                    Call<ResponseBody> call3=outOrderProcessService.updateProcess(detail.getOutOrderId(),"5");
                    call3.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    Toast.makeText(activity,"该发货单装车完成",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedCheckOutOrderFinishedInfo> call, Throwable t) {

            }
        });
        WriteBizlogUtil writeBizlogUtil=new WriteBizlogUtil(detail,activity);
        writeBizlogUtil.writeOutOrderFinishedLog();
        AreaInOutUpdateUtil areaInOutUpdate=new AreaInOutUpdateUtil(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo(),"6");

    }
}
