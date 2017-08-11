package com.example.barscanv01.SaleLoad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.PositionBean;
import com.example.barscanv01.Bean.ReceivedGoodsBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedPositionInfo;
import com.example.barscanv01.Fragment.LoadOperationSelectFragment;
import com.example.barscanv01.Fragment.ScanResultFragment;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetPositionsByDepotService;
import com.example.barscanv01.ServiceAPI.LoadOverByDepotService;
import com.example.barscanv01.ServiceAPI.LoadOverService;
import com.example.barscanv01.ServiceAPI.PutGoodLoadedforPDAService;
import com.example.barscanv01.ServiceAPI.ScanBarcodeResultService;
import com.example.barscanv01.ServiceAPI.UpdatePositionService;
import com.example.barscanv01.Util.CheckOutOrederDetailFinishedUtil;
import com.example.barscanv01.Util.GetCountUtil;
import com.example.barscanv01.Util.GoodsManageUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.example.barscanv01.Util.WriteBizlogUtil;
import com.example.barscanv01.Util.WriteDetailBarcodeUtil;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SaleLoadActivity extends AppCompatActivity {
    @BindView(R.id.load_outOrder_No)
    TextView outOrderNo;
    @BindView(R.id.load_car_plate)
    TextView carPlate;
    @BindView(R.id.load_weight)
    TextView weight;
    @BindView(R.id.load_act_weight)
    TextView actWeight;
    @BindView(R.id.customer_load_toolbar)
    Toolbar toolbar;
    @BindView(R.id.customer_load_change_fragment)
    FrameLayout fragmentChange;
    @BindView(R.id.customer_load_confrim)
    Button confirm;
    @BindView(R.id.customer_load_cancel)
    Button cancel;
    @BindView(R.id.sale_load_result_show)
    LinearLayout resultShow;


    private FragmentManager fragmentManager;
    private MyApp myApp;
    private ScanManager scanManager;
    private List<PositionBean> positionList;
    private PositionBean position;

    private AlertDialog alertDialog;
    private OutOrderBean outOrder;
    private List<OutOrderDetailBean> detailList;
    private DepotBean currentDepot;

    /*销邦初始化设置*/
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_load);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("扫码装车/倒垛");
        myApp = (MyApp) getApplication();
        intitalData();
        positionList = new ArrayList<PositionBean>();
        getPositionList();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        LoadOperationSelectFragment loadOperationSelectFragment = new LoadOperationSelectFragment();
        loadOperationSelectFragment.setOnItemClickListener(new LoadOperationSelectFragment.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String tag) {
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                ScanResultFragment scanResultFragment = new ScanResultFragment();
                transaction1.replace(R.id.customer_load_change_fragment, scanResultFragment, tag);
                transaction1.commit();
            }
        });
        transaction.add(R.id.customer_load_change_fragment, loadOperationSelectFragment, "OPERATION_SELECT");
        transaction.commit();
        initalScanSetting();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.out_order_depot_over) {
                    AlertDialog.Builder outOrderDetailOverbuilder = new AlertDialog.Builder(SaleLoadActivity.this);
                    outOrderDetailOverbuilder.setTitle("注意");
                    outOrderDetailOverbuilder.setMessage("您确定该车辆在此库位所有货品装车完成");
                    outOrderDetailOverbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            Retrofit retrofit = new RetrofitBuildUtil().retrofit;
                            LoadOverByDepotService loadOverByDepotService = retrofit.create(LoadOverByDepotService.class);
                            Call<ResponseBody> call = loadOverByDepotService.loadedByDepot(outOrder.getId(), currentDepot.getDepotNo());
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    CheckOutOrederDetailFinishedUtil checkFinishedUtil = new CheckOutOrederDetailFinishedUtil(outOrder, SaleLoadActivity.this);
                                    checkFinishedUtil.checkOutOrderFinished();
                                    Toast.makeText(SaleLoadActivity.this, "该车辆在该库区装车完成", Toast.LENGTH_SHORT).show();
                                    setResult(1);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                            WriteBizlogUtil wirteBizlog = new WriteBizlogUtil(SaleLoadActivity.this);
                            wirteBizlog.writeDepotLoadFinishedLog();

                        }
                    });
                } else if (id == R.id.out_order_total_over) {
                    AlertDialog.Builder outOrderOverBuild = new AlertDialog.Builder(SaleLoadActivity.this);
                    outOrderOverBuild.setTitle("注意")
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
                                            CheckOutOrederDetailFinishedUtil checkFinished = new CheckOutOrederDetailFinishedUtil(outOrder, SaleLoadActivity.this);
                                            checkFinished.outOrderFinised();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                    //GoodsManageUtil goodsManageUtil = new GoodsManageUtil(SaleLoadActivity.this);
                                }
                            })
                            .show();
                }
                return true;
            }
        });
    }

    private void initalScanSetting() {
        if(myApp.getDeviceBrand().equals("NEWLAND")){
            scanManager = ScanManager.getInstance();
            scanManager.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_BROADCAST);
        }else if(Build.BRAND.equals("SUPOIN")){
           LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) resultShow.getLayoutParams();
            params.height=300;
            resultShow.setLayoutParams(params);
        }
    }

    private void intitalData() {
        outOrderNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo());
        carPlate.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo());
        showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean());
        outOrder=DeliveryBillSingleton.getInstance().getOutOrderBean();
        detailList=DeliveryBillSingleton.getInstance().getOutOrderDetailBean();
        currentDepot=myApp.getCurrentDepot();
    }

    private void showOutOrderWeight(List<OutOrderDetailBean> outOrderDetailBeanList) {
        float totalWeight=0;
        float totalActWeight=0;
        for(OutOrderDetailBean detail:outOrderDetailBeanList){
            totalWeight=totalWeight+detail.getWeight();
            if(detail.getActWeight()==null){
                totalActWeight=totalActWeight+0;
            }else {
                totalActWeight=totalActWeight+Float.valueOf(detail.getActWeight());
            }
        }
        weight.setText(String.valueOf(totalWeight));
        actWeight.setText(String.valueOf(totalActWeight));
    }

    private void submitData() {
        String tag = fragmentManager.findFragmentById(R.id.customer_load_change_fragment).getTag();
        if (tag.equals("OPERATION_SELECT")) {
            Toast.makeText(this, "请选择装车操作，扫描产品条形码", Toast.LENGTH_SHORT).show();
        } else {
            ScanResultFragment fragment = (ScanResultFragment) fragmentManager.findFragmentById(R.id.customer_load_change_fragment);
            ArrayList<GoodsBarcodeBean> scanResult = new ArrayList<GoodsBarcodeBean>();
            scanResult = fragment.getScanResultArryList();
            switch (tag) {
                case "OPERATION_LOAD_CAR":
                    if (scanResult.size() > 0) {
                        String Ids="";
                        for (GoodsBarcodeBean good : scanResult) {
                            Ids=Ids+good.getId()+",";
                        }
                        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                        PutGoodLoadedforPDAService goodLoadedforPDAService = retrofit.create(PutGoodLoadedforPDAService.class);
                        Call<ResponseBody> call=goodLoadedforPDAService.putGoodsLoaded(outOrder.getId(),Ids,myApp.getCurrentAreaBean().getAreaNo(),myApp.getUserBean().getUserName());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                CheckOutOrederDetailFinishedUtil checkFinishedUtil = new CheckOutOrederDetailFinishedUtil(outOrder, SaleLoadActivity.this);
                                checkFinishedUtil.checkOutOrderFinished();
                                Toast.makeText(SaleLoadActivity.this,"货品装车成功！",Toast.LENGTH_SHORT).show();
                                ScanResultFragment fragment = (ScanResultFragment) fragmentManager.findFragmentById(R.id.customer_load_change_fragment);
                                fragment.cleanData();
                                setResult(1);
                                finish();
                                /*需在服务器后台，修改AraeName和DepotName*/
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });


                    } else {
                        Toast.makeText(this, "没有扫描货品条形码", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "OPERATION_CHANGE_DEPOT":
                    if (positionList.size() > 0) {
                        ArrayList<String> positionNames = new ArrayList<String>();
                        for (PositionBean position : positionList) {
                            positionNames.add(position.getPositionName());
                        }
                        ArrayAdapter<String> adpter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, positionNames);
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("请选择倒垛目标库位");
                        builder.setSingleChoiceItems(adpter, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                position = positionList.get(which);
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            ScanResultFragment sresultfg = (ScanResultFragment) fragmentManager.findFragmentById(R.id.customer_load_change_fragment);
                            ArrayList<GoodsBarcodeBean> sresult = sresultfg.getScanResultArryList();

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (position != null) {
                                    if (sresult.size() > 0) {
                                        for (GoodsBarcodeBean good1 : sresult) {
                                            changeDepot(good1, position);
                                        }
                                    }
                                }
                                sresultfg.cleanData();
                                finish();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void changeDepot(GoodsBarcodeBean good1, PositionBean position) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        UpdatePositionService updatePositionService = retrofit.create(UpdatePositionService.class);
        Call<ResponseBody> call = updatePositionService.updatePosition(good1.getId(), position.getPositionNo());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

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

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    private void registerReceiver() {
        if(myApp.getDeviceBrand().equals("NEWLAND")) {
            IntentFilter intFilter = new IntentFilter(ScanManager.ACTION_SEND_SCAN_RESULT);
            registerReceiver(mResultReceiver, intFilter);
        }else if(myApp.getDeviceBrand().equals("SUPOIN")){
            IntentFilter inFilter=new IntentFilter(SCN_CUST_ACTION_SCODE);
            registerReceiver(mSamDataReceiver,inFilter);
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
                    if (!fragmentManager.findFragmentById(R.id.customer_load_change_fragment).getTag().equals("OPERATION_SELECT")) {
                        getScanResult(result);
                    }else{
                        Toast.makeText(SaleLoadActivity.this,"请选择装车或扫码操作",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SaleLoadActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
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
                    if (!fragmentManager.findFragmentById(R.id.customer_load_change_fragment).getTag().equals("OPERATION_SELECT")) {
                        getScanResult(message);
                    }else{
                        Toast.makeText(SaleLoadActivity.this,"请选择装车或扫码操作",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("in", e.toString());
                    Toast.makeText(SaleLoadActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void getScanResult(final String barcode) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        ScanBarcodeResultService scanBarcodeResultService = retrofit.create(ScanBarcodeResultService.class);
        Call<ReceivedGoodsBarcodeInfo> call = scanBarcodeResultService.getGoodsBarcode(barcode);
        call.enqueue(new Callback<ReceivedGoodsBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedGoodsBarcodeInfo> call, Response<ReceivedGoodsBarcodeInfo> response) {
                final GoodsBarcodeBean good;
                good = response.body().getAttributes().getGoodsBarcode();
                if (good != null) {
                    Log.d("aaaa", good.getId());
                    final ScanResultFragment result = (ScanResultFragment) fragmentManager.findFragmentById(R.id.customer_load_change_fragment);
                    if (checkGood(good, result.getTag())) {
                        if (result.getTag().equals("OPERATION_LOAD_CAR")) {
                            result.addData(good);
                            float act_weight=Float.valueOf(actWeight.getText().toString().trim())+Float.valueOf(good.getActWeight());
                            actWeight.setText(String.valueOf(act_weight));
                        } else {
                            if (checkGoodSpecialMode(good)) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(SaleLoadActivity.this);
                                builder1.setTitle("注意")
                                        .setMessage("该货品可以直接装车，确认是否需要将其倒垛")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                AlertDialog.Builder builder2 = new AlertDialog.Builder(SaleLoadActivity.this);
                                                builder2.setTitle("注意")
                                                        .setMessage("确定将其倒垛？")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                AlertDialog.Builder builder3 = new AlertDialog.Builder(SaleLoadActivity.this);
                                                                builder3.setTitle("注意")
                                                                        .setMessage("再次确定将其倒垛？")
                                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                result.addData(good);
                                                                            }
                                                                        }).show();
                                                            }
                                                        }).show();
                                            }
                                        }).show();
                            } else {
                                result.addData(good);
                            }
                        }
                    }
                } else {
                    Toast.makeText(SaleLoadActivity.this, "扫码不存在", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReceivedGoodsBarcodeInfo> call, Throwable t) {

            }
        });
    }

    private boolean checkGood(GoodsBarcodeBean good, String tag) {
        boolean checkresult = true;
        ScanResultFragment scanResult = (ScanResultFragment) fragmentManager.findFragmentById(R.id.customer_load_change_fragment);
        List<GoodsBarcodeBean> result = scanResult.getScanResultArryList();
        switch (tag) {
            case "OPERATION_LOAD_CAR":
                if (!good.getStatus().equals("0")) {
                    Toast.makeText(this, "该货品已装车", Toast.LENGTH_SHORT).show();
                    checkresult = false;
                    break;
                } else {
                    for (GoodsBarcodeBean re : result) {
                        if (re.getId().equals(good.getId())) {
                            Toast.makeText(this, "该货品已扫", Toast.LENGTH_LONG).show();
                            checkresult = false;
                            break;
                        }
                    }
                    if (checkresult == false) {
                        break;
                    }
                }
                if (!checkGoodSpecialMode(good)) {
                    Toast.makeText(this, "该货品规格与此次装车规格不符", Toast.LENGTH_LONG).show();
                    checkresult = false;
                    break;
                }
                GetCountUtil getCountUtil=new GetCountUtil(detailList,good);
                if(getCountUtil.getActCount()+getCountUtil.getResultCount(result)+1>getCountUtil.getCount()){
                    Toast.makeText(this,"该规格货品扫码数目已经超过发货单规定装车数目",Toast.LENGTH_SHORT).show();
                    checkresult=false;
                    break;
                }
                break;

            case "OPERATION_CHANGE_DEPOT":
                if (!good.getStatus().equals("0")) {
                    Toast.makeText(this, "该货品已装车", Toast.LENGTH_SHORT).show();
                    checkresult = false;
                    break;
                } else {
                    for (GoodsBarcodeBean re : result) {
                        if (re.getId().equals(good.getId())) {
                            Toast.makeText(this, "该货品已扫", Toast.LENGTH_LONG).show();
                            checkresult = false;
                            break;
                        }
                    }
                    if (checkresult == false) {
                        break;
                    }
                }
                break;

            default:
                break;
        }
        return checkresult;
    }

    private boolean checkGoodSpecialMode(GoodsBarcodeBean good) {
        boolean result=false;
        for(OutOrderDetailBean detail:detailList){
            if(detail.getSpecificationModel().equals(good.getSpecificationModel())){
                result=true;
                break;
            }
        }
        return result;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loadover_select, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        super.onBackPressed();
    }
}
