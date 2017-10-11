package com.example.barscanv01.Translate;

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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.ScanResultAdapter;
import com.example.barscanv01.Adapter.TranslateScanResultAdapter;
import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.PositionBean;
import com.example.barscanv01.Bean.ReceiveDepotByAreaInfo;
import com.example.barscanv01.Bean.ReceivedGoodsBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedPositionInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetDepotInfoByAreaService;
import com.example.barscanv01.ServiceAPI.GetPositionsByDepotService;
import com.example.barscanv01.ServiceAPI.PutGoodChangeDepotService;
import com.example.barscanv01.ServiceAPI.PutGoodsUnpackService;
import com.example.barscanv01.ServiceAPI.ScanBarcodeResultService;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TranslateActivity extends AppCompatActivity {

    @BindView(R.id.translate_confirm)
    Button comfirmButton;
    @BindView(R.id.translate_cancel)
    Button cancelButton;
    @BindView(R.id.translate_result_view)
    RecyclerView scanResultView;


    private List<GoodsBarcodeBean> scanResult;
    private MyApp myApp;
    private ScanManager scanManager;
    private TranslateScanResultAdapter adpter;
    private List<DepotBean> depotList;
    private Map<Integer, List<PositionBean>> map;
    private DepotBean selectedDepot;
    private PositionBean selectedPosition;


    /*销邦初始化设置*/
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        ButterKnife.bind(this);
/*        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("产成品移库");*/
        myApp = (MyApp) getApplication();
        scanResult = new ArrayList<GoodsBarcodeBean>();
        scanResultView.setLayoutManager(new LinearLayoutManager(this));
        scanResultView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        scanResultView.setItemAnimator(new DefaultItemAnimator());
        depotList = new ArrayList<DepotBean>();
        map = new HashMap<>();
        getDepotInfo();
        setListener();
    }

    private void setListener() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scanResult.size() > 0) {
                    showAlertDialog();
                } else {
                    Toast.makeText(TranslateActivity.this, "请扫描需要拆包的产品条形码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        comfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scanResult.size() > 0) {
                    showPopWindow();
                } else {
                    Toast.makeText(TranslateActivity.this, "请扫描需要移库的产品条形码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDepotInfo() {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetDepotInfoByAreaService getDepotService = retrofit.create(GetDepotInfoByAreaService.class);
        Call<ReceiveDepotByAreaInfo> call = getDepotService.getDepot(myApp.getCurrentAreaBean().getId());
        call.enqueue(new Callback<ReceiveDepotByAreaInfo>() {
            @Override
            public void onResponse(Call<ReceiveDepotByAreaInfo> call, Response<ReceiveDepotByAreaInfo> response) {
                depotList = response.body().getAttributes().getDepotList();
                //Toast.makeText(TranslateActivity.this,depotList.size()+"",Toast.LENGTH_SHORT).show();
                if (depotList.size() > 0) {
                    for (final DepotBean depot : depotList) {
                        Retrofit retrofit1 = new RetrofitBuildUtil().getRetrofit();
                        GetPositionsByDepotService getPositionService = retrofit1.create(GetPositionsByDepotService.class);
                        Call<ReceivedPositionInfo> call1 = getPositionService.getPositions(depot.getId());
                        call1.enqueue(new Callback<ReceivedPositionInfo>() {
                            @Override
                            public void onResponse(Call<ReceivedPositionInfo> call, Response<ReceivedPositionInfo> response) {
                                List<PositionBean> positionList = new ArrayList<PositionBean>();
                                positionList = response.body().getAttributes().getPositionList();
                                map.put(depotList.indexOf(depot), positionList);

                            }

                            @Override
                            public void onFailure(Call<ReceivedPositionInfo> call, Throwable t) {
                                Toast.makeText(TranslateActivity.this, "获取库位信息失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } else {
                    Toast.makeText(TranslateActivity.this, "获取库房信息失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceiveDepotByAreaInfo> call, Throwable t) {
                Toast.makeText(TranslateActivity.this, "获取库房信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showPopWindow() {
        if (depotList.size() > 0) {
            View conttextView = LayoutInflater.from(TranslateActivity.this).inflate(R.layout.translate_pop, null);
            TextView areaName = (TextView) conttextView.findViewById(R.id.translate_pop_area_name);
            Spinner depotSpinner = (Spinner) conttextView.findViewById(R.id.translate_pop_depot_spinner);
            final Spinner positionSpinner = (Spinner) conttextView.findViewById(R.id.translate_pop_position_spinner);
            areaName.setText(myApp.getCurrentAreaBean().getAreaName() + ":");
            depotSpinner.setAdapter(getDepotAdapter(depotList));
            depotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    positionSpinner.setAdapter(getPositionAdapter(map.get(position)));
                    selectedDepot = depotList.get(position);
                    positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedPosition = map.get(depotList.indexOf(selectedDepot)).get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(conttextView)
                    .setTitle("请选择移库的目标库位:")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            submitData();
                        }
                    }).show();
        }
    }

    private void submitData() {
        if (scanResult.size() > 0) {
            String id = "";
            for (GoodsBarcodeBean good : scanResult) {
                id = id + good.getId() + ",";
            }
            Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
            PutGoodChangeDepotService goodChangeDepotService = retrofit.create(PutGoodChangeDepotService.class);
            Call<ResponseBody> call = goodChangeDepotService.changeDepot(id, selectedDepot.getDepotNo(), selectedPosition.getPositionNo());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(TranslateActivity.this, "产成品移库成功", Toast.LENGTH_SHORT).show();
                    scanResult.clear();
                    showResult();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注意")
                .setMessage("确定将扫描产品拆包？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unpacked();
                    }
                }).show();
    }

    private void unpacked() {
        String ids = "";
        for (GoodsBarcodeBean goods : scanResult) {
            ids = ids + goods.getId() + ",";
        }
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        PutGoodsUnpackService putGoodsUnpackService = retrofit.create(PutGoodsUnpackService.class);
        Call<ResponseBody> call = putGoodsUnpackService.putGoodsUnpacked("4", ids);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(TranslateActivity.this, scanResult.size() + "件货品拆包成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TranslateActivity.this, "货品拆包提交失败", Toast.LENGTH_SHORT).show();
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
                    getGood(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("in", e.toString());
                    Toast.makeText(TranslateActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void getGood(String barcode) {

        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        ScanBarcodeResultService scanBarcodeResultService = retrofit.create(ScanBarcodeResultService.class);
        Call<ReceivedGoodsBarcodeInfo> call = scanBarcodeResultService.getGoodsBarcode(barcode);
        call.enqueue(new Callback<ReceivedGoodsBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedGoodsBarcodeInfo> call, Response<ReceivedGoodsBarcodeInfo> response) {
                if (response.body().getAttributes().getGoodsBarcode() != null) {
                    GoodsBarcodeBean good = response.body().getAttributes().getGoodsBarcode();
                    if (checkGoodScaned(good)) {
                        scanResult.add(good);
                        showResult();
                    } else {
                        Toast.makeText(TranslateActivity.this, "改条码已扫描", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TranslateActivity.this, "改条码无对应的货品信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedGoodsBarcodeInfo> call, Throwable t) {
                Toast.makeText(TranslateActivity.this, "获取条码信息失败", Toast.LENGTH_SHORT);
            }
        });
    }

    private void showResult() {
        if (adpter == null) {
            adpter = new TranslateScanResultAdapter(TranslateActivity.this, scanResult);
            scanResultView.setAdapter(adpter);
            adpter.setOnItemClickListener(new ScanResultAdapter.OnItemClickListener() {
                @Override
                public void OnItemLongClick(View view, int postion) {
                    showPopMenu(view, postion);
                }
            });
        } else {
            adpter.notifyDataSetChanged();
        }
    }

    private boolean checkGoodScaned(GoodsBarcodeBean good) {
        boolean result = true;
        if (scanResult.size() > 0) {
            for (GoodsBarcodeBean goodsBarcode : scanResult) {
                if (goodsBarcode.getBarcode().equals(good.getBarcode())) {
                    result = false;
                    break;
                }
            }
        }
        if (!good.getStatus().equals("0")) {
            result = false;
            String status = good.getStatus();
            switch (status) {
                case "1":
                    Toast.makeText(TranslateActivity.this, "该货品已经装车", Toast.LENGTH_SHORT).show();
                    break;
                case "2":
                    Toast.makeText(this, "该货品已经负重", Toast.LENGTH_SHORT).show();
                    break;
                case "3":
                    Toast.makeText(this, "该货品已经结算", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "该货品不允许移库", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
        return result;
    }

    private void showPopMenu(View view, final int postion) {
        PopupMenu popupMenu = new PopupMenu(TranslateActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.delete_menu_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                deleteData(postion);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    public ArrayAdapter<String> getDepotAdapter(List<DepotBean> depotBeanList) {
        ArrayList<String> depotNames = new ArrayList<String>();
        for (DepotBean depot : depotBeanList) {
            depotNames.add(depot.getDepotName());
        }
        ArrayAdapter<String> depotAdapter = new ArrayAdapter<String>(TranslateActivity.this, android.R.layout.simple_list_item_1, depotNames);
        return depotAdapter;
    }

    public ArrayAdapter<String> getPositionAdapter(List<PositionBean> positionBeanList) {
        ArrayList<String> positionNames = new ArrayList<String>();
        for (PositionBean position : positionBeanList) {
            positionNames.add(position.getPositionName());
        }
        ArrayAdapter<String> positionAdapter = new ArrayAdapter<String>(TranslateActivity.this, android.R.layout.simple_list_item_1, positionNames);
        return positionAdapter;
    }

    private void deleteData(int postion) {
        scanResult.remove(postion);
        adpter.notifyDataSetChanged();
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
}
