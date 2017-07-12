package com.example.barscanv01.Util;

import android.app.Activity;
import android.widget.Toast;

import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedOutOrderDetailInfo;
import com.example.barscanv01.ServiceAPI.GetOutOrderDetailByIdService;
import com.example.barscanv01.ServiceAPI.OutOrderDetailProcessService;

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
    private OutOrderBean outOrder;
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

}
