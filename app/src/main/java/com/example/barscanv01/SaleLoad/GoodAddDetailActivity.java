package com.example.barscanv01.SaleLoad;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.GoodAddDetailAdapter;
import com.example.barscanv01.Bean.DetailBarcodeBean;
import com.example.barscanv01.Bean.GoodsManageDetailBean;
import com.example.barscanv01.Bean.ReceivedDetailBarcodeInfo;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetDetailBarcodeService;
import com.example.barscanv01.Util.GoodsManageUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GoodAddDetailActivity extends AppCompatActivity {
    @BindView(R.id.good_add_detail_outOrder_No)
    TextView orderNo;
    @BindView(R.id.good_add_detail_car_plate)
    TextView carPlate;
    @BindView(R.id.good_add_detail_weight)
    TextView actWeight;
    @BindView(R.id.good_add_detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.good_add_detail_confrim)
    Button confrimButton;
    @BindView(R.id.good_add_detail_cancel)
    Button cancelButton;
    @BindView(R.id.good_add_detail_result_view)
    RecyclerView resultView;
    @BindView(R.id.good_add_detail_act_count)
    TextView actCount;

    List<GoodsManageDetailBean> mGoodsManageDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_add_detail);
        ButterKnife.bind(this);
        initalData();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("加货信息确认");
        getGoodsManageList();
        mGoodsManageDetailList = new ArrayList<GoodsManageDetailBean>();
        getGoodsManageList();
        resultView.setLayoutManager(new LinearLayoutManager(this));
        resultView.setItemAnimator(new DefaultItemAnimator());
        resultView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        setListener();
    }

    private void setListener() {
        confrimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoodsManageDetailList.size() > 0) {
                    Intent intent = new Intent(GoodAddDetailActivity.this, GoodAddLoadActivity.class);
                    if (actWeight.getText().toString().trim().equals("")) {
                        actWeight.setText("0");
                    }
                    intent.putExtra("act_weight", Double.valueOf(actWeight.getText().toString().trim()));
                    startActivity(intent);
                } else {
                    Toast.makeText(GoodAddDetailActivity.this, "现在无加货货品", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryBillSingleton.getInstance().setGoodsManageDetailList(null);
                GoodAddDetailActivity.this.finish();
            }
        });
    }

    private void initalData() {
        showOrderWeight();
        orderNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo());
        carPlate.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo());
    }

    private void showOrderWeight() {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetDetailBarcodeService getDetailBarcodeService = retrofit.create(GetDetailBarcodeService.class);
        Call<ReceivedDetailBarcodeInfo> call = getDetailBarcodeService.getDetailBarcodesById(DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
        call.enqueue(new Callback<ReceivedDetailBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedDetailBarcodeInfo> call, Response<ReceivedDetailBarcodeInfo> response) {
                List<DetailBarcodeBean> detailBarcodes = new ArrayList<DetailBarcodeBean>();
                detailBarcodes = response.body().getAttributes().getDetailBarcodeEntityList();
                double act_weight = 0;
                int act_count = 0;
                if (detailBarcodes.size() > 0) {
                    for (DetailBarcodeBean detaiBarcode : detailBarcodes) {
                        act_weight = act_weight + detaiBarcode.getWeight();
                        if (detaiBarcode.getBarcode() != null) {
                            act_count = act_count + 1;
                        }
                    }
                }
                /*act_weight = Math.round(act_weight * 100);
                act_weight = act_weight / 100;*/
                actWeight.setText(String.valueOf(act_weight));
                actCount.setText(String.valueOf(act_count));
            }

            @Override
            public void onFailure(Call<ReceivedDetailBarcodeInfo> call, Throwable t) {

            }
        });
    }

    private void getGoodsManageList() {
        GoodsManageUtil goodsManageUtil = new GoodsManageUtil();
        goodsManageUtil.getGoodsManageDetail();
        goodsManageUtil.setOnResponseListener(new GoodsManageUtil.OnResponseListener() {
            @Override
            public void onResponse(List<GoodsManageDetailBean> goodsManagerDetailList) {
                mGoodsManageDetailList = goodsManagerDetailList;
                DeliveryBillSingleton.getInstance().setGoodsManageDetailList(mGoodsManageDetailList);
                showDetail(mGoodsManageDetailList);
                if (mGoodsManageDetailList.size() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GoodAddDetailActivity.this);
                    builder.setTitle("注意")
                            .setMessage("现在无加货货品")
                            .setPositiveButton("确定", null)
                            .show();
                }
            }
        });
    }

    private void showDetail(List<GoodsManageDetailBean> mGoodsManageDetailList) {
        GoodAddDetailAdapter adapter = new GoodAddDetailAdapter(GoodAddDetailActivity.this, mGoodsManageDetailList);
        resultView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DeliveryBillSingleton.getInstance().setGoodsManageDetailList(null);
        this.finish();
    }
}
