package com.example.barscanv01.SaleLoad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.LoadedResultAdapter;
import com.example.barscanv01.Adapter.OutOrderDetailAdapter;
import com.example.barscanv01.Bean.DetailBarcodeBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedDelivieryBillInfo;
import com.example.barscanv01.Bean.ReceivedDetailBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedDetailTotalWeightBean;
import com.example.barscanv01.Bean.ReceivedLoadGoodsBarcodeInfo;
import com.example.barscanv01.Login.LoginActivity;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.DeliveryBillByBillNoService;
import com.example.barscanv01.ServiceAPI.DeliveryBillById;
import com.example.barscanv01.ServiceAPI.DeliveryBillByPlateService;
import com.example.barscanv01.ServiceAPI.GetDetailBarcodeService;
import com.example.barscanv01.ServiceAPI.GetLoadGoodsBarcodeService;
import com.example.barscanv01.ServiceAPI.GetOrderDetailWeightService;
import com.example.barscanv01.Setting.SettingSingletone;
import com.example.barscanv01.TitleChangeFragment.OrderDetailTitleFragment;
import com.example.barscanv01.TitleChangeFragment.OrderNoDetailTitleFragment;
import com.example.barscanv01.Util.CarPlateUtil;
import com.example.barscanv01.Util.OutOrderDetailSortUtil;
import com.example.barscanv01.Util.OutOrderScanedUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.example.barscanv01.Util.WriteBizlogUtil;
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

public class DeliveryBillActivity extends AppCompatActivity {

    @BindView(R.id.car_plate_spinner)
    Spinner carPlateProvince;
    @BindView(R.id.get_car_plate)
    EditText carPlate;
    @BindView(R.id.get_bill_number)
    EditText billNumber;
    @BindView(R.id.out_order_detial)
    RecyclerView outOrderDetialView;
    @BindView(R.id.out_order_swipe_refresh)
    SwipeRefreshLayout mySwipe;
    @BindView(R.id.delivery_bill_act_weight)
    TextView ActWeight;
    @BindView(R.id.delivery_bill_confirm_button)
    Button confirmButton;
    @BindView(R.id.delivery_bill_cancel_button)
    Button cancelButton;
    @BindView(R.id.delivery_bill_count)
    TextView count;
    @BindView(R.id.delivery_bill_act_cout)
    TextView act_count;

    private CarPlateUtil carPlateUtil;
    private OutOrderDetailAdapter myAdapter;
    private LoadedResultAdapter loadedResultAdapter;
    private MyApp myApp;
    private List<GoodsBarcodeBean> loadGoodsResult;
    private FragmentManager fragmentManager;

