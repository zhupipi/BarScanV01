package com.example.barscanv01.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.R;

/**
 * Created by zhulin on 2017/11/10.
 */

public class BackgroundUtil {
    public static final int SCAN_SUCESS = 1;
    public static final int SCAN_FAILED = 0;

    public PopupWindow backGroundWindow;
    public View backGroundView;
    public TextView textView;

    public BackgroundUtil(Context context) {
        backGroundView = LayoutInflater.from(context).inflate(R.layout.scan_result_background, null);
        textView = (TextView) backGroundView.findViewById(R.id.scan_result_text_view);
        backGroundWindow = new PopupWindow(context);
        backGroundWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        backGroundWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void changeBackground(View parentView, int result, GoodsBarcodeBean good) {
        switch (result) {
            case SCAN_SUCESS:
                backGroundView.setBackgroundColor(Color.RED);
                textView.setText("条码为：" + good.getBarcode() + "的" + good.getSpecificationModel() + "货品扫描成功");
                break;
            case SCAN_FAILED:
                backGroundView.setBackgroundColor(Color.BLACK);
                textView.setText(Html.fromHtml("<font color='#FF82AB'>扫码失败！</font>"));
                break;
            default:
                break;
        }
        backGroundWindow.setContentView(backGroundView);
        backGroundWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        BackgroundAsyncTast asyncTast = new BackgroundAsyncTast();
        asyncTast.execute();

    }

    class BackgroundAsyncTast extends AsyncTask<Void, Void, LinearLayout> {

        @Override
        protected LinearLayout doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(LinearLayout linearLayout) {
            super.onPostExecute(linearLayout);
            backGroundWindow.dismiss();
        }
    }
}
