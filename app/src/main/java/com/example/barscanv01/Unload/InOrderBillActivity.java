package com.example.barscanv01.Unload;

import android.content.Intent;
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
import com.example.barscanv01.Adapter.InOrderDetailAdapter;
import com.example.barscanv01.Bean.InOrderBean;
import com.example.barscanv01.Bean.InOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedCarResonInfo;
import com.example.barscanv01.Bean.ReceivedDetailBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedInOrderInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetCarResonService;
import com.example.barscanv01.ServiceAPI.GetDetailBarcodeService;
import com.example.barscanv01.ServiceAPI.GetInOrdeByIdService;
import com.example.barscanv01.ServiceAPI.GetInOrderByOrderNoService;
import com.example.barscanv01.ServiceAPI.GetInOrderforPDAbyPlateService;
import com.example.barscanv01.Util.CarPlateUtil;
import com.example.barscanv01.Util.InOrderScanedUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InOrderBillActivity extends AppCompatActivity {
    @BindView(R.id.inorder_bill_no)
    EditText billNo;
    @BindView(R.id.inorder_car_plate)
    TextView carPlate;
    @BindView(R.id.inorder_car_plate_spinner)
    Spinner carPlateSpinner;
    @BindView(R.id.inorder_confirm_button)
    Button confirmButton;
    @BindView(R.id.inorder_cancel_button)
    Button cancelButton;
    @BindView(R.id.inorder_detial)
    RecyclerView detailView;
    @BindView(R.id.inorder_weight)
    TextView weight;
    @BindView(R.id.inorder_act_weight)
    TextView actWeight;
    @BindView(R.id.inorder_bill_toolbar)
    Toolbar toolbar;

    private MyApp myApp;
    private CarPlateUtil carPlateUtil;
    private ScanManager scanManager;
    private InOrderDetailAdapter detailAdapter;

    final static int HAVE_INORDER_DETAIL = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_order_bill);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("卸车信息获取");
        myApp = (MyApp) getApplication();
        scanManager = ScanManager.getInstance();
        scanManager.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_FILLING);
        scanManager.enableBeep();
        carPlateUtil = new CarPlateUtil();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, carPlateUtil.getProvinces());
        carPlateSpinner.setAdapter(adapter);
        detailView.setLayoutManager(new LinearLayoutManager(this));
        detailView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        detailView.setItemAnimator(new DefaultItemAnimator());
        setLinstener();
    }

    private void setLinstener() {
        billNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    String inOrderNo = s.toString().trim();
                    Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                    GetInOrderByOrderNoService getInOrderByOrderNoService = retrofit.create(GetInOrderByOrderNoService.class);
                    Call<ReceivedInOrderInfo> call = getInOrderByOrderNoService.getInOrderbyNo(inOrderNo);
                    call.enqueue(new Callback<ReceivedInOrderInfo>() {
                        @Override
                        public void onResponse(Call<ReceivedInOrderInfo> call, Response<ReceivedInOrderInfo> response) {
                            if (response.body().getAttributes().getInOrder() != null) {
                                if (checkInOrderProcess(response.body().getAttributes().getInOrder())) {
                                    manageInOrder(response.body().getAttributes().getInOrder(), response.body().getAttributes().getInOrderDetailList());
                                    carPlate.setText(carPlateUtil.getplateNum(InOrderSingleton.getInstance().getInOrder().getPlateNo()));
                                    carPlateSpinner.setSelection(carPlateUtil.getId(InOrderSingleton.getInstance().getInOrder().getPlateNo()));
                                    showDetailData();
                                    showWeight();
                                    InOrderScanedUtil scanedUtil=new InOrderScanedUtil(InOrderSingleton.getInstance().getInOrder());
                                    scanedUtil.upDateInOrder();
                                }
                            } else {
                                Toast.makeText(InOrderBillActivity.this, "改车牌无对应退货单", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReceivedInOrderInfo> call, Throwable t) {

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
                /**需要注意：车牌省份已经修改。若后面需要动态修改省份，则我们也需要做动态
                 *
                 */
                if (s.length() == 8) {
                    try {
                        String plate = s.toString();
                        String province = plate.substring(0, 2);
                        int id = Integer.parseInt(province);
                        carPlateSpinner.setSelection(id - 1);
                        s = s.delete(0, 2);
                        String finalPlate = ((String) carPlateSpinner.getSelectedItem()) + s;
                        finalPlate.trim();
                        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                        GetInOrderforPDAbyPlateService getInOrderforPDAbyPlateService = retrofit.create(GetInOrderforPDAbyPlateService.class);
                        Call<ReceivedInOrderInfo> call = getInOrderforPDAbyPlateService.getInOrder(finalPlate);
                        call.enqueue(new Callback<ReceivedInOrderInfo>() {
                            @Override
                            public void onResponse(Call<ReceivedInOrderInfo> call, Response<ReceivedInOrderInfo> response) {
                                if (response.body().getAttributes().getInOrder() != null) {
                                    if (checkInOrderProcess(response.body().getAttributes().getInOrder())) {
                                        manageInOrder(response.body().getAttributes().getInOrder(), response.body().getAttributes().getInOrderDetailList());
                                        billNo.setText(InOrderSingleton.getInstance().getInOrder().getInOrderNo() + " ");
                                        showDetailData();
                                        showWeight();
                                        InOrderScanedUtil scanedUtil=new InOrderScanedUtil(InOrderSingleton.getInstance().getInOrder());
                                        scanedUtil.upDateInOrder();
                                    }
                                } else {
                                    Toast.makeText(InOrderBillActivity.this, "改车牌无对应退货单", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ReceivedInOrderInfo> call, Throwable t) {

                            }
                        });

                    } catch (Exception e) {
                        Toast.makeText(InOrderBillActivity.this, "请扫描正确的车牌号", Toast.LENGTH_SHORT).show();
                        Log.d("eeee", e.getMessage());
                    }
                }

            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InOrderSingleton.getInstance().getInOrder().getProcess().equals("5")) {
                    Intent intent = new Intent(InOrderBillActivity.this, UnLoadActivity.class);
                    intent.putExtra("weight", weight.getText().toString().trim());
                    intent.putExtra("actWeight", actWeight.getText().toString().trim());
                    startActivityForResult(intent, HAVE_INORDER_DETAIL);
                }else{
                    Toast.makeText(InOrderBillActivity.this,"该退货单已卸货完成",Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDetailData() {
        detailAdapter = new InOrderDetailAdapter(InOrderBillActivity.this, InOrderSingleton.getInstance().getInOrderDetailList());
        detailView.setAdapter(detailAdapter);
    }

    private void manageInOrder(InOrderBean inOrder, List<InOrderDetailBean> inOrderDetailList) {
        InOrderSingleton.getInstance().setInOrder(inOrder);
        InOrderSingleton.getInstance().setInOrderDetailList(inOrderDetailList);
    }

    private void showWeight() {
        getDetailBarcode();
        double totalWeight = 0;
        double detailActWeight = 0;
        for (InOrderDetailBean detail : InOrderSingleton.getInstance().getInOrderDetailList()) {
            totalWeight = totalWeight + detail.getWeight();
            detailActWeight = detailActWeight + detail.getActWeight();
        }
        weight.setText(totalWeight + "");
        actWeight.setText(detailActWeight + "");
    }

    private void getDetailBarcode() {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        GetDetailBarcodeService getDetailBarcodeService = retrofit.create(GetDetailBarcodeService.class);
        Call<ReceivedDetailBarcodeInfo> call = getDetailBarcodeService.getDetailBarcodes(InOrderSingleton.getInstance().getInOrder().getOutOrderNo());
        call.enqueue(new Callback<ReceivedDetailBarcodeInfo>() {
            @Override
            public void onResponse(Call<ReceivedDetailBarcodeInfo> call, Response<ReceivedDetailBarcodeInfo> response) {
                if (response.body().getAttributes().getDetailBarcodeEntityList().size() > 0) {
                    InOrderSingleton.getInstance().setDetailBarcodeList(response.body().getAttributes().getDetailBarcodeEntityList());
                }
            }

            @Override
            public void onFailure(Call<ReceivedDetailBarcodeInfo> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == HAVE_INORDER_DETAIL) {
            Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
            GetInOrdeByIdService getInOrdeByIdService = retrofit.create(GetInOrdeByIdService.class);
            Call<ReceivedInOrderInfo> call = getInOrdeByIdService.getInOrderbyId(InOrderSingleton.getInstance().getInOrder().getId());
            call.enqueue(new Callback<ReceivedInOrderInfo>() {
                @Override
                public void onResponse(Call<ReceivedInOrderInfo> call, Response<ReceivedInOrderInfo> response) {
                    manageInOrder(response.body().getAttributes().getInOrder(), response.body().getAttributes().getInOrderDetailList());
                    showDetailData();
                    showWeight();
                }

                @Override
                public void onFailure(Call<ReceivedInOrderInfo> call, Throwable t) {

                }
            });
        }
    }

    public boolean checkInOrderProcess(InOrderBean inOrder) {
        boolean result = false;
        if (inOrder.getProcess().equals("3") || inOrder.getProcess().equals("4") || inOrder.getProcess().equals("5")) {
            result = true;
        } else {
            switch (inOrder.getProcess()) {
                case "1":
                    Toast.makeText(InOrderBillActivity.this, "该退货单车辆未进场", Toast.LENGTH_SHORT).show();
                    break;
                case "0":
                    Toast.makeText(InOrderBillActivity.this,"该退货单未打印",Toast.LENGTH_SHORT).show();
                    break;
                case "6":
                    Toast.makeText(InOrderBillActivity.this, "该发货单的已过负重", Toast.LENGTH_SHORT).show();
                    break;
                case "7":
                    Toast.makeText(InOrderBillActivity.this, "该发货单的车辆已出厂", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
