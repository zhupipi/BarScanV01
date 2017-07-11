package com.example.barscanv01.Operation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.barscanv01.Bean.UserBean;
import com.example.barscanv01.Login.LoginActivity;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.UpdateUserService;
import com.example.barscanv01.Util.UpdateUserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OperationSelectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout userContentLayout;
    MyApp myApp;
    UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_select);
        myApp=(MyApp)getApplication();
        myApp.addActivity(this);
        userBean=myApp.getUserBean();
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.operation_select_drawerLayout);
        userContentLayout = (LinearLayout) findViewById(R.id.user_content);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("操作选择");
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("注意")
                .setMessage("您即将退出系统")
                //.setCancelable(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateUserUtil updateUserUtil=new UpdateUserUtil(myApp.getUserBean());
                        updateUserUtil.exit();
                        myApp.exit();
                    }
                })
                .show();

    }
}