    /*销邦设置*/
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_bill);
        ButterKnife.bind(this);
        myApp = (MyApp) getApplication();
        loadGoodsResult = new ArrayList<GoodsBarcodeBean>();
        carPlateUtil = new CarPlateUtil();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, carPlateUtil.getProvinces());
        carPlateProvince.setAdapter(adapter);
        fragmentManager = getSupportFragmentManager();
        OrderDetailTitleFragment orderDetailTitleFragment = new OrderDetailTitleFragment();
        setTitleFragment(orderDetailTitleFragment, "OutOrderDetailTitle");
        outOrderDetialView.setLayoutManager(new LinearLayoutManager(this));
        outOrderDetialView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        outOrderDetialView.setItemAnimator(new DefaultItemAnimator());
        setListener();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (myApp.getUserBean().getId() == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("注意")
                    .setMessage("用户已退出系统请重新登录！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(DeliveryBillActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }).show();
        }
    }

    private void setListener() {
        billNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 12) {
                    String billNo = s.toString();
                    getOutOrderByNo(billNo);
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(DeliveryBillActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else if (s.length() == 8) {
                    carPlate.setText(s.toString().trim());
                    billNumber.setText("");
                }
            }
        });
        carPlate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8 || s.length() == 6) {
                    if (s.length() == 8) {
                        int id = 0;
                        String plate = s.toString();
                        String province = plate.substring(0, 2);
                        try {
                            id = Integer.parseInt(province);
                        } catch (Exception e) {
                            Log.e("DeliveryBill", e.getMessage().toString());
                            Toast.makeText(DeliveryBillActivity.this, "请扫描正确车牌号", Toast.LENGTH_SHORT).show();
                        }
                        if (id < 31) {
                            carPlateProvince.setSelection(id - 1);
                            s = s.delete(0, 2);
                        }
                    } else if (s.length() == 6) {
                        String finalPlate = ((String) carPlateProvince.getSelectedItem()) + s.toString().trim();
                        getOutOrderbyPlate(finalPlate);
                    }
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(DeliveryBillActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else if (s.length() == 12) {
                    String billNo = s.toString();
                    billNumber.setText(billNo);
                    carPlate.setText("");
                }
            }
        });
        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipe.setRefreshing(true);
                if (DeliveryBillSingleton.getInstance().getOutOrderDetailBean().size() > 0) {
                    String id = DeliveryBillSingleton.getInstance().getOutOrderBean().getId();
                    Log.e("aaaa", "开始了");
                    Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                    DeliveryBillById deliveryBillByBillNoService = retrofit.create(DeliveryBillById.class);
                    Call<ReceivedDelivieryBillInfo> call = deliveryBillByBillNoService.getDeliveryBillById(id);
                    call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
                        @Override
                        public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                            manageOutOrder(response.body().getAttributes().getOutOrder(), response.body().getAttributes().getOutOrderDetailList());
                            showDetail();
                            showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean(), response.body().getAttributes().getOutOrder().getId());
                        }

                        @Override
                        public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {

                        }
                    });
                } else {
                    Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                    GetLoadGoodsBarcodeService getLoadGoodsBarcodeService = retrofit.create(GetLoadGoodsBarcodeService.class);
                    Call<ReceivedLoadGoodsBarcodeInfo> call = getLoadGoodsBarcodeService.getLoadedGoods(DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
                    call.enqueue(new Callback<ReceivedLoadGoodsBarcodeInfo>() {
                        @Override
                        public void onResponse(Call<ReceivedLoadGoodsBarcodeInfo> call, Response<ReceivedLoadGoodsBarcodeInfo> response) {
                            loadGoodsResult = response.body().getAttributes().getGoodsBarcodeEndtityList();
                            Log.d("ffff", response.toString());
                            showLoadedGoods(loadGoodsResult);
                            showLoadedGoodsWeight(loadGoodsResult);
                        }

                        @Override
                        public void onFailure(Call<ReceivedLoadGoodsBarcodeInfo> call, Throwable t) {

                        }
                    });
                }
                mySwipe.setRefreshing(false);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DeliveryBillSingleton.getInstance().getOutOrderBean() != null && DeliveryBillSingleton.getInstance().getOutOrderBean().getId() != null) {
                    if (DeliveryBillSingleton.getInstance().getOutOrderBean().getProcess().equals("4") || DeliveryBillSingleton.getInstance().getOutOrderBean().getProcess().equals("3")) {
                        if (DeliveryBillSingleton.getInstance().getOutOrderDetailBean().size() > 0) {
                            if (!checkDepotFinished(DeliveryBillSingleton.getInstance().getOutOrderDetailBean())) {
                                Intent intent = new Intent(DeliveryBillActivity.this, SaleLoadActivity.class);
                                startActivityForResult(intent, 1);
                            } else {
                                Toast.makeText(DeliveryBillActivity.this, "该库区暂无可以装车货品", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryBillActivity.this);
                            builder.setTitle("注意")
                                    .setMessage("该发货单在系统中无发货明细，请根据纸质发货单扫码装货")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(DeliveryBillActivity.this, DeliveryBillNoDetailActivity.class);
                                            startActivityForResult(intent, 2);
                                        }
                                    }).show();
                        }
                    } else if (DeliveryBillSingleton.getInstance().getOutOrderBean().getProcess().equals("5")) {
                        if (SettingSingletone.getInstance(DeliveryBillActivity.this).getAddResult()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryBillActivity.this);
                            builder.setTitle("注意")
                                    .setMessage("该发货单已经装车完成，是否需要根据加货明细进行加货")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(DeliveryBillActivity.this, GoodAddDetailActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        } else {
                            Toast.makeText(DeliveryBillActivity.this, "该发货单已经完成装车", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(DeliveryBillActivity.this, "请扫描发货单", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryBillSingleton.getInstance().setOutOrderBean(null);
                DeliveryBillSingleton.getInstance().setOutOrderDetailBean(null);
                DeliveryBillActivity.this.finish();
            }
        });
    }

    private void getOutOrderByNo(String billNo) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        DeliveryBillByBillNoService deliveryBillByBillNoService = retrofit.create(DeliveryBillByBillNoService.class);
        Call<ReceivedDelivieryBillInfo> call = deliveryBillByBillNoService.getDeliveryBillbyBillN0(billNo, myApp.getCurrentAreaBean().getAreaNo());
        call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
            @Override
            public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                try {
                    if (response.body().getAttributes().getOutOrder() != null) {
                        if (checkOutOrderProcess(response.body().getAttributes().getOutOrder())) {
                            manageOutOrder(response.body().getAttributes().getOutOrder(), response.body().getAttributes().getOutOrderDetailList());
                            carPlate.setText(carPlateUtil.getplateNum(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo()) + " ");
                            carPlateProvince.setSelection(carPlateUtil.getId(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo()));
                            if (DeliveryBillSingleton.getInstance().getOutOrderDetailBean().size() == 0) {
                                showNoDetail();
                            } else {
                                showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean(), DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
                                showDetail();
                            }
                            OutOrderScanedUtil orderScanedUtil = new OutOrderScanedUtil(response.body().getAttributes().getOutOrder(), DeliveryBillActivity.this);
                            orderScanedUtil.updateOutOrderProcess();
                            orderScanedUtil.updateAreaInOut();
                            if (response.body().getAttributes().getOutOrder().getProcess().equals("3") && response.body().getAttributes().getOutOrder().getProcess().equals("4")) {
                                WriteBizlogUtil writeBizlog = new WriteBizlogUtil(DeliveryBillActivity.this);
                                writeBizlog.writeLoadStartedLog();
                            }
                        }
                    } else {
                        Toast.makeText(DeliveryBillActivity.this, "该库区不存在该发货单", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {

            }
        });

    }

    private void getOutOrderbyPlate(String finalPlate) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        DeliveryBillByPlateService deliveryBillByPlateService = retrofit.create(DeliveryBillByPlateService.class);
        Call<ReceivedDelivieryBillInfo> call = deliveryBillByPlateService.getDeliveryBillByPlate(finalPlate, myApp.getCurrentAreaBean().getAreaNo());
        call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
            @Override
            public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                if (response.body().getAttributes().getOutOrder() != null) {
                    if (checkOutOrderProcess(response.body().getAttributes().getOutOrder())) {
                        manageOutOrder(response.body().getAttributes().getOutOrder(), response.body().getAttributes().getOutOrderDetailList());
                        billNumber.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo() + " ");
                        if (DeliveryBillSingleton.getInstance().getOutOrderDetailBean().size() == 0) {
                            showNoDetail();
                        } else {
                            showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean(), DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
                            showDetail();
                        }
                        OutOrderScanedUtil orderScanedUtil = new OutOrderScanedUtil(response.body().getAttributes().getOutOrder(), DeliveryBillActivity.this);
                        orderScanedUtil.updateOutOrderProcess();
                        orderScanedUtil.updateAreaInOut();
                        if (!response.body().getAttributes().getOutOrder().getProcess().equals("5")) {
                            WriteBizlogUtil writeBizlog = new WriteBizlogUtil(DeliveryBillActivity.this);
                            writeBizlog.writeLoadStartedLog();
                        }
                    }
                } else {
                    Toast.makeText(DeliveryBillActivity.this, "该库区不存在该发货单", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {

            }
        });
    }

    private void setTitleFragment(Fragment fragment, String tag) {
        if (fragmentManager.findFragmentById(R.id.delivery_bill_title_name_frg) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.delivery_bill_title_name_frg, fragment, tag);
            transaction.commit();
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.delivery_bill_title_name_frg, fragment, tag);
            transaction.commit();
        }
    }

    private void showDetail() {
        OrderDetailTitleFragment orderDetailTitleFragment = new OrderDetailTitleFragment();
        setTitleFragment(orderDetailTitleFragment, "OutOrderDetailTitle");
        myAdapter = new OutOrderDetailAdapter(DeliveryBillActivity.this, DeliveryBillSingleton.getInstance().getOutOrderDetailBean(), myApp.getCurrentDepot());
        outOrderDetialView.setAdapter(myAdapter);
    }

    private void showLoadedGoods(List<GoodsBarcodeBean> loadGoodsResult) {
        loadedResultAdapter = new LoadedResultAdapter(DeliveryBillActivity.this, loadGoodsResult);
        outOrderDetialView.setAdapter(loadedResultAdapter);
    }

    public boolean checkOutOrderProcess(OutOrderBean outOrder) {
        boolean result = false;
        if (outOrder.getProcess().equals("3") || outOrder.getProcess().equals("4") || outOrder.getProcess().equals("5")) {
            result = true;
        } else {
            switch (outOrder.getProcess()) {
                case "2":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单未过皮重", Toast.LENGTH_SHORT).show();
                    break;
                /*case "5":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单已装车完毕", Toast.LENGTH_SHORT).show();
                    break;*/
                case "1":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单的车辆未进厂", Toast.LENGTH_SHORT).show();
                    break;
                case "6":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单的已过负重", Toast.LENGTH_SHORT).show();
                    break;
                case "7":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单的车辆已出厂", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(DeliveryBillActivity.this, "该发货单不允许装车", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }

    public void manageOutOrder(OutOrderBean outOrderBean, List<OutOrderDetailBean> outOrderDetailList) {
        DeliveryBillSingleton.getInstance().setOutOrderBean(outOrderBean);
        if (outOrderDetailList != null) {
            OutOrderDetailSortUtil sort = new OutOrderDetailSortUtil(outOrderDetailList, DeliveryBillActivity.this);
            DeliveryBillSingleton.getInstance().setOutOrderDetailBean(sort.getFinalOutOrderDetails());
        }
    }

    public void showOutOrderWeight(List<OutOrderDetailBean> outOrderDetailList, String id) {
        int counts = 0;
        int actCounts = 0;
        for (OutOrderDetailBean detail : outOrderDetailList) {
            if (detail.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())) {
                counts = counts + detail.getCount();
                if (detail.getActCount() != null) {
                    actCounts = (int) (actCounts + Double.valueOf(detail.getActCount()));
                }
            }
        }
        count.setText(String.valueOf(counts));
        act_count.setText(String.valueOf(actCounts));
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetDetailBarcodeService getDetailBarcodeService = retrofit.create(GetDetailBarcodeService.class);
        Call<ReceivedDetailBarcodeInfo> call = getDetailBarcodeService.getDetailBarcodesById(id);
        call.enqueue(new Callback<ReceivedDetailBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedDetailBarcodeInfo> call, Response<ReceivedDetailBarcodeInfo> response) {
                List<DetailBarcodeBean> detailBarcodeList = new ArrayList<DetailBarcodeBean>();
                detailBarcodeList = response.body().getAttributes().getDetailBarcodeEntityList();
                if (detailBarcodeList == null) {
                    ActWeight.setText("0.0" + "t");
                } else {
                    double act_weight = 0;
                    for (DetailBarcodeBean detailBarcode : detailBarcodeList) {
                        act_weight = act_weight + detailBarcode.getWeight();
                    }
                    /*act_weight = Math.round(act_weight);
                    act_weight = act_weight / 1000;*/
                    ActWeight.setText(String.valueOf(act_weight) + "t");
                }
            }

            @Override
            public void onFailure(Call<ReceivedDetailBarcodeInfo> call, Throwable t) {

            }
        });
    }

    public boolean checkDepotFinished(List<OutOrderDetailBean> outOrderDetailList) {
        boolean result = true;
        for (OutOrderDetailBean detail : outOrderDetailList) {
            if (detail.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())) {
                if (detail.getFinishStatus().equals("0")) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public void showNoDetail() {
        OrderNoDetailTitleFragment orderNoDetailTitleFragment = new OrderNoDetailTitleFragment();
        setTitleFragment(orderNoDetailTitleFragment, "OrderNoDetailTitle");
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetLoadGoodsBarcodeService getLoadGoodsBarcodeService = retrofit.create(GetLoadGoodsBarcodeService.class);
        Call<ReceivedLoadGoodsBarcodeInfo> call = getLoadGoodsBarcodeService.getLoadedGoods(DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
        call.enqueue(new Callback<ReceivedLoadGoodsBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedLoadGoodsBarcodeInfo> call, Response<ReceivedLoadGoodsBarcodeInfo> response) {
                loadGoodsResult = response.body().getAttributes().getGoodsBarcodeEndtityList();
                Log.d("ffff", response.toString());
                showLoadedGoods(loadGoodsResult);
                showLoadedGoodsWeight(loadGoodsResult);

            }

            @Override
            public void onFailure(Call<ReceivedLoadGoodsBarcodeInfo> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            String id = DeliveryBillSingleton.getInstance().getOutOrderBean().getId();
            Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
            DeliveryBillById deliveryBillByBillNoService = retrofit.create(DeliveryBillById.class);
            Call<ReceivedDelivieryBillInfo> call = deliveryBillByBillNoService.getDeliveryBillById(id);
            call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
                @Override
                public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                    manageOutOrder(response.body().getAttributes().getOutOrder(), response.body().getAttributes().getOutOrderDetailList());
                    showDetail();
                    showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean(), DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
                }

                @Override
                public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {
                }
            });
        } else if (requestCode == 2) {
            Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
            GetLoadGoodsBarcodeService getLoadGoodsBarcodeService = retrofit.create(GetLoadGoodsBarcodeService.class);
            Call<ReceivedLoadGoodsBarcodeInfo> call = getLoadGoodsBarcodeService.getLoadedGoods(DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
            call.enqueue(new Callback<ReceivedLoadGoodsBarcodeInfo>() {
                @Override
                public void onResponse(Call<ReceivedLoadGoodsBarcodeInfo> call, Response<ReceivedLoadGoodsBarcodeInfo> response) {
                    loadGoodsResult = response.body().getAttributes().getGoodsBarcodeEndtityList();
                    Log.d("ffff", response.toString());
                    if (loadGoodsResult.size() > 0) {
                        showLoadedGoods(loadGoodsResult);
                        showLoadedGoodsWeight(loadGoodsResult);
                    }
                }

                @Override
                public void onFailure(Call<ReceivedLoadGoodsBarcodeInfo> call, Throwable t) {

                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showLoadedGoodsWeight(List<GoodsBarcodeBean> loadGoodsResult) {
        float totalActWeight = 0;
        int totoalCount = 0;
        for (GoodsBarcodeBean good : loadGoodsResult) {
            totalActWeight = totalActWeight + good.getActWeight();
            totoalCount = totoalCount + 1;
        }
        /*totalActWeight = Math.round(totalActWeight);
        totalActWeight = totalActWeight / 1000;*/
        ActWeight.setText(String.valueOf(totalActWeight) + "t");
        act_count.setText(String.valueOf(totoalCount));

    }


    @Override
    public void onBackPressed() {
        DeliveryBillSingleton.getInstance().setOutOrderBean(null);
        DeliveryBillSingleton.getInstance().setOutOrderDetailBean(null);
        super.onBackPressed();
    }
}