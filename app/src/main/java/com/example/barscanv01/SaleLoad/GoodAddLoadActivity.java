package com.example.barscanv01.SaleLoad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Bean.CustomerBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.GoodsManageDetailBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedGoodsBarcodeInfo;
import com.example.barscanv01.Fragment.LoadOperationSelectFragment;
import com.example.barscanv01.Fragment.ScanResultFragment;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.PutAddGoodLoadService;
import com.example.barscanv01.ServiceAPI.ScanBarcodeResultService;
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

public class GoodAddLoadActivity extends AppCompatActivity {
    @BindView(R.id.good_add_load_toolbar)
    Toolbar toolbar;
    @BindView(R.id.good_add_load_order_No)
    TextView orderNo;
    @BindView(R.id.good_add_load_car_plate)
    TextView carPlate;
    @BindView(R.id.good_add_load_confrim)
    Button cofrimButton;
    @BindView(R.id.good_add_load_cancel)
    Button cancelButton;
    @BindView(R.id.good_add_load_weight)
    TextView actWeight;

    private List<GoodsBarcodeBean> scanResult;
    private List<GoodsManageDetailBean> goodsManageDetailList;
    private FragmentManager fragmentManager;
    private MyApp myApp;
    private List<CustomerBean> customerList;
    private CustomerBean customer;

