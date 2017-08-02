package com.example.barscanv01.SaleLoad;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.LoadedResultAdapter;
import com.example.barscanv01.Adapter.OutOrderDetailAdapter;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedDelivieryBillInfo;
import com.example.barscanv01.Bean.ReceivedLoadGoodsBarcodeInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.DeliveryBillByBillNoService;
import com.example.barscanv01.ServiceAPI.DeliveryBillById;
import com.example.barscanv01.ServiceAPI.DeliveryBillByPlateService;
import com.example.barscanv01.ServiceAPI.GetLoadGoodsBarcodeService;
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
    @BindView(R.id.delivery_bill_toolbar)
    Toolbar toolbar;
    @BindView(R.id.out_order_swipe_refresh)
    SwipeRefreshLayout mySwipe;
    @BindView(R.id.delivery_bill_weight)
    TextView weight;
    @BindView(R.id.delivery_bill_act_weight)
    TextView ActWeight;
    @BindView (R.id.delivery_bill_confirm_button)
    Button confirmButton;
    @BindView(R.id.delivery_bill_cancel_button)
    Button cancelButton;

    CarPlateUtil carPlateUtil;
    ScanManager scanManager;
    OutOrderDetailAdapter myAdapter;
    LoadedResultAdapter loadedResultAdapter;
    MyApp myApp;

    private  List<GoodsBarcodeBean> loadGoodsResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_bill);
        ButterKnife.bind(this);
        myApp=(MyApp)getApplication();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("发货通知单信息");
        List<GoodsBarcodeBean> loadGoodsResult=new ArrayList<GoodsBarcodeBean>();
        scanManager=ScanManager.getInstance();
        scanManager.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_FILLING);
        scanManager.enableBeep();
        carPlateUtil=new CarPlateUtil();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,carPlateUtil.getProvinces());
        carPlateProvince.setAdapter(adapter);
        outOrderDetialView.setLayoutManager(new LinearLayoutManager(this));
        outOrderDetialView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        outOrderDetialView.setItemAnimator(new DefaultItemAnimator());
        setListener();

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
                if (s.length() == 10) {
                    String billNo = s.toString();
                    Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
                    DeliveryBillByBillNoService deliveryBillByBillNoService = retrofit.create(DeliveryBillByBillNoService.class);
                    Call<ReceivedDelivieryBillInfo> call = deliveryBillByBillNoService.getDeliveryBillbyBillN0(billNo);
                    call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
                        @Override
                        public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                            try {
                                if (response.body().getAttributes().getOutOrder() != null) {
                                    if (checkOutOrderProcess(response.body().getAttributes().getOutOrder())) {
                                            manageOutOrder(response.body().getAttributes().getOutOrder(),response.body().getAttributes().getOutOrderDetailList());
                                            carPlate.setText(carPlateUtil.getplateNum(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo()));
                                            carPlateProvince.setSelection(carPlateUtil.getId(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo()));
                                            showDetail();
                                            showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean());
                                            OutOrderScanedUtil orderScanedUtil = new OutOrderScanedUtil(response.body().getAttributes().getOutOrder(),DeliveryBillActivity.this);
                                            orderScanedUtil.updateOutOrderProcess();
                                            orderScanedUtil.updateAreaInOut();
                                            WriteBizlogUtil writeBizlog=new WriteBizlogUtil(DeliveryBillActivity.this);
                                            writeBizlog.writeLoadStartedLog();
                                            if(DeliveryBillSingleton.getInstance().getOutOrderDetailBean().size()==0){
                                                showNoDetail();
                                            }
                                    }
                                } else {
                                    Toast.makeText(DeliveryBillActivity.this, "发货单不存在", Toast.LENGTH_SHORT).show();
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
                if (s.length() == 8) {
                    try {
                        String plate = s.toString();
                        String province = plate.substring(0, 2);
                        int id = Integer.parseInt(province);
                        carPlateProvince.setSelection(id - 1);
                        s = s.delete(0, 2);
                        String finalPlate=((String) carPlateProvince.getSelectedItem())+s;
                        Log.d("aaaa",finalPlate);
                        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                        DeliveryBillByPlateService deliveryBillByPlateService = retrofit.create(DeliveryBillByPlateService.class);
                        Call<ReceivedDelivieryBillInfo> call = deliveryBillByPlateService.getDeliveryBillByPlate(finalPlate);
                        call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
                            @Override
                            public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                                if (response.body().getAttributes().getOutOrder()!= null) {
                                    if (checkOutOrderProcess(response.body().getAttributes().getOutOrder())) {
                                        manageOutOrder(response.body().getAttributes().getOutOrder(), response.body().getAttributes().getOutOrderDetailList());
                                        billNumber.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo() + " ");
                                        showDetail();
                                        showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean());
                                        OutOrderScanedUtil orderScanedUtil = new OutOrderScanedUtil(response.body().getAttributes().getOutOrder(),DeliveryBillActivity.this);
                                        orderScanedUtil.updateOutOrderProcess();
                                        orderScanedUtil.updateAreaInOut();
                                        WriteBizlogUtil writeBizlog=new WriteBizlogUtil(DeliveryBillActivity.this);
                                        writeBizlog.writeLoadStartedLog();
                                        if (DeliveryBillSingleton.getInstance().getOutOrderDetailBean().size() == 0) {
                                            showNoDetail();
                                        }
                                    }
                                }else{
                                    Toast.makeText(DeliveryBillActivity.this,"发货单不存在",Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {

                            }
                        });

                    } catch (Exception e) {
                        Toast.makeText(DeliveryBillActivity.this, "请扫描正确的车牌号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipe.setRefreshing(true);
                String id=DeliveryBillSingleton.getInstance().getOutOrderBean().getId();
                Log.e("aaaa","开始了");
                Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
                DeliveryBillById deliveryBillByBillNoService = retrofit.create(DeliveryBillById.class);
                Call<ReceivedDelivieryBillInfo> call = deliveryBillByBillNoService.getDeliveryBillById(id);
                call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
                    @Override
                    public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                        manageOutOrder(response.body().getAttributes().getOutOrder(), response.body().getAttributes().getOutOrderDetailList());
                        showDetail();
                        showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean());
                    }

                    @Override
                    public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {

                    }
                });
            mySwipe.setRefreshing(false);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DeliveryBillSingleton.getInstance().getOutOrderBean()!= null&&DeliveryBillSingleton.getInstance().getOutOrderBean().getId()!=null) {
                    if(DeliveryBillSingleton.getInstance().getOutOrderBean().getProcess().equals("4")) {
                        if (DeliveryBillSingleton.getInstance().getOutOrderDetailBean().size() > 0) {
                            if (!checkDepotFinished(DeliveryBillSingleton.getInstance().getOutOrderDetailBean())) {
                                Intent intent = new Intent(DeliveryBillActivity.this, SaleLoadActivity.class);
                                startActivityForResult(intent, 0);
                            } else {
                                Toast.makeText(DeliveryBillActivity.this, "该库区货品已全部装车完成", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Intent intent=new Intent(DeliveryBillActivity.this,DeliveryBillNoDetailActivity.class);
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(DeliveryBillActivity.this,"该发货单已经完成装车",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(DeliveryBillActivity.this,"请扫描发货单",Toast.LENGTH_SHORT).show();
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

    private void showDetail() {
            myAdapter=new OutOrderDetailAdapter(DeliveryBillActivity.this,DeliveryBillSingleton.getInstance().getOutOrderDetailBean(),myApp.getCurrentDepot());
            outOrderDetialView.setAdapter(myAdapter);
    }

    public boolean checkOutOrderProcess(OutOrderBean outOrder){
        boolean result=false;
        if(outOrder.getProcess().equals("3")||outOrder.getProcess().equals("4")){
            result=true;
        }else{
            switch (outOrder.getProcess()) {
                case "2":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单未过皮重", Toast.LENGTH_SHORT).show();
                    break;
                case "5":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单已装车完毕", Toast.LENGTH_SHORT).show();
                    break;
                case "1":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单的车辆未进厂", Toast.LENGTH_SHORT).show();
                    break;
                case "6":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单的已过毛重", Toast.LENGTH_SHORT).show();
                    break;
                case "7":
                    Toast.makeText(DeliveryBillActivity.this, "该发货单的车辆已出厂", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(DeliveryBillActivity.this, "该发货单不允许装车", Toast.LENGTH_SHORT).show();
            }
        }
       return  result;
    }

    public void manageOutOrder(OutOrderBean outOrderBean, List<OutOrderDetailBean> outOrderDetailList){
        DeliveryBillSingleton.getInstance().setOutOrderBean(outOrderBean);
        OutOrderDetailSortUtil sort=new OutOrderDetailSortUtil(outOrderDetailList,DeliveryBillActivity.this);
        DeliveryBillSingleton.getInstance().setOutOrderDetailBean(sort.getFinalOutOrderDetails());
    }
    public void showOutOrderWeight(List<OutOrderDetailBean> outOrderDetailList){
        float totalWeight=0;
        float totalActWeight=0;
        for(OutOrderDetailBean detail:outOrderDetailList){
            totalWeight=totalWeight+detail.getWeight();
            if(detail.getActWeight()==null){
                totalActWeight=totalActWeight+0;
            }else {
                totalActWeight=totalActWeight+Float.valueOf(detail.getActWeight());
            }
        }
        weight.setText(totalWeight+"");
        ActWeight.setText(totalActWeight+"");
    }

    public boolean checkDepotFinished(List<OutOrderDetailBean> outOrderDetailList){
        boolean result=true;
        for(OutOrderDetailBean detail:outOrderDetailList){
            if(detail.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())) {
                if (detail.getFinishStatus().equals("0")) {
                    result = false;
                    break;
                }
            }
        }
        return  result;
    }
    public void showNoDetail(){
        AlertDialog.Builder builder=new AlertDialog.Builder(DeliveryBillActivity.this);
        builder.setTitle("注意")
                .setMessage("该发货单在系统中无发货明细，请根据纸质发货单扫码装货")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(DeliveryBillActivity.this,DeliveryBillNoDetailActivity.class);
                        startActivityForResult(intent,2);
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if(resultCode==1){
            String id=DeliveryBillSingleton.getInstance().getOutOrderBean().getId();
            Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
            DeliveryBillById deliveryBillByBillNoService = retrofit.create(DeliveryBillById.class);
            Call<ReceivedDelivieryBillInfo> call = deliveryBillByBillNoService.getDeliveryBillById(id);
            call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
                @Override
                public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                    manageOutOrder(response.body().getAttributes().getOutOrder(), response.body().getAttributes().getOutOrderDetailList());
                    showDetail();
                    showOutOrderWeight(DeliveryBillSingleton.getInstance().getOutOrderDetailBean());
                }
                @Override
                public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {
                }
            });
        }else if(resultCode==2){
            Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
            GetLoadGoodsBarcodeService getLoadGoodsBarcodeService=retrofit.create(GetLoadGoodsBarcodeService.class);
            Call<ReceivedLoadGoodsBarcodeInfo> call=getLoadGoodsBarcodeService.getLoadedGoods(DeliveryBillSingleton.getInstance().getOutOrderBean().getId());
            call.enqueue(new Callback<ReceivedLoadGoodsBarcodeInfo>() {
                @Override
                public void onResponse(Call<ReceivedLoadGoodsBarcodeInfo> call, Response<ReceivedLoadGoodsBarcodeInfo> response) {
                    loadGoodsResult=response.body().getAttributes().getGoodsBarcodeEndtityList();
                    Log.d("ffff",response.toString());
                    if(loadGoodsResult.size()>0){
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
        float totalActWeight=0;
        for(GoodsBarcodeBean good:loadGoodsResult){
            totalActWeight=totalActWeight+Float.valueOf(good.getActWeight());
        }
        ActWeight.setText(String.valueOf(totalActWeight));
    }

    private void showLoadedGoods(List<GoodsBarcodeBean> loadGoodsResult) {
            loadedResultAdapter=new LoadedResultAdapter(DeliveryBillActivity.this,loadGoodsResult);
            outOrderDetialView.setAdapter(loadedResultAdapter);
    }


    @Override
    public void onBackPressed() {
        DeliveryBillSingleton.getInstance().setOutOrderBean(null);
        DeliveryBillSingleton.getInstance().setOutOrderDetailBean(null);
        super.onBackPressed();
    }
}