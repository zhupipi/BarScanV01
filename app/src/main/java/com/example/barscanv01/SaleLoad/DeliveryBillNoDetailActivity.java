package com.example.barscanv01.SaleLoad;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.ReceivedDetailTotalWeightBean;
import com.example.barscanv01.Fragment.LoadOperationSelectFragment;
import com.example.barscanv01.Fragment.ScanResultFragment;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetOrderDetailWeightService;
import com.example.barscanv01.ServiceAPI.LoadOverService;
import com.example.barscanv01.Util.CheckOutOrederDetailFinishedUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeliveryBillNoDetailActivity extends AppCompatActivity {
    @BindView(R.id.delivery_bill_nodetail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.delivery_bill_nodetail_billNo)
    TextView billNo;
    @BindView(R.id.delivery_bill_nodetail_plateNo)
    TextView plateNo;
    @BindView(R.id.delivery_bill_nodetail_frag_change)
    FrameLayout fragExchange;
    @BindView(R.id.delivery_bill_nodetail_act_weight)
    TextView actWeight;

    private FragmentManager fragmentManager;
    private MyApp myApp;
    private ScanManager scanManager;
    private OutOrderBean outOrder;

    // private float totalWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_bill_no_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("扫码装车/倒垛");
        myApp = (MyApp) getApplication();
        initalData();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        LoadOperationSelectFragment loadOperationSelectFragment = new LoadOperationSelectFragment();
        loadOperationSelectFragment.setOnItemClickListener(new LoadOperationSelectFragment.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String tag) {
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                ScanResultFragment scanResultFragment = new ScanResultFragment();
                transaction1.replace(R.id.delivery_bill_nodetail_frag_change, scanResultFragment, tag);
                transaction1.commit();
            }
        });
        transaction.add(R.id.delivery_bill_nodetail_frag_change, loadOperationSelectFragment, "OPERATION_SELECT");
        transaction.commit();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.delivery_bill_nodetail_loadover) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(DeliveryBillNoDetailActivity.this);
                    builder.setTitle("注意")
                            .setMessage("您确定该发货单全部装车完成")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Retrofit retrofit = new RetrofitBuildUtil().retrofit;
                                    LoadOverService loadOverService = retrofit.create(LoadOverService.class);
                                    Call<ResponseBody> call = loadOverService.loadOver(outOrder.getId());
                                    call.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            CheckOutOrederDetailFinishedUtil checkFinished = new CheckOutOrederDetailFinishedUtil(outOrder, DeliveryBillNoDetailActivity.this);
                                            checkFinished.outOrderFinised();
                                        }
                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                }
                            }).show();
                }
                return true;
            }
        });
    }

    private void initalData() {
        outOrder = DeliveryBillSingleton.getInstance().getOutOrderBean();
        billNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo());
        plateNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo());
        scanManager = ScanManager.getInstance();
        scanManager.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_BROADCAST);
        showWeight();
    }

    private void showWeight() {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetOrderDetailWeightService getWeightService = retrofit.create(GetOrderDetailWeightService.class);
        Call<ReceivedDetailTotalWeightBean> call = getWeightService.getTotalWeight(outOrder.getId());
        call.enqueue(new Callback<ReceivedDetailTotalWeightBean>() {
            @Override
            public void onResponse(Call<ReceivedDetailTotalWeightBean> call, Response<ReceivedDetailTotalWeightBean> response) {
                float totalWeight = response.body().getAttributes().getTotalWeight();
                actWeight.setText(String.valueOf(totalWeight));

            }

            @Override
            public void onFailure(Call<ReceivedDetailTotalWeightBean> call, Throwable t) {
                actWeight.setText("0.0");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delivery_no_detail_select,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
