package com.example.barscanv01.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhupipi on 2017/7/6.
 */

public class RetrofitBuildUtil {
    public Retrofit retrofit;
    //public static String currentUrl = "http://60.2.118.174:8088/scan/";         //正元基础IP地址
    //public static String currentUrl = "http://60.30.209.26:8088/scan/";      //一公司基础IP地址
    public static String currentUrl="http://192.168.4.170:8080/scan/";
    //public Context context;

    /*    public RetrofitBuildUtil() {
            retrofit = new Retrofit.Builder()
                    //.baseUrl("http://192.168.4.176:8080/scan/")   //本机地址
                    //.baseUrl("http://192.168.0.164:8080/scan/")    //正元服务器地址(内网)
                    //.baseUrl("http://sc.yfgg.com/scan/")          //一公司服务器地址（域名）
                    // .baseUrl("http://60.2.118.174:8088/scan/")     //正元服务器地址(外网)
                    .baseUrl("http://60.30.209.26:8088/scan/")  //一公司服务器地址（IP）
                    // .baseUrl("http://27.191.227.80:8088/scan/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }*/
    public static void getCurrentURL(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("SelectUrl", Context.MODE_PRIVATE);
        String url = preferences.getString("currentURL", "http://60.2.118.174:8088/scan/");
        currentUrl = url;
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.commit();
    }

    public RetrofitBuildUtil() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        if (!currentUrl.isEmpty()) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(currentUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
        }

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
