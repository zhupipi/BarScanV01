package com.example.barscanv01.Util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedGoodsManageInfo;
import com.example.barscanv01.SaleLoad.DeliveryBillSingleton;
import com.example.barscanv01.ServiceAPI.GetGoodsManageService;
import com.example.barscanv01.ServiceAPI.OutOrderDetailProcessService;
import com.example.barscanv01.ServiceAPI.OutOrderProcessService;

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
    private Activity activity;
    ArrayList<String> goods;
    ArrayList<String> goodCodes;
    String goodCode;
    public GoodsManageUtil(Activity activity){
        this.activity=activity;
        goods=new ArrayList<String>();
        goodCodes=new ArrayList<String>();
        getGoodList();
       // showDialog();
    }
    public void getGoodList(){
        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
        GetGoodsManageService getGoodsManageService=retrofit.create(GetGoodsManageService.class);
        Call<ReceivedGoodsManageInfo> call=getGoodsManageService.getGoodList();
        call.enqueue(new Callback<ReceivedGoodsManageInfo>() {
            @Override
            public void onResponse(Call<ReceivedGoodsManageInfo> call, Response<ReceivedGoodsManageInfo> response) {

                if(response.body().getAttributes().getGoodsList().size()>0){
                    for(ReceivedGoodsManageInfo.AttributesBean.GoodsListBean goodsListBean:response.body().getAttributes().getGoodsList()){
                        goods.add(goodsListBean.getGoodsName()+","+goodsListBean.getSpecificationModel());
                        goodCodes.add(goodsListBean.getGoodsCode());
                    }
                    showDialog();
                }

            }

            @Override
            public void onFailure(Call<ReceivedGoodsManageInfo> call, Throwable t) {

            }
        });
    }
    public void showDialog() {
        if (goods.size() > 0) {
            ArrayAdapter<String> adpter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice, goods);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("请选择加减货货品");
            builder.setSingleChoiceItems(adpter,0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goodCode=goodCodes.get(which);
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    List<OutOrderDetailBean> detailList= DeliveryBillSingleton.getInstance().getOutOrderDetailBean();
                    for(final OutOrderDetailBean detail:detailList){
                        if(detail.getGoodsCode().equals(goodCode)){
                            Retrofit retrofit= new RetrofitBuildUtil().getRetrofit();
                            final OutOrderDetailProcessService outOrderDetailProcessService=retrofit.create(OutOrderDetailProcessService.class);
                            Call<ResponseBody> call=outOrderDetailProcessService.recallProcess(detail.getId());
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    Retrofit retrofit1=new RetrofitBuildUtil().getRetrofit();
                                    OutOrderProcessService outOrderProcessService=retrofit1.create(OutOrderProcessService.class);
                                    Call<ResponseBody> responseBodyCall=outOrderProcessService.updateProcess(detail.getOutOrderId(),"4");
                                    responseBodyCall.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            activity.setResult(1);
                                            activity.finish();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });

                        }
                    }
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.setResult(1);
                    activity.finish();
                }
            }).show();
        }
    }

}
