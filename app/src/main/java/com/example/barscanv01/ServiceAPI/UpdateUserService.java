package com.example.barscanv01.ServiceAPI;

import com.example.barscanv01.Bean.UserBean;
import com.example.barscanv01.Bean.UserUpdateResultBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhupipi on 2017/6/27.
 */

public interface UpdateUserService {
    @POST("userController.do?saveUserforPDA")
    @FormUrlEncoded
    //Call<ResponseBody> updateUser(@FieldMap Map<String,String> map1,@FieldMap Map<String,Integer> map2);
    //Call<ResponseBody> updateUser(@Body UserBean user);
    Call<UserUpdateResultBean> updateUser(@FieldMap Map<String,String> map1,@FieldMap Map<String,Integer> map2);
}
