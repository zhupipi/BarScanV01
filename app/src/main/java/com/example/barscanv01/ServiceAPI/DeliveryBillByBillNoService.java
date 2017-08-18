package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedDelivieryBillInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/6/30.
 */

public interface DeliveryBillByBillNoService {
    @FormUrlEncoded
    @POST("outOrderController.do?getoutOrderforPDA")
    Call<ReceivedDelivieryBillInfo> getDeliveryBillbyBillN0(@Field("outOrderNo") String outOrderNo ,@Field("areaNo") String areaNo);
}
