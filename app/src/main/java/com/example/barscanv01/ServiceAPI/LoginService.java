package com.example.barscanv01.ServiceAPI;
import com.example.barscanv01.Bean.ReceivedUserInfo;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhulin on 2017/5/11.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("loginController.do?checkuserforPDA")

   // Call<ResponseBody> login(@FieldMap Map<String,String> map);
    Call<ReceivedUserInfo> login(@FieldMap Map<String,String> map);
}
