package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.ReceivedDelivieryBillInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/7/2.
 */

public interface DeliveryBillByPlateService {
    @FormUrlEncoded
    @POST("outOrderController.do?getoutOrderforPDAbyPlate")
    Call<ReceivedDelivieryBillInfo> getDeliveryBillByPlate(@Field("plateNo") String plateNo ,@Field("areaNo") String areaNo);
}
