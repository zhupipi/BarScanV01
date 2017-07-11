package com.example.barscanv01.Util;

import android.content.Context;
import android.util.Log;

import com.example.barscanv01.Bean.UserBean;
import com.example.barscanv01.Bean.UserUpdateResultBean;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.ServiceAPI.UpdateUserService;

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
 * Created by zhupipi on 2017/6/27.
 */

public class UpdateUserUtil {
    UserBean user;
    MyApp myApp;
    boolean result;
    //UserUpdateResultBean userUpdateResultBean;
    public UpdateUserUtil(UserBean user){
        this.user=user;
    };
    public void exit(){

        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        UpdateUserService updateUserService=retrofit.create(UpdateUserService.class);
        Map map1=new HashMap();
        map1.put("id",user.getId());
        map1.put("userName",user.getUserName());
        Map map2=new HashMap();
        map2.put("status",2);
        Call<UserUpdateResultBean> call =updateUserService.updateUser(map1,map2);
        //Call<ResponseBody> call=updateUserService.updateUser(userBean);
        call.enqueue(new Callback<UserUpdateResultBean>() {
            @Override
            public void onResponse(Call<UserUpdateResultBean> call, Response<UserUpdateResultBean> response) {
                if(response.body().isSuccess()){
                    setResult(true);
                }
            }
            @Override
            public void onFailure(Call<UserUpdateResultBean> call, Throwable t) {

            }
        });

    }

    public void login(){

        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        UpdateUserService updateUserService=retrofit.create(UpdateUserService.class);
        Map map1=new HashMap();
        map1.put("id",user.getId());
        map1.put("userName",user.getUserName());
        Map map2=new HashMap();
        map2.put("status",1);
        Call<UserUpdateResultBean> call =updateUserService.updateUser(map1,map2);
        //Call<ResponseBody> call=updateUserService.updateUser(userBean);
        call.enqueue(new Callback<UserUpdateResultBean>() {
            @Override
            public void onResponse(Call<UserUpdateResultBean> call, Response<UserUpdateResultBean> response) {
                    if(response.body().isSuccess()){
                        setResult(true);
                    }
            }
            @Override
            public void onFailure(Call<UserUpdateResultBean> call, Throwable t) {

            }
        });

    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
