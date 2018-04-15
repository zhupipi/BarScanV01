package com.example.barscanv01.Unload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.InOrderScanResultAdapter;
import com.example.barscanv01.Bean.DetailBarcodeBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.InOrderBean;
import com.example.barscanv01.Bean.InOrderDetailBean;
import com.example.barscanv01.Bean.PositionBean;
import com.example.barscanv01.Bean.ReceivedGoodsBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedPositionInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.SaleLoad.SaleLoadActivity;
import com.example.barscanv01.ServiceAPI.GetPositionsByDepotService;
import com.example.barscanv01.ServiceAPI.PutGoodLoadedforPDAService;
import com.example.barscanv01.ServiceAPI.PutGoodsUnLoadService;
import com.example.barscanv01.ServiceAPI.ScanBarcodeResultService;
import com.example.barscanv01.Util.CheckInOrderDetailFinishedUtil;
import com.example.barscanv01.Util.NetOutUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UnLoadActivity extends AppCompatActivity {
    @BindView(R.id.un_load_bill_no)
    TextView billNo;
    @BindView(R.id.un_load_car_plate)
    TextView carPlate;
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
    @BindView (R.id.un_load_act_count)
    TextView actCount;
    @BindView(R.id.un_load_count)
    TextView count;


    private MyApp myApp;
    private ScanManager scanManager;
    private List<GoodsBarcodeBean> resultList;
    private InOrderScanResultAdapter scanAdapter;
    private InOrderBean inOrder;
    private List<InOrderDetailBean> detailList;

    private PositionBean position;
    private List<PositionBean> positionList;
    private Map<String, Integer> map;

    /*销邦初始化设置*/
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_load);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("卸货扫码");
        myApp = (MyApp) getApplication();
        myApp.addActivity(this);
        intialData();
        getPositionList();
        resultView.setLayoutManager(new LinearLayoutManager(this));
        resultView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        resultView.setItemAnimator(new DefaultItemAnimator());
        setListener();
    }

    private void intialData() {
        Intent intent = getIntent();
        String totalActWeight = intent.getStringExtra("actWeight");
        actWeight.setText(totalActWeight);
        billNo.setText(InOrderSingleton.getInstance().getInOrder().getInOrderNo());
        carPlate.setText(InOrderSingleton.getInstance().getInOrder().getPlateNo());
        resultList = new ArrayList<GoodsBarcodeBean>();
        inOrder = InOrderSingleton.getInstance().getInOrder();
        detailList = InOrderSingleton.getInstance().getInOrderDetailList();
        positionList = new ArrayList<PositionBean>();
        map = new HashMap<>();
        for (InOrderDetailBean detail : detailList) {
            map.put(detail.getId(), detail.getActCount());
        }
    }

    private void getPositionList() {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetPositionsByDepotService getPositionsByDepotService = retrofit.create(GetPositionsByDepotService.class);
        Call<ReceivedPositionInfo> call = getPositionsByDepotService.getPositions(myApp.getCurrentDepot().getId());
        Log.d("aaaa", myApp.getCurrentDepot().getId());
        call.enqueue(new Callback<ReceivedPositionInfo>() {
            @Override
            public void onResponse(Call<ReceivedPositionInfo> call, Response<ReceivedPositionInfo> response) {

                positionList = response.body().getAttributes().getPositionList();
            }

            @Override
            public void onFailure(Call<ReceivedPositionInfo> call, Throwable t) {
                NetOutUtil.netOut(UnLoadActivity.this,myApp);
            }
        });
    }

    private void setListener() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionList.size() > 0) {
                    ArrayList<String> positionNames = new ArrayList<String>();
                    for (PositionBean position : positionList) {
                        positionNames.add(position.getPositionName());
                    }
                    ArrayAdapter<String> adpter = new ArrayAdapter<String>(UnLoadActivity.this, android.R.layout.select_dialog_singlechoice, positionNames);
                    AlertDialog.Builder builder = new AlertDialog.Builder(UnLoadActivity.this);
                    builder.setTitle("请选择卸货目标库位");
                    builder.setSingleChoiceItems(adpter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            position = positionList.get(which);
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (resultList.size() > 0) {
                                String ids = "";
                                for (GoodsBarcodeBean good : resultList) {
                                    ids = ids + good.getId() + ",";
                                }
                                Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                                PutGoodsUnLoadService putGoodsUnLoadService = retrofit.create(PutGoodsUnLoadService.class);
                                Call<ResponseBody> call = putGoodsUnLoadService.putGoodsUnload(inOrder.getId(), inOrder.getOutOrderNo(), ids, myApp.getUserBean().getUserName(), position.getPositionNo());
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Toast.makeText(UnLoadActivity.this, "卸货货品提交成功", Toast.LENGTH_SHORT).show();
                                        CheckInOrderDetailFinishedUtil checkfinishedUtil = new CheckInOrderDetailFinishedUtil(inOrder, UnLoadActivity.this);
                                        checkfinishedUtil.checkOrderFinished();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        NetOutUtil.netOut(UnLoadActivity.this,myApp);
                                    }
                                });
                            } else {
                                Toast.makeText(UnLoadActivity.this, "请扫描需要卸车的货品", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    private void registerReceiver() {
        if (myApp.getDeviceBrand().equals("NEWLAND")) {
            IntentFilter intFilter = new IntentFilter(ScanManager.ACTION_SEND_SCAN_RESULT);
            registerReceiver(mResultReceiver, intFilter);
        } else if (myApp.getDeviceBrand().equals("SUPOIN")) {
            IntentFilter inFilter = new IntentFilter(SCN_CUST_ACTION_SCODE);
            registerReceiver(mSamDataReceiver, inFilter);
        }
    }

    private void unRegisterReceiver() {
        try {
            if (myApp.getDeviceBrand().equals("NEWLAND")) {
                unregisterReceiver(mResultReceiver);
            } else if (myApp.getDeviceBrand().equals("SUPOIN")) {
                unregisterReceiver(mSamDataReceiver);
            }
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

    private BroadcastReceiver mSamDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)) {
                String message;
                try {
                    message = intent.getStringExtra(SCN_CUST_EX_SCODE).toString().trim();
                    message=message.substring(0,message.length()-1);
                    getScanResult(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("in", e.toString());
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
                        double act_weight = Double.valueOf(actWeight.getText().toString().trim()) + Double.valueOf(good.getActWeight());
                        actWeight.setText(act_weight + "");
                    }
                } else {
                    Toast.makeText(UnLoadActivity.this, "该条码无对应货品信息", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReceivedGoodsBarcodeInfo> call, Throwable t) {
                NetOutUtil.netOut(UnLoadActivity.this,myApp);
            }
        });
    }

    private boolean checkGood(GoodsBarcodeBean good) {
        boolean result = true;
        /*需要待确定——1*/
        if (!good.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())) {
            result = false;
            Toast.makeText(UnLoadActivity.this, "该货品不在用户管理的库区", Toast.LENGTH_SHORT).show();
        } else if (!checkGoodEixted(good)) {
            result = false;
            Toast.makeText(UnLoadActivity.this, "该货品已扫", Toast.LENGTH_SHORT).show();
        } else if (!good.getStatus().equals("2")) {
            if (good.getStatus().equals("0")) {
                Toast.makeText(UnLoadActivity.this, "该货品已卸车", Toast.LENGTH_SHORT).show();
            } else if (good.getStatus().equals("1")) {
                Toast.makeText(UnLoadActivity.this, "该货品已装车，但未负重", Toast.LENGTH_SHORT).show();
            } else if (good.getStatus().equals("3")) {
                Toast.makeText(UnLoadActivity.this, "该货品已结算", Toast.LENGTH_SHORT).show();
            }
            result = false;

        } else if (!checkDetailGood(good)) {
            result = false;
            Toast.makeText(UnLoadActivity.this, "该货品不不符合卸货单明细要求", Toast.LENGTH_SHORT).show();
        } else if (getScanNum(good) + getDetailModleActNum(good) + 1 > getDetailModleNum(good)) {
            result = false;
            Toast.makeText(UnLoadActivity.this, "该货品已超出退货明细规定退货数目", Toast.LENGTH_SHORT).show();
        } else if (!checkDetailNum(good)) {
            result = false;
            Toast.makeText(UnLoadActivity.this, "该货品已超出退货明细规定退货数目", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private boolean checkDetailNum(GoodsBarcodeBean good) {
        boolean result = true;
        String customerCode = "";
        for (DetailBarcodeBean detailBarcode : InOrderSingleton.getInstance().getDetailBarcodeList()) {
            if(detailBarcode.getBarcode()!=null) {
                if (detailBarcode.getBarcode().equals(good.getBarcode())) {
                    customerCode = detailBarcode.getCustomerCode();
                    break;
                }
            }
        }
        for (InOrderDetailBean detail : InOrderSingleton.getInstance().getInOrderDetailList()) {
            if (detail.getCustomerCode().equals(customerCode)) {
                if (detail.getGoodsName().equals(good.getGoodsName()) && detail.getSpecificationModel().equals(good.getSpecificationModel())) {
                    if (map.get(detail.getId()) + 1 > detail.getCount()) {
                        result = false;
                        break;
                    } else {
                        int act_count = map.get(detail.getId()) + 1;
                        map.put(detail.getId(), act_count);
                    }
                }
            }
        }
        return result;
    }

    private int getScanNum(GoodsBarcodeBean good) {
        int totalNum = 0;
        if (resultList.size() > 0) {
            for (GoodsBarcodeBean goodBarcode : resultList) {
                if (goodBarcode.getGoodsName().equals(good.getGoodsName())&&goodBarcode.getSpecificationModel().equals(good.getSpecificationModel())) {
                totalNum = totalNum + 1;
               }
               /* if(goodBarcode.getGoodsCode().equals(good.getGoodsCode())){
                    totalNum = totalNum + 1;
                }*/
            }
        }
        return totalNum;
    }

    private int getDetailModleNum(GoodsBarcodeBean good) {
        int totalNum = 0;
        for (InOrderDetailBean detail : InOrderSingleton.getInstance().getInOrderDetailList()) {
            if (detail.getGoodsName().equals(good.getGoodsName()) && detail.getSpecificationModel().equals(good.getSpecificationModel())) {
                totalNum = totalNum + detail.getCount();
            }
        }
        return totalNum;
    }

    private int getDetailModleActNum(GoodsBarcodeBean good) {
        int totalNum = 0;
        for (InOrderDetailBean detail : InOrderSingleton.getInstance().getInOrderDetailList()) {
            if (detail.getGoodsName().equals(good.getGoodsName()) && detail.getSpecificationModel().equals(good.getSpecificationModel())) {
                totalNum = totalNum + detail.getActCount();
            }
        }
        return totalNum;
    }

    private boolean checkDetailGood(GoodsBarcodeBean good) {
        boolean result = false;
        for (DetailBarcodeBean detailBarcode : InOrderSingleton.getInstance().getDetailBarcodeList()) {
            if (detailBarcode.getBarcode() != null) {
                if (detailBarcode.getBarcode().equals(good.getBarcode())) {
                    for (InOrderDetailBean detail : InOrderSingleton.getInstance().getInOrderDetailList()) {
                        if (detail.getGoodsName().equals(good.getGoodsName()) && detail.getSpecificationModel().equals(good.getSpecificationModel()) && detail.getCustomerCode().equals(detail.getCustomerCode())) {
                            result = true;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return result;
    }

    private boolean checkGoodEixted(GoodsBarcodeBean good) {
        boolean result = true;
        for (GoodsBarcodeBean goodsBarcode : resultList) {
            if (goodsBarcode.getBarcode().equals(good.getBarcode())) {
                result = false;
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

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }
}
