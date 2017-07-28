package com.example.barscanv01.Util;

import android.app.Activity;
import android.util.Log;

import com.example.barscanv01.Bean.DetailBarcodeEntity;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.ServiceAPI.WriteBarcodeDetailService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhupipi on 2017/7/5.
 */

public class WriteDetailBarcodeUtil {

    public OutOrderBean outOrder;
    public GoodsBarcodeBean good;
    public MyApp myApp;
    public Map<String,String> map;


    public WriteDetailBarcodeUtil(OutOrderBean outOrderBean, GoodsBarcodeBean goodsBarcodeBean, Activity activity){
        myApp=(MyApp) activity.getApplication();
        this.outOrder=outOrderBean;
        this.good=goodsBarcodeBean;
        map=new HashMap<String, String>();
    }
    public void write(){
        map.put("goodsId",good.getGoodsId());
        map.put("orderId",outOrder.getId());
        map.put("areaNo",myApp.getCurrentAreaBean().getAreaNo());
        map.put("depotNo",good.getDepotNo());
        map.put("batchNumber",good.getBatchNo());
        map.put("barcode",good.getBarcode());
        map.put("flag","0");
        map.put("scanUserName",myApp.getUserBean().getUserName());


        Date currentDate=new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time= dateFormat.format(currentDate);

        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();;
        WriteBarcodeDetailService writeBarcodeDetailService=retrofit.create(WriteBarcodeDetailService.class);
        Call<ResponseBody> call=writeBarcodeDetailService.addBarcodeDetail(map,new BigDecimal(good.getActWeight()),time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("aaaaa",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
