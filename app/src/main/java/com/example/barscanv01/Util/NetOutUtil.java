package com.example.barscanv01.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.barscanv01.Login.LoginActivity;
import com.example.barscanv01.MyApp;

/**
 * Created by zhulin on 2018/1/11.
 */

public class NetOutUtil {
    public  static void netOut(final Context context, final MyApp myApp){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("注意")
                .setMessage("网络已断，请重新登录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        myApp.exit();
                    }
                }).show();

    }
}
