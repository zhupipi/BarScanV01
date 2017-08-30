package com.example.barscanv01.SaleLoad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.LoadedGoodsBarcodeAdapter;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedLoadGoodsBarcodeInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetLoadGoodsBarcodeService;
import com.example.barscanv01.Util.RetrofitBuildUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoadContentActivity extends AppCompatActivity {
    @BindView(R.id.out_order_load_content_title)
    TextView title;
    @BindView(R.id.out_order_load_content_result)
    RecyclerView loadedResut;
    @BindView(R.id.out_order_load_content_actcount)
    TextView actCount;
    @BindView(R.id.out_order_load_content_not_load_count)
    TextView notLoadCount;
    @BindView(R.id.out_order_load_content_act_weight)
    TextView actWeight;

    private static final String ORDER_LOAD_CONTENT = "ORDER_LOAD_CONTENT";
    private static final String DEPOT_LOAD_CONTENT = "DEPOT_LOAD_CONTENT";
    private String tag;
    private MyApp myApp;

    private List<GoodsBarcodeBean> loadedGoodsBarcodeList;
    private List<String> customerNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_content);
        ButterKnife.bind(this);
        myApp = (MyApp) getApplication();
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        loadedGoodsBarcodeList = new ArrayList<GoodsBarcodeBean>();
        customerNameList = new ArrayList<String>();
        loadedResut.setLayoutManager(new LinearLayoutManager(this));
        loadedResut.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        loadedResut.setItemAnimator(new DefaultItemAnimator());
        getLoadedGoodsBarcode();
        intialTitle();

    }

    private void getLoadedGoodsBarcode() {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetLoadGoodsBarcodeService getLoadGoodsBarcodeService = retrofit.create(GetLoadGoodsBarcodeService.class);
        Call<ReceivedLoadGoodsBarcodeInfo> call = getLoadGoodsBarcodeService.getLoadedGoods(DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
        call.enqueue(new Callback<ReceivedLoadGoodsBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedLoadGoodsBarcodeInfo> call, Response<ReceivedLoadGoodsBarcodeInfo> response) {
                loadedGoodsBarcodeList = response.body().getAttributes().getGoodsBarcodeEndtityList();
                customerNameList = response.body().getAttributes().getCustomerList();
                intialWeight();
                showLoadedGoods();
            }

            @Override
            public void onFailure(Call<ReceivedLoadGoodsBarcodeInfo> call, Throwable t) {
                Toast.makeText(LoadContentActivity.this, "获取装货信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intialTitle() {
        switch (tag) {
            case ORDER_LOAD_CONTENT:
                title.setText("此发货单已经装车货品明细：");
                break;
            case DEPOT_LOAD_CONTENT:
                title.setText("用户库房已经装车货品明细：");
                break;
            default:
                break;
        }
    }

    private void intialWeight() {
        float act_weight = 0;
        for (GoodsBarcodeBean goodsBarcodeBean : loadedGoodsBarcodeList) {
            act_weight = act_weight + Float.valueOf(goodsBarcodeBean.getActWeight());
        }
        actWeight.setText(String.valueOf(act_weight));
        switch (tag) {
            case ORDER_LOAD_CONTENT:
                int act_count = 0;
                int not_load_count = 0;
                for (OutOrderDetailBean detail : DeliveryBillSingleton.getInstance().getOutOrderDetailBean()) {
                    if (detail.getActCount() != null) {
                        act_count = (int) (act_count + Double.valueOf(detail.getActCount()));
                    }
                    if (detail.getFinishStatus().equals("0")) {
                        if (detail.getActCount() == null) {
                            not_load_count = not_load_count + detail.getCount();
                        } else {
                            not_load_count = (int) (not_load_count + detail.getCount() - Double.valueOf(detail.getActCount()));
                        }
                    }
                }
                actCount.setText(String.valueOf(act_count));
                notLoadCount.setText(String.valueOf(not_load_count));
                break;
            case DEPOT_LOAD_CONTENT:
                int act_count1 = 0;
                int not_load_count1 = 0;
                for (OutOrderDetailBean detail : DeliveryBillSingleton.getInstance().getOutOrderDetailBean()) {
                    if (detail.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())) {
                        if (detail.getActCount() != null) {
                            act_count1 = (int) (act_count1 + Double.valueOf(detail.getActCount()));
                        }
                        if (detail.getFinishStatus().equals("0")) {
                            if (detail.getActCount() == null) {
                                not_load_count1 = not_load_count1 + detail.getCount();
                            } else {
                                not_load_count1 = (int) (not_load_count1 + detail.getCount() - Double.valueOf(detail.getActCount()));
                            }
                        }
                    }
                }
                actCount.setText(String.valueOf(act_count1));
                notLoadCount.setText(String.valueOf(not_load_count1));

                break;
            default:
                break;
        }
    }

    private void showLoadedGoods() {
        LoadedGoodsBarcodeAdapter adapter = new LoadedGoodsBarcodeAdapter(LoadContentActivity.this, loadedGoodsBarcodeList, customerNameList, tag, myApp.getCurrentDepot().getDepotNo());
        loadedResut.setAdapter(adapter);
    }

}
