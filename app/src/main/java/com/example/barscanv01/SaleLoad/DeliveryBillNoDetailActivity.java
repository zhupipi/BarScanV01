package com.example.barscanv01.SaleLoad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Bean.AreaBean;
import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.PositionBean;
import com.example.barscanv01.Bean.ReceivedDetailTotalWeightBean;
import com.example.barscanv01.Bean.ReceivedGoodsBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedPositionInfo;
import com.example.barscanv01.Bean.UserBean;
import com.example.barscanv01.Fragment.LoadOperationSelectFragment;
import com.example.barscanv01.Fragment.ScanResultFragment;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetOrderDetailWeightService;
import com.example.barscanv01.ServiceAPI.GetPositionsByDepotService;
import com.example.barscanv01.ServiceAPI.LoadOverService;
import com.example.barscanv01.ServiceAPI.PutGoodLoadNoDetailService;
import com.example.barscanv01.ServiceAPI.PutGoodLoadedforPDAService;
import com.example.barscanv01.ServiceAPI.ScanBarcodeResultService;
import com.example.barscanv01.ServiceAPI.UpdatePositionService;
import com.example.barscanv01.Util.CheckOutOrederDetailFinishedUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;

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
import retrofit2.http.POST;

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
    @BindView(R.id.delivery_bill_nodetail_confirm)
    Button confrim;
    @BindView(R.id.delivery_bill_nodetail_cancel)
    Button cancel;


    private FragmentManager fragmentManager;
    private MyApp myApp;
    private OutOrderBean outOrder;
    private DepotBean userDepot;
    private AreaBean userArea;
    private UserBean user;
    private List<PositionBean> positionList;
    private PositionBean position;
    private SoundPool soundPool;
    private Map soundMap;

    /*销邦初始化设置*/
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    private static final int SOUND_SUCCESS = 1;
    private static final int SOUND_FAIL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_bill_no_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("扫码装车/倒垛");
        myApp = (MyApp) getApplication();
        initalData();
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundMap = new HashMap<>();
        soundMap.put(SOUND_SUCCESS, soundPool.load(this, R.raw.sucess1, 1));
        soundMap.put(SOUND_FAIL, soundPool.load(this, R.raw.fail1, 1));
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryBillNoDetailActivity.this);
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
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
    }

    private void initalData() {
        outOrder = DeliveryBillSingleton.getInstance().getOutOrderBean();
        billNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo());
        plateNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo());
        userDepot = myApp.getCurrentDepot();
        userArea = myApp.getCurrentAreaBean();
        user = myApp.getUserBean();
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

    private void submitData() {
        ScanResultFragment scanResultFragment = (ScanResultFragment) fragmentManager.findFragmentById(R.id.delivery_bill_nodetail_frag_change);
        ArrayList<GoodsBarcodeBean> scanResult = scanResultFragment.getScanResultArryList();
        String tag = scanResultFragment.getTag();
        switch (tag) {
            case "OPERATION_LOAD_CAR":
                if (scanResult.size() > 0) {
                    confrim.setClickable(false);
                    String ids = "";
                    for (GoodsBarcodeBean good : scanResult) {
                        ids = ids + good.getId() + ",";
                    }
                    Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                    PutGoodLoadNoDetailService putGoodLoadNoDetailService = retrofit.create(PutGoodLoadNoDetailService.class);
                    Call<ResponseBody> call = putGoodLoadNoDetailService.putGoodLoadNoDetail(outOrder.getId(), ids, userArea.getAreaNo(), user.getUserName(), user.getId());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(DeliveryBillNoDetailActivity.this, "货品装车成功！", Toast.LENGTH_SHORT).show();
                            ScanResultFragment fragment = (ScanResultFragment) fragmentManager.findFragmentById(R.id.delivery_bill_nodetail_frag_change);
                            fragment.cleanData();
                            setResult(2);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(DeliveryBillNoDetailActivity.this, "未扫描产品条形码", Toast.LENGTH_SHORT).show();
                }
                break;
            case "OPERATION_CHANGE_DEPOT":
                if (positionList.size() > 0) {
                    confrim.setClickable(false);
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
                        ScanResultFragment scanResult = (ScanResultFragment) fragmentManager.findFragmentById(R.id.delivery_bill_nodetail_frag_change);
                        ArrayList<GoodsBarcodeBean> result = scanResult.getScanResultArryList();

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (position != null) {
                                if (result.size() > 0) {
                                    String ids="";
                                    for (GoodsBarcodeBean goodsBarcode : result) {
                                        ids=ids+goodsBarcode.getId()+",";
                                        //changePosition(goodsBarcode, position);
                                    }
                                    changePosition(ids,position);
                                }
                            }
                            scanResult.cleanData();
                            finish();
                        }
                    }).show();
                }

            default:
                break;
        }
    }

    private void changePosition(String ids, PositionBean position) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        UpdatePositionService updatePositionService = retrofit.create(UpdatePositionService.class);
        Call<ResponseBody> call = updatePositionService.updatePosition(position.getPositionNo(),ids,myApp.getUserBean().getId(),myApp.getCurrentDepot().getDepotNo(),outOrder.getOutOrderNo());
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

    private BroadcastReceiver mSamDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)) {
                String message;
                try {
                    message = intent.getStringExtra(SCN_CUST_EX_SCODE).toString().trim();
                    message = message.substring(0, message.length() - 1);
                    if (!fragmentManager.findFragmentById(R.id.delivery_bill_nodetail_frag_change).getTag().equals("OPERATION_SELECT")) {
                        getScanResult(message);
                    } else {
                        Toast.makeText(DeliveryBillNoDetailActivity.this, "请选择装车或扫码操作", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("in", e.toString());
                    Toast.makeText(DeliveryBillNoDetailActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void getScanResult(String result) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        ScanBarcodeResultService scanResultService = retrofit.create(ScanBarcodeResultService.class);
        Call<ReceivedGoodsBarcodeInfo> call = scanResultService.getGoodsBarcode(result);
        call.enqueue(new Callback<ReceivedGoodsBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedGoodsBarcodeInfo> call, Response<ReceivedGoodsBarcodeInfo> response) {
                GoodsBarcodeBean good = response.body().getAttributes().getGoodsBarcode();
                if (good != null) {
                    ScanResultFragment result = (ScanResultFragment) fragmentManager.findFragmentById(R.id.delivery_bill_nodetail_frag_change);
                    if (checkResult(good)) {
                        soundPool.play((int) (soundMap.get(SOUND_SUCCESS)), 1, 1, 1, 0, 1);
                        result.addData(good);
                        if (result.getTag().equals("OPERATION_LOAD_CAR")) {
                            float act_weight = Float.valueOf(actWeight.getText().toString().trim()) + Float.valueOf(good.getActWeight());
                            actWeight.setText(String.valueOf(act_weight));
                        }
                    }
                } else {
                    Toast.makeText(DeliveryBillNoDetailActivity.this, "该条码不存在对应的货品", Toast.LENGTH_SHORT).show();
                    soundPool.play((int) (soundMap.get(SOUND_FAIL)), 1, 1, 1, 0, 1);
                }
            }

            @Override
            public void onFailure(Call<ReceivedGoodsBarcodeInfo> call, Throwable t) {

            }
        });

    }

    private boolean checkResult(GoodsBarcodeBean good) {
        boolean result = true;
        ScanResultFragment result1 = (ScanResultFragment) fragmentManager.findFragmentById(R.id.delivery_bill_nodetail_frag_change);
        List<GoodsBarcodeBean> scanResult = result1.getScanResultArryList();
        if (!good.getDepotNo().equals(userDepot.getDepotNo())) {
            Toast.makeText(DeliveryBillNoDetailActivity.this, "该货品不在用户管理库区", Toast.LENGTH_SHORT).show();
            result = false;
        } else if (!good.getStatus().equals("0")) {
            if (good.getStatus().equals("1")) {
                soundPool.play((int) (soundMap.get(SOUND_FAIL)), 1, 1, 1, 0, 1);
                Toast.makeText(this, "该货品已装车", Toast.LENGTH_SHORT).show();
            } else if (good.getStatus().equals("2")) {
                soundPool.play((int) (soundMap.get(SOUND_FAIL)), 1, 1, 1, 0, 1);
                Toast.makeText(this, "该货品已复重", Toast.LENGTH_SHORT).show();
            } else if (good.getStatus().equals("3")) {
                soundPool.play((int) (soundMap.get(SOUND_FAIL)), 1, 1, 1, 0, 1);
                Toast.makeText(this, "该货品已结算", Toast.LENGTH_SHORT).show();
            }
            result = false;

        } else {
            for (GoodsBarcodeBean re : scanResult) {
                if (re.getId().equals(good.getId())) {
                    soundPool.play((int) (soundMap.get(SOUND_FAIL)), 1, 1, 1, 0, 1);
                    Toast.makeText(this, "该货品已扫", Toast.LENGTH_LONG).show();
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delivery_no_detail_select, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.unload((int) (soundMap.get(SOUND_SUCCESS)));
        soundPool.unload((int) (soundMap.get(SOUND_FAIL)));
        soundPool.release();
    }
}
