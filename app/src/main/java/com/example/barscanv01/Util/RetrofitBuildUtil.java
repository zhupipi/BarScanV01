package com.example.barscanv01.Util;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhupipi on 2017/7/6.
 */

public class RetrofitBuildUtil {
    public Retrofit retrofit;
    public RetrofitBuildUtil(){
        retrofit=new Retrofit.Builder()
               //.baseUrl("http://192.168.1.100:8080/jeecg/")
                //.baseUrl("http://192.168.5.235:8088/scan/")
                .baseUrl("http://sc.yfgg.com/scan/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