    /*销邦初始化设置*/
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_add_load);
        ButterKnife.bind(this);
        myApp = (MyApp) getApplication();
        scanResult = new ArrayList<GoodsBarcodeBean>();
        goodsManageDetailList = new ArrayList<GoodsManageDetailBean>();
        customerList = new ArrayList<CustomerBean>();
        intialData();
        intialFragment();
        getCustomerList();
        setListener();
    }

    private void intialData() {
        actWeight.setText(String.valueOf(getIntent().getExtras().getDouble("act_weight")));
        orderNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo());
        carPlate.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo());
        goodsManageDetailList = DeliveryBillSingleton.getInstance().getGoodsManageDetailList();
    }

    private void intialFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        LoadOperationSelectFragment loadOperationSelectFragment = new LoadOperationSelectFragment();
        loadOperationSelectFragment.setOnItemClickListener(new LoadOperationSelectFragment.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String tag) {
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                ScanResultFragment scanResultFragment = new ScanResultFragment();
                transaction1.replace(R.id.good_add_load_scan_result_frg, scanResultFragment, tag);
                transaction1.commit();
            }
        });
        transaction.add(R.id.good_add_load_scan_result_frg, loadOperationSelectFragment, "OPERATION_SELECT");
        transaction.commit();
    }

    private void getCustomerList() {
        for (OutOrderDetailBean outOrderDetail : DeliveryBillSingleton.getInstance().getOutOrderDetailBean()) {
            CustomerBean customer = new CustomerBean(outOrderDetail.getCustomerCode(), outOrderDetail.getCustomerName(), outOrderDetail.getAddress());
            if (customerList.size() == 0) {
                customerList.add(customer);
            } else {
                boolean result = true;
                for (CustomerBean customerlist : customerList) {
                    if (customerlist.getCustomerCode().equals(customer.getCustomerCode())) {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    customerList.add(customer);
                }
            }
        }
    }

    private void setListener() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cofrimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scanResult.size() > 0) {
                    submitData();
                } else {
                    Toast.makeText(GoodAddLoadActivity.this, "请扫描货品条码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void submitData() {
        if (customerList.size() == 0) {
            getCustomerList();
        }
        ArrayList<String> customerName = new ArrayList<String>();
        for (CustomerBean customer : customerList) {
            customerName.add(customer.getCustomerName());
        }
        ArrayAdapter<String> customerAdapter = new ArrayAdapter<String>(GoodAddLoadActivity.this, android.R.layout.simple_list_item_1, customerName);
        showPopWindow(customerAdapter);
    }

    private void showPopWindow(ArrayAdapter<String> customerAdapter) {
        View contextView = LayoutInflater.from(GoodAddLoadActivity.this).inflate(R.layout.add_good_customer_pop, null);
        Spinner customerSpinner = (Spinner) contextView.findViewById(R.id.add_good_customer_spinnner);
        final TextView address = (TextView) contextView.findViewById(R.id.add_good_customer_address);
        customerSpinner.setAdapter(customerAdapter);
        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customer = customerList.get(position);
                address.setText(customer.getAddress());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AlertDialog.Builder bulilder = new AlertDialog.Builder(this);
        bulilder.setView(contextView)
                .setTitle("注意")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ids = "";
                        for (GoodsBarcodeBean goods : scanResult) {
                            ids = ids + goods.getId() + ",";
                        }
                        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                        PutAddGoodLoadService putAddGoodLoadService = retrofit.create(PutAddGoodLoadService.class);
                        Call<ResponseBody> call = putAddGoodLoadService.putAddGoodLoad(DeliveryBillSingleton.getInstance().getOutOrderBean().getId(), ids, customer.getCustomerCode(), customer.getCustomerName(), customer.getAddress(), myApp.getUserBean().getUserName());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(GoodAddLoadActivity.this, "加货货物装车提交成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(GoodAddLoadActivity.this, "加货货物装车提交失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).show();
    }


    public void getScanResult(String barcode) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        ScanBarcodeResultService scanBarcodeResultService = retrofit.create(ScanBarcodeResultService.class);
        Call<ReceivedGoodsBarcodeInfo> call = scanBarcodeResultService.getGoodsBarcode(barcode);
        call.enqueue(new Callback<ReceivedGoodsBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedGoodsBarcodeInfo> call, Response<ReceivedGoodsBarcodeInfo> response) {
                GoodsBarcodeBean good = response.body().getAttributes().getGoodsBarcode();
                if (good != null) {

                    ScanResultFragment result = (ScanResultFragment) fragmentManager.findFragmentById(R.id.good_add_load_scan_result_frg);
                    if (check(good, result.getTag())) {
                        result.addData(good);
                        scanResult.add(good);
                        if (result.getTag().equals("OPERATION_LOAD_CAR")) {
                            double act_weight = Double.valueOf(actWeight.getText().toString().trim());
                            act_weight = act_weight + Double.valueOf(good.getActWeight());
                            act_weight = Math.round(act_weight * 100);
                            act_weight = act_weight / 100;
                            actWeight.setText(String.valueOf(act_weight));
                        }
                    }
                } else {
                    Toast.makeText(GoodAddLoadActivity.this, "扫码不存在", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedGoodsBarcodeInfo> call, Throwable t) {

            }
        });
    }

    private boolean check(GoodsBarcodeBean good, String tag) {
        boolean result = true;
        if (!good.getStatus().equals("0")) {
            result = false;
            Toast.makeText(GoodAddLoadActivity.this, "该货品不能装车", Toast.LENGTH_SHORT).show();
        } else if (!good.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())) {
            result = false;
            Toast.makeText(GoodAddLoadActivity.this, "该货品所在库房与用户管理库房不一致", Toast.LENGTH_SHORT).show();
        } else if (!checkEixt(good)) {
            result = false;
            Toast.makeText(GoodAddLoadActivity.this, "该条码此次已扫", Toast.LENGTH_SHORT).show();
        } else if (tag == "OPERATION_LOAD_CAR") {
            if (!checkGoodsManageExit(good)) {
                result = false;
                Toast.makeText(GoodAddLoadActivity.this, "该货品不在加货明细中", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }


    private boolean checkEixt(GoodsBarcodeBean good) {
        boolean result = true;
        if (scanResult.size() > 0) {
            for (GoodsBarcodeBean goodBarcode : scanResult) {
                if (goodBarcode.getBarcode().equals(good.getBarcode())) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private boolean checkGoodsManageExit(GoodsBarcodeBean good) {
        boolean result = false;
        if (goodsManageDetailList.size() > 0) {
            for (GoodsManageDetailBean detail : goodsManageDetailList) {
                if (detail.getGoodsName().equals(good.getGoodsName()) && detail.getSpecificationModel().equals(good.getSpecificationModel())) {
                    result = true;
                }
            }
        }
        return result;
    }

    private BroadcastReceiver mSamDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)) {
                String message;
                try {
                    message = intent.getStringExtra(SCN_CUST_EX_SCODE).toString().trim();
                    if (!fragmentManager.findFragmentById(R.id.good_add_load_scan_result_frg).getTag().equals("OPERATION_SELECT")) {
                        getScanResult(message);
                    } else {
                        Toast.makeText(GoodAddLoadActivity.this, "请选择装车或扫码操作", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("in", e.toString());
                    Toast.makeText(GoodAddLoadActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

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
        finish();
    }
}
