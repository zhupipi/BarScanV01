package com.example.barscanv01.Unload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.InOrderScanResultAdapter;
import com.example.barscanv01.Bean.DetailBarcodeBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.InOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedGoodsBarcodeInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.ScanBarcodeResultService;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UnLoadActivity extends AppCompatActivity {
    @BindView(R.id.un_load_bill_no)
    TextView billNo;
    @BindView(R.id.un_load_car_plate)
    TextView carPlate;
    @BindView(R.id.un_load_weight)
    TextView weight;
    @BindView(R.id.un_load_act_weight)
    TextView actWeight;
    @BindView(R.id.un_load_result_view)
    RecyclerView resultView;
    @BindView(R.id.un_load_toolbar)
    Toolbar toolbar;
    @BindView(R.id.un_load_confirm_button)
    Button confirmButton;
    @BindView(R.id.un_load_cancel_button)
    Button cancelButton;

    private MyApp myApp;
    private ScanManager scanManager;
    private List<GoodsBarcodeBean> resultList;
    private InOrderScanResultAdapter scanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_load);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("卸货扫码");
        myApp = (MyApp) getApplication();
        intialData();
        scanManager = ScanManager.getInstance();
        scanManager.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_BROADCAST);
        resultView.setLayoutManager(new LinearLayoutManager(this));
        resultView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        resultView.setItemAnimator(new DefaultItemAnimator());
    }

    private void intialData() {
        Intent intent = getIntent();
        String totalWeight = intent.getStringExtra("weight");
        String totalActWeight = intent.getStringExtra("actWeight");
        weight.setText(totalWeight);
        actWeight.setText(totalActWeight);
        billNo.setText(InOrderSingleton.getInstance().getInOrder().getInOrderNo());
        carPlate.setText(InOrderSingleton.getInstance().getInOrder().getPlateNo());
        resultList = new ArrayList<GoodsBarcodeBean>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter intFilter = new IntentFilter(ScanManager.ACTION_SEND_SCAN_RESULT);
        registerReceiver(mResultReceiver, intFilter);
    }

    private void unRegisterReceiver() {
        try {
            unregisterReceiver(mResultReceiver);
        } catch (Exception e) {
        }
    }

    private BroadcastReceiver mResultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ScanManager.ACTION_SEND_SCAN_RESULT.equals(action)) {
                byte[] bvalue1 = intent.getByteArrayExtra(ScanManager.EXTRA_SCAN_RESULT_ONE_BYTES);
                byte[] bvalue2 = intent.getByteArrayExtra(ScanManager.EXTRA_SCAN_RESULT_TWO_BYTES);
                String svalue1 = null;
                String svalue2 = null;
                try {
                    if (bvalue1 != null)
                        svalue1 = new String(bvalue1, "GBK");
                    if (bvalue2 != null)
                        svalue2 = new String(bvalue1, "GBK");
                    svalue1 = svalue1 == null ? "" : svalue1;
                    svalue2 = svalue2 == null ? "" : svalue2;
                    String result = svalue1 + svalue2;
                    getScanResult(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(UnLoadActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void getScanResult(String barcode) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        ScanBarcodeResultService scanBarcodeResultService = retrofit.create(ScanBarcodeResultService.class);
        Call<ReceivedGoodsBarcodeInfo> call = scanBarcodeResultService.getGoodsBarcode(barcode);
        call.enqueue(new Callback<ReceivedGoodsBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedGoodsBarcodeInfo> call, Response<ReceivedGoodsBarcodeInfo> response) {
                if (response.body().getAttributes().getGoodsBarcode() != null) {
                    GoodsBarcodeBean good = response.body().getAttributes().getGoodsBarcode();
                    if (checkGood(good)) {
                        showScanRsult(good);
                        double act_weight=Double.valueOf(actWeight.getText().toString().trim())+Double.valueOf(good.getActWeight());
                        actWeight.setText(act_weight+"");
                    }
                } else {
                    Toast.makeText(UnLoadActivity.this, "该条码无对应货品信息", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReceivedGoodsBarcodeInfo> call, Throwable t) {

            }
        });
    }

    private boolean checkGood(GoodsBarcodeBean good) {
        boolean result = true;
        if (!(good.getStatus().equals("1") && checkGoodEixted(good))) {
            result = false;
            Toast.makeText(UnLoadActivity.this, "该条码不在发货单“" + InOrderSingleton.getInstance().getInOrder().getOutOrderNo() + "”的装车明细中", Toast.LENGTH_SHORT).show();
        } else if (!checkDetailGood(good)) {
            result = false;
            Toast.makeText(UnLoadActivity.this, "改条码不不符合卸货单明细要求", Toast.LENGTH_SHORT).show();
        }else if(getScanNum(good)+getDetailModleActNum(good)+1>getDetailModleNum(good)){
            result=false;
            Toast.makeText(UnLoadActivity.this,"该货品已超出退货明细规定退货数目",Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private int getScanNum(GoodsBarcodeBean good) {
        int totalNum=0;
        if(resultList.size()>0){
            for(GoodsBarcodeBean goodBarcode:resultList){
                if(goodBarcode.getGoodsId().equals(good.getGoodsId())){
                    totalNum=totalNum+1;
                }
            }
        }
        return totalNum;
    }
    private int getDetailModleNum(GoodsBarcodeBean good){
        int totalNum=0;
        for(InOrderDetailBean detail:InOrderSingleton.getInstance().getInOrderDetailList()){
            if(detail.getGoodsName().equals(good.getGoodsName())&&detail.getSpecificationModel().equals(good.getSpecificationModel())){
                totalNum=totalNum+detail.getCount();
            }
        }
        return totalNum;
    }
    private int getDetailModleActNum(GoodsBarcodeBean good){
        int totalNum=0;
        for(InOrderDetailBean detail:InOrderSingleton.getInstance().getInOrderDetailList()){
            if(detail.getGoodsName().equals(good.getGoodsName())&&detail.getSpecificationModel().equals(good.getSpecificationModel())){
                totalNum=totalNum+detail.getActCount();
            }
        }
        return totalNum;
    }

    private boolean checkDetailGood(GoodsBarcodeBean good) {
        boolean result = false;
        for (DetailBarcodeBean detailBarcode : InOrderSingleton.getInstance().getDetailBarcodeList()) {
            if (detailBarcode.getBarcode().equals(good.getBarcode())) {
                for (InOrderDetailBean detail : InOrderSingleton.getInstance().getInOrderDetailList()) {
                    if (detail.getSpecificationModel().equals(good.getSpecificationModel()) && detail.getCustomerCode().equals(detail.getCustomerCode())) {
                        result = true;
                        break;
                    }
                }
                break;
            }
        }
        return result;
    }

    private boolean checkGoodEixted(GoodsBarcodeBean good) {
        boolean result = false;
        for (DetailBarcodeBean detailBarcode : InOrderSingleton.getInstance().getDetailBarcodeList()) {
            Log.e("fffff", detailBarcode.getBarcode());
            if (detailBarcode.getBarcode().equals(good.getBarcode())) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void showScanRsult(GoodsBarcodeBean good) {
        resultList.add(good);
        if (scanAdapter == null) {
            scanAdapter = new InOrderScanResultAdapter(UnLoadActivity.this, resultList);
            resultView.setAdapter(scanAdapter);
        } else {
            scanAdapter.notifyDataSetChanged();
        }

    }

}
