package com.example.barscanv01.Util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.GoodsManageDetailBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedGoodsManageInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.SaleLoad.DeliveryBillSingleton;
import com.example.barscanv01.ServiceAPI.GetGoodsManageDetailService;
import com.example.barscanv01.ServiceAPI.GetGoodsManageService;
import com.example.barscanv01.ServiceAPI.OutOrderDetailProcessService;
import com.example.barscanv01.ServiceAPI.OutOrderProcessService;
import com.example.barscanv01.Setting.SettingSingletone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhupipi on 2017/7/21.
 */

public class GoodsManageUtil {

    public static final int REMOVE_GOOD_NO_PROMISE = 0;
    public static final int DEPOT_LOAD_OVER_NO_PROMISE = 1;
    public static final int ORDER_LOAD_OVER_NO_PROMISE = 2;

    private Context context;
    private List<OutOrderDetailBean> outOrderDetailList;
    private List<GoodsManageDetailBean> goodsManageDetailBeanList;
    private MyApp myApp;
    private OnResponseListener onResponseListener;

    public int resultCode;

    public GoodsManageUtil() {
        goodsManageDetailBeanList = new ArrayList<GoodsManageDetailBean>();
    }

    public GoodsManageUtil(List<OutOrderDetailBean> outOrderDetailList, Context context) {
        this.context = context;
        this.outOrderDetailList = outOrderDetailList;
        goodsManageDetailBeanList = new ArrayList<GoodsManageDetailBean>();
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    public void getGoodsManageDetail() {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetGoodsManageDetailService getGoodsManageDetailService = retrofit.create(GetGoodsManageDetailService.class);
        Call<ReceivedGoodsManageInfo> call = getGoodsManageDetailService.getGoodsManageDetail();
        call.enqueue(new Callback<ReceivedGoodsManageInfo>() {
            @Override
            public void onResponse(Call<ReceivedGoodsManageInfo> call, Response<ReceivedGoodsManageInfo> response) {
                goodsManageDetailBeanList = response.body().getAttributes().getGoodsManageDetailList();
                if (onResponseListener != null) {
                    onResponseListener.onResponse(goodsManageDetailBeanList);
                }
            }

            @Override
            public void onFailure(Call<ReceivedGoodsManageInfo> call, Throwable t) {
                Toast.makeText(context, "获取加减货信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean getDepotRemoveResult(DepotBean currentDepot, List<GoodsManageDetailBean> goodsManageDetailBeanList) {
        boolean result = true;
        if (SettingSingletone.getInstance(context).getRemoveResult()) {
            if (goodsManageDetailBeanList.size() > 0) {
                for (GoodsManageDetailBean goodsManageDetail : goodsManageDetailBeanList) {
                    for (OutOrderDetailBean outOrderDetail : outOrderDetailList) {
                        if (outOrderDetail.getGoodsCode().equals(goodsManageDetail.getGoodsCode())) {
                            outOrderDetail.setRemovePromise(true);
                        }
                    }
                }
                for (OutOrderDetailBean orderDetail : outOrderDetailList) {
                    if (orderDetail.getDepotNo().equals(currentDepot.getDepotNo())) {
                        if (!orderDetail.isRemovePromise()) {
                            float actCount = 0;
                            if (orderDetail.getActCount() != null) {
                                actCount = Float.valueOf(orderDetail.getActCount());
                            }
                            if (actCount < orderDetail.getCount()) {
                                result = false;
                                resultCode = DEPOT_LOAD_OVER_NO_PROMISE;
                                break;
                            }
                        }
                    }
                }

            } else {
                for (OutOrderDetailBean orderDetail : outOrderDetailList) {
                    if (orderDetail.getDepotNo().equals(currentDepot.getDepotNo())) {
                        float actCount = 0;
                        if (orderDetail.getActCount() != null) {
                            actCount = Float.valueOf(orderDetail.getActCount());
                        }
                        if (actCount < orderDetail.getCount()) {
                            result = false;
                            resultCode = DEPOT_LOAD_OVER_NO_PROMISE;
                            break;
                        }
                    }
                }
            }
        } else {
            result = false;
            resultCode = REMOVE_GOOD_NO_PROMISE;
        }
        return result;
    }

    public boolean getOrderRemoveResult(List<GoodsManageDetailBean> goodsManageDetailBeanList) {
        boolean result = true;
        if (SettingSingletone.getInstance(context).getRemoveResult()) {
            if (goodsManageDetailBeanList.size() > 0) {
                for (GoodsManageDetailBean goodsManageDetail : goodsManageDetailBeanList) {
                    for (OutOrderDetailBean outOrderDetail : outOrderDetailList) {
                        if (outOrderDetail.getGoodsCode().equals(goodsManageDetail.getGoodsCode())) {
                            outOrderDetail.setRemovePromise(true);
                        }
                    }
                }
                for (OutOrderDetailBean orderDetail : outOrderDetailList) {
                    if (!orderDetail.isRemovePromise()) {
                        float actCount = 0;
                        if (orderDetail.getActCount() != null) {
                            actCount = Float.valueOf(orderDetail.getActCount());
                        }
                        if (actCount < orderDetail.getCount()) {
                            result = false;
                            resultCode = DEPOT_LOAD_OVER_NO_PROMISE;
                            break;
                        }
                    }
                }
            } else {
                for (OutOrderDetailBean orderDetail : outOrderDetailList) {

                    float actCount = 0;
                    if (orderDetail.getActCount() != null) {
                        actCount = Float.valueOf(orderDetail.getActCount());
                    }
                    if (actCount < orderDetail.getCount()) {
                        result = false;
                        resultCode = DEPOT_LOAD_OVER_NO_PROMISE;
                        break;
                    }
                }

            }

        } else {
            result = false;
            resultCode = REMOVE_GOOD_NO_PROMISE;
        }
        return result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public interface OnResponseListener {
        void onResponse(List<GoodsManageDetailBean> goodsManagerDetailList);
    }
}
