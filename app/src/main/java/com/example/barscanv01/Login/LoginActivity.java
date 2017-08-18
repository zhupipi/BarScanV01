package com.example.barscanv01.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.barscanv01.Bean.AreaBean;
import com.example.barscanv01.Bean.ReceivedUserInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.Operation.OperationSelectActivity;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.LoginService;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.example.barscanv01.Util.UpdateUserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.delete_button1)
    ImageButton name_delete;
    @BindView(R.id.delete_button2)
    ImageButton password_delete;
    @BindView(R.id.uer_name_edit)
    AutoCompleteTextView user_name;
    @BindView(R.id.user_password_edit)
    EditText user_password;
    @BindView(R.id.login_button)
    Button login_button;

    SharedPreferences userSharedPreference;
    SharedPreferences.Editor mEditor;
    LoginCache loginCache;
    MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        myApp = (MyApp) getApplication();
        myApp.addActivity(this);
        loginCache = new LoginCache(LoginActivity.this);
        user_name.setText(loginCache.getIntalName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, loginCache.getNameList());
        user_name.setAdapter(adapter);

    }

    @OnClick(R.id.delete_button1)
    void editNameDelete() {
        user_name.setText("");
    }

    @OnClick(R.id.delete_button2)
    void editPasswordDelete() {
        user_password.setText("");
    }

    @OnClick(R.id.login_button)
    void login() {
        String account_name = user_name.getText().toString();
        String account_password = user_password.getText().toString();
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        LoginService loginService = retrofit.create(LoginService.class);
        Map<String, String> map = new HashMap<>();
        map.put("userName", account_name);
        map.put("password", account_password);
        Call<ReceivedUserInfo> call = loginService.login(map);
        call.enqueue(new Callback<ReceivedUserInfo>() {
            @Override
            public void onResponse(Call<ReceivedUserInfo> call, Response<ReceivedUserInfo> response) {
                if (response.body().isSuccess()) {
                    if (response.body().getAttributes().getUser().getStatus() != 1) {
                        loginCache.putName(response.body().getAttributes().getUser().getUserName());
                        myApp.setUserBean(response.body().getAttributes().getUser());
                        myApp.setDepotlist(response.body().getAttributes().getDepotlist());
                        myApp.setArealist(response.body().getAttributes().getArealist());
                        Intent intent = new Intent(LoginActivity.this, OperationSelectActivity.class);
                        startActivity(intent);
                        UpdateUserUtil updateUserUtil = new UpdateUserUtil(myApp.getUserBean(), LoginActivity.this);
                        updateUserUtil.login();
                    } else {
                        Toast.makeText(LoginActivity.this, "此用户在" + response.body().getAttributes().getUser().getDeviceName() + "上已经登录", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "用户名密码错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedUserInfo> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "服务器连接错误", Toast.LENGTH_SHORT).show();
            }

        });


    }

}
