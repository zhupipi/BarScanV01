package com.example.barscanv01.Unload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.NoDetailUnloadAdapter;
import com.example.barscanv01.Bean.DetailBarcodeBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.PositionBean;
import com.example.barscanv01.Bean.ReceivedDetailBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedGoodsBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedPositionInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetDetailBarcodeService;
import com.example.barscanv01.ServiceAPI.GetPositionsByDepotService;
import com.example.barscanv01.ServiceAPI.PutGoodsUnLoadService;
import com.example.barscanv01.ServiceAPI.ScanBarcodeResultService;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.nlscan.android.scan.ScanManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoDetailUnLoadActivity extends AppCompatActivity {
    /*    @BindView(R.id.no_detail_unload_toolbar)
        Toolbar toolbar;*/
    @BindView(R.id.no_detail_unload_plateNo)
    TextView plateNo;
    @BindView(R.id.no_detail_unload_act_weight)
    TextView actWeight;
    @BindView(R.id.no_detail_unload_recycle_view)
    RecyclerView resultView;
    @BindView(R.id.no_detail_unload_billNo)
    TextView billNo;
    @BindView(R.id.no_detail_unload_confirm)
    Button confirmButton;
    @BindView(R.id.no_detail_unload_cancel)
    Button cancelButton;
    @BindView(R.id.no_detail_unload_act_number)
    TextView actNumber;

    /*销邦初始化设置*/
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    private List<GoodsBarcodeBean> scanResult;
    private List<DetailBarcodeBean> loadDetailBarcode;
    private OutOrderBean outOrder;
    private NoDetailUnloadAdapter noDetailUnloadAdapter;
    private MyApp myApp;
    private PositionBean position;
    private List<PositionBean> positionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_detail_un_load);
        ButterKnife.bind(this);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("发货单卸车");
        myApp = (MyApp) getApplication();
        initalData();
        getDetailBarocde();
        getPositionList();
        resultView.setLayoutManager(new LinearLayoutManager(this));
        resultView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        resultView.setItemAnimator(new DefaultItemAnimator());
        setListener();
    }

    /*扫完退货产品后，选择库位*/
    private void setListener() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scanResult.size() > 0) {
                    ArrayAdapter adapter;
                    adapter = getPositionAdapter(positionList);
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoDetailUnLoadActivity.this);
                    builder.setTitle("请选择退货库位")
                            .setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    position = positionList.get(which);
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submitData(scanResult);
                                }
                            }).show();
                } else {
                    Toast.makeText(NoDetailUnLoadActivity.this, "请扫描需要卸货产品的条形码", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoDetailUnLoadActivity.this.finish();
            }
        });
    }

    /*获取条码信息*/
    private void getDetailBarocde() {
        if (outOrder != null) {
            Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
            GetDetailBarcodeService getDetailBarcodeService = retrofit.create(GetDetailBarcodeService.class);
            Call<ReceivedDetailBarcodeInfo> call = getDetailBarcodeService.getDetailBarcodesById(outOrder.getId());
            call.enqueue(new Callback<ReceivedDetailBarcodeInfo>() {
                @Override
                public void onResponse(Call<ReceivedDetailBarcodeInfo> call, Response<ReceivedDetailBarcodeInfo> response) {
                    if (response.body().getAttributes().getDetailBarcodeEntityList() != null && response.body().getAttributes().getDetailBarcodeEntityList().size() > 0) {
                        loadDetailBarcode = response.body().getAttributes().getDetailBarcodeEntityList();
                    } else {
                        Toast.makeText(NoDetailUnLoadActivity.this, "该装车单未装车", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReceivedDetailBarcodeInfo> call, Throwable t) {
                    Toast.makeText(NoDetailUnLoadActivity.this, "获取该装车单的装车信息失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /*获取用户工作库区的库位信息*/
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

            }
        });
    }

    private ArrayAdapter getPositionAdapter(List<PositionBean> positions) {
        ArrayList<String> positionNames = new ArrayList<>();
        for (PositionBean position : positions) {
            positionNames.add(position.getPositionName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_singlechoice, positionNames);
        return adapter;
    }

    private void initalData() {
        if (InOrderSingleton.getInstance().getNoDetailOutOrder() != null) {
            plateNo.setText(InOrderSingleton.getInstance().getNoDetailOutOrder().getPlateNo());
            billNo.setText(InOrderSingleton.getInstance().getNoDetailOutOrder().getOutOrderNo());
            actWeight.setText("0t");
            actNumber.setText("0");
        }
        scanResult = new ArrayList<GoodsBarcodeBean>();
        outOrder = InOrderSingleton.getInstance().getNoDetailOutOrder();
        loadDetailBarcode = new ArrayList<DetailBarcodeBean>();
        positionList = new ArrayList<PositionBean>();
    }

    private BroadcastReceiver mSamDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)) {
                String message;
                try {
                    message = intent.getStringExtra(SCN_CUST_EX_SCODE).toString().trim();
                    message = message.substring(0, message.length() - 1);
                    getScanResult(message);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("in", e.toString());
                    Toast.makeText(NoDetailUnLoadActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
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
                        showScanResult(good);
                        String weight_temp = actWeight.getText().toString().trim();
                        weight_temp = weight_temp.substring(0, weight_temp.length() - 1);
                        float act_weight = Float.valueOf(weight_temp) + good.getAdjustWeight();
                        String act_number_temp = actNumber.getText().toString().trim();
                        int act_number = Integer.valueOf(act_number_temp) + 1;
                        actWeight.setText(act_weight + "t");
                        actNumber.setText(act_number + "");
                    }
                } else {
                    Toast.makeText(NoDetailUnLoadActivity.this, "改条码无对应的货品信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedGoodsBarcodeInfo> call, Throwable t) {

            }
        });
    }

    private boolean checkGood(GoodsBarcodeBean good) {
        boolean result = true;
        if (!good.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())) {
            result = false;
            Toast.makeText(NoDetailUnLoadActivity.this, "该货品已扫描", Toast.LENGTH_SHORT).show();
        } else if (!good.getStatus().equals("1")) {
            if (good.getStatus().equals("0")) {
                Toast.makeText(NoDetailUnLoadActivity.this, "该货品并未装车", Toast.LENGTH_SHORT).show();
            } else if (good.getStatus().equals("2")) {
                Toast.makeText(NoDetailUnLoadActivity.this, "该货品已复重", Toast.LENGTH_SHORT).show();
            } else if (good.getStatus().equals("3")) {
                Toast.makeText(NoDetailUnLoadActivity.this, "该货品结算", Toast.LENGTH_SHORT).show();
            }
            result = false;
            Toast.makeText(NoDetailUnLoadActivity.this, "该货品并未装车", Toast.LENGTH_SHORT).show();
        } else if (checkExist(good)) {
            result = false;
            Toast.makeText(NoDetailUnLoadActivity.this, "该货品并未在装车明细中", Toast.LENGTH_SHORT).show();
        } else if (checkGoodScaned(good)) {
            result = false;
            Toast.makeText(NoDetailUnLoadActivity.this, "该货品已扫描", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private boolean checkGoodScaned(GoodsBarcodeBean good) {
        boolean result = false;
        for (GoodsBarcodeBean goodBarcode : scanResult) {
            if (goodBarcode.getBarcode().equals(good.getBarcode())) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean checkExist(GoodsBarcodeBean good) {
        boolean result = true;
        if (loadDetailBarcode.size() > 0) {
            for (DetailBarcodeBean detail : loadDetailBarcode) {
                if (detail.getBarcode() != null) {
                    if (detail.getBarcode().equals(good.getBarcode())) {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }

    private void showScanResult(GoodsBarcodeBean good) {
        scanResult.add(good);
        if (noDetailUnloadAdapter == null) {
            noDetailUnloadAdapter = new NoDetailUnloadAdapter(NoDetailUnLoadActivity.this, scanResult, loadDetailBarcode);
            resultView.setAdapter(noDetailUnloadAdapter);
        } else {
            noDetailUnloadAdapter.notifyDataSetChanged();
        }
    }

    private void submitData(List<GoodsBarcodeBean> scanResult) {
        if (scanResult.size() > 0) {
            String ids = "";
            for (GoodsBarcodeBean goods : scanResult) {
                if (goods != null) {
                    ids = ids + goods.getId() + ",";
                }
            }
            Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
            PutGoodsUnLoadService unloadService = retrofit.create(PutGoodsUnLoadService.class);
            Call<ResponseBody> call = unloadService.putGoodsUnloadNoDetail(outOrder.getId(), ids, position.getPositionNo());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(NoDetailUnLoadActivity.this, "货品已成功卸在" + myApp.getCurrentDepot().getDepotName() + "-" + position.getPositionName() + "中", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "请扫描卸货货品条形码", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerReceiver() {
        IntentFilter inFilter = new IntentFilter(SCN_CUST_ACTION_SCODE);
        registerReceiver(mSamDataReceiver, inFilter);
    }

    private void unRegisterReceiver() {
        try {
            unregisterReceiver(mSamDataReceiver);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
