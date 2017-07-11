package com.example.barscanv01.SaleLoad;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.barscanv01.Adapter.DividerItemDecoration;
import com.example.barscanv01.Adapter.OutOrderDetailAdapter;
import com.example.barscanv01.Bean.OutOrderBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedDelivieryBillInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.DeliveryBillByBillNoService;
import com.example.barscanv01.ServiceAPI.DeliveryBillById;
import com.example.barscanv01.ServiceAPI.DeliveryBillByPlateService;
import com.example.barscanv01.Util.CarPlateUtil;
import com.example.barscanv01.Util.CheckOutOrderDetailUtil;
import com.example.barscanv01.Util.OutOrderDetailSortUtil;
import com.example.barscanv01.Util.OutOrderScanedUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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


    ArrayList<OutOrderDetailBean> scanResult;
    CarPlateUtil carPlateUtil;
    ScanManager scanManager;
    OutOrderDetailAdapter myAdapter;
    MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_bill);
        ButterKnife.bind(this);
        myApp=(MyApp)getApplication();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("发货通知单信息");
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
                                if(checkOutOrderProcess(response.body().getAttributes().getOutOrder())) {
                                    DeliveryBillSingleton.getInstance().setOutOrderBean(response.body().getAttributes().getOutOrder());
                                    OutOrderDetailSortUtil detailSort=new OutOrderDetailSortUtil(response.body().getAttributes().getOutOrderDetailList(),DeliveryBillActivity.this);
                                    CheckOutOrderDetailUtil checkOutOrderDetail=new CheckOutOrderDetailUtil();
                                    DeliveryBillSingleton.getInstance().setOutOrderDetailBean(detailSort.getFinalOutOrderDetails());
                                    Log.d("aaaa", "" + DeliveryBillSingleton.getInstance().getOutOrderDetailBean().size());
                                    Log.d("aaaa", carPlateUtil.getplateNum(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo()));
                                    carPlate.setText(carPlateUtil.getplateNum(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo()));
                                    carPlateProvince.setSelection(carPlateUtil.getId(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo()));
                                    showDetail();
                                    OutOrderScanedUtil orderScanedUtil=new OutOrderScanedUtil(response.body().getAttributes().getOutOrder());
                                    orderScanedUtil.updateOutOrderProcess();
                                    orderScanedUtil.updateAreaInOut();
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
                                if (DeliveryBillSingleton.getInstance().getOutOrderBean() != null) {
                                    if (checkOutOrderProcess(response.body().getAttributes().getOutOrder())) {
                                        DeliveryBillSingleton.getInstance().setOutOrderBean(response.body().getAttributes().getOutOrder());
                                        OutOrderDetailSortUtil detailSort = new OutOrderDetailSortUtil(response.body().getAttributes().getOutOrderDetailList(), DeliveryBillActivity.this);
                                        CheckOutOrderDetailUtil checkOutOrderDetail = new CheckOutOrderDetailUtil();
                                        DeliveryBillSingleton.getInstance().setOutOrderDetailBean(detailSort.getFinalOutOrderDetails());
                                        billNumber.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo());
                                        showDetail();
                                        OutOrderScanedUtil orderScanedUtil = new OutOrderScanedUtil(response.body().getAttributes().getOutOrder());
                                        orderScanedUtil.updateOutOrderProcess();
                                        orderScanedUtil.updateAreaInOut();
                                    }
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
                        if (checkOutOrderProcess(response.body().getAttributes().getOutOrder())) {
                            DeliveryBillSingleton.getInstance().setOutOrderBean(response.body().getAttributes().getOutOrder());
                            OutOrderDetailSortUtil detailSort=new OutOrderDetailSortUtil(response.body().getAttributes().getOutOrderDetailList(),DeliveryBillActivity.this);
                            CheckOutOrderDetailUtil checkOutOrderDetail=new CheckOutOrderDetailUtil();
                            DeliveryBillSingleton.getInstance().setOutOrderDetailBean(detailSort.getFinalOutOrderDetails());
                            Log.e("aaaa", "" + DeliveryBillSingleton.getInstance().getOutOrderDetailBean().get(0).getCount());
                            showDetail();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {

                    }
                });
            mySwipe.setRefreshing(false);
            }
        });
    }

    private void showDetail() {

            myAdapter=new OutOrderDetailAdapter(DeliveryBillActivity.this,DeliveryBillSingleton.getInstance().getOutOrderDetailBean(),myApp.getCurrentDepot());
           // myAdapter=new OutOrderDetailAdapter(DeliveryBillActivity.this,scanResult);
            outOrderDetialView.setAdapter(myAdapter);
            myAdapter.setOnItemClickLitener(new OutOrderDetailAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.d("aaaa",""+position);
                    OutOrderDetailBean outdetail=DeliveryBillSingleton.getInstance().getOutOrderDetailBean().get(position);
                    if(outdetail.getFinishStatus().equals("0")){
                        if(outdetail.getDepotNo().equals(myApp.getCurrentDepot().getDepotNo())){
                           // CheckOutOrderDetailUtil checkOutOrderDetail=new CheckOutOrderDetailUtil();
                            if(DeliveryBillSingleton.getInstance().getLastCustomerCode()==null){
                                Intent intent=new Intent(DeliveryBillActivity.this,SaleLoadActivity.class);
                                intent.putExtra("id",position);
                                startActivity(intent);
                            }else{
                                if(outdetail.getCustomerCode().equals(DeliveryBillSingleton.getInstance().getLastCustomerCode())){
                                    Intent intent=new Intent(DeliveryBillActivity.this,SaleLoadActivity.class);
                                    intent.putExtra("id",position);
                                    startActivity(intent);
                                }else{
                                    if(!checklastCustomerNotLoaded()){
                                        Intent intent=new Intent(DeliveryBillActivity.this,SaleLoadActivity.class);
                                        intent.putExtra("id",position);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(DeliveryBillActivity.this,DeliveryBillSingleton.getInstance().getLastCustomerName()+"用户还未装车完成",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }else {
                            Toast.makeText(DeliveryBillActivity.this,"该发货货品不在用户管理的库区",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(DeliveryBillActivity.this,"该明细已经完成装车",Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
    }

    private boolean checklastCustomerNotLoaded() {
        boolean result = false;
        if (DeliveryBillSingleton.getInstance().getLastCustomerCode() != null) {
            for (OutOrderDetailBean outOrderDetailBean : DeliveryBillSingleton.getInstance().getOutOrderDetailBean()) {
                if(outOrderDetailBean.getCustomerCode().equals(DeliveryBillSingleton.getInstance().getLastCustomerCode())){
                    if(outOrderDetailBean.getFinishStatus().equals("0")){
                        result=true;
                        break;
                    }
                }
            }
        }
        return  result;
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
                default:
                    Toast.makeText(DeliveryBillActivity.this, "该发货单不允许装车", Toast.LENGTH_SHORT).show();
            }
        }
       return  result;
    }
}