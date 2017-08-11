package com.example.barscanv01.Util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.SaleLoad.DeliveryBillSingleton;
import com.example.barscanv01.ServiceAPI.WriteBarcodeDetailService;
import com.example.barscanv01.ServiceAPI.WriteBizlogService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhupipi on 2017/7/11.
 */

public class WriteBizlogUtil {

    private Activity activity;
    private MyApp myApp;
    private OutOrderBean outOrder;
    public WriteBizlogUtil( Activity activity){
        this.activity=activity;
        myApp=(MyApp)activity.getApplication();
        outOrder= DeliveryBillSingleton.getInstance().getOutOrderBean();
    }
    public void writeLoadStartedLog(){
        Map<String,String> map1=new HashMap<String,String>();
        map1.put("plateNo",outOrder.getPlateNo());
        map1.put("orderNo",outOrder.getOutOrderNo());
        map1.put("location","库位");
        map1.put("depotName",myApp.getCurrentDepot().getDepotName());
        map1.put("areaName",myApp.getCurrentAreaBean().getAreaName());
        map1.put("process","开始装车");
        map1.put("remark","车牌号为["+outOrder.getPlateNo()+"]的车辆正在为订单号为"+"“"+outOrder.getOutOrderNo()+"”的订单在库区"+myApp.getCurrentDepot().getDepotName()+"装车");

        Date currentDate=new Date(System.currentTimeMillis()+5000);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time= dateFormat.format(currentDate);

        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        WriteBizlogService writeBizlogService=retrofit.create(WriteBizlogService.class);
        Call<ResponseBody> call=writeBizlogService.writeBizlog(map1,time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    public void writeDepotLoadFinishedLog(){
        List<OutOrderDetailBean> detailList=DeliveryBillSingleton.getInstance().getOutOrderDetailBean();
        for(OutOrderDetailBean detail:detailList){
            if(detail.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())){
                writeLoadFinishLog(detail);
            }
        }
    }
    public void writeLoadFinishLog(OutOrderDetailBean detail){
        Map<String,String> map1=new HashMap<String,String>();
        map1.put("plateNo",outOrder.getPlateNo());
        map1.put("orderNo",outOrder.getOutOrderNo());
        map1.put("location","库位");
        map1.put("depotName",myApp.getCurrentDepot().getDepotName());
        map1.put("areaName",myApp.getCurrentAreaBean().getAreaName());
        map1.put("process","开始装车");
        map1.put("remark","车牌号为["+outOrder.getPlateNo()+"]的车辆已为客户"+detail.getCustomerName()+"装规格为"+detail.getSpecificationModel()+"的货品完成装车");

        Date currentDate=new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time= dateFormat.format(currentDate);

        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        WriteBizlogService writeBizlogService=retrofit.create(WriteBizlogService.class);
        Call<ResponseBody> call=writeBizlogService.writeBizlog(map1,time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void writeOutOrderFinishedLog(){
        Map<String,String> map1=new HashMap<String,String>();
        map1.put("plateNo",outOrder.getPlateNo());
        map1.put("orderNo",outOrder.getOutOrderNo());
        map1.put("location","库位");
        map1.put("depotName",myApp.getCurrentDepot().getDepotName());
        map1.put("areaName",myApp.getCurrentAreaBean().getAreaName());
        map1.put("process","装车完成");
        map1.put("remark","车牌号为["+outOrder.getPlateNo()+"]的车辆为发货单号为"+outOrder.getOutOrderNo()+"的发货单全部装车");

        Date currentDate=new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time= dateFormat.format(currentDate);
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        WriteBizlogService writeBizlogService=retrofit.create(WriteBizlogService.class);
        Call<ResponseBody> call=writeBizlogService.writeBizlog(map1,time);
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
