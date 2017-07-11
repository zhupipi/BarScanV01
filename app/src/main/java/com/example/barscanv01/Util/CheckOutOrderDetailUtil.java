package com.example.barscanv01.Util;

import android.util.Log;

import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedLastCustomerCodeInfo;
import com.example.barscanv01.SaleLoad.DeliveryBillSingleton;
import com.example.barscanv01.ServiceAPI.LastCustomerCodefromDetailBarcodeService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhupipi on 2017/7/10.
 */

public class CheckOutOrderDetailUtil {

    public CheckOutOrderDetailUtil() {

        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        LastCustomerCodefromDetailBarcodeService lastCustomerCodefromDetailBarcodeService = retrofit.create(LastCustomerCodefromDetailBarcodeService.class);
        Call<ReceivedLastCustomerCodeInfo> call = lastCustomerCodefromDetailBarcodeService.getlastCustomerCode(DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
        call.enqueue(new Callback<ReceivedLastCustomerCodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedLastCustomerCodeInfo> call, Response<ReceivedLastCustomerCodeInfo> response) {

                DeliveryBillSingleton.getInstance().setLastCustomerName(response.body().getAttributes().getLastCustomerName());
                DeliveryBillSingleton.getInstance().setLastCustomerCode(response.body().getAttributes().getLastCustomerCode());
            }

            @Override
            public void onFailure(Call<ReceivedLastCustomerCodeInfo> call, Throwable t) {

            }
        });
    }

    public boolean checklastCustomerNotLoaded() {
        boolean result = false;
        if (DeliveryBillSingleton.getInstance().getLastCustomerCode() != null) {
            for (OutOrderDetailBean outOrderDetailBean : DeliveryBillSingleton.getInstance().getOutOrderDetailBean()) {
                if(outOrderDetailBean.getCustomerCode().equals(DeliveryBillSingleton.getInstance().getLastCustomerCode())){
                    if(outOrderDetailBean.getFinishStatus().equals("0")){
                        result=true;
                        break;
                    }
                }
            }
        }
        return  result;
    }

}
