package com.example.barscanv01.Util;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhupipi on 2017/7/6.
 */

public class RetrofitBuildUtil {
    public Retrofit retrofit;

    public RetrofitBuildUtil() {
        retrofit = new Retrofit.Builder()
                // .baseUrl("http://192.168.1.110:8080/scan/")   //本机地址
                .baseUrl("http://192.168.0.164:8080/scan/")    //正元服务器地址
               // .baseUrl("http://sc.yfgg.com/scan/")          //一公司服务器地址
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
