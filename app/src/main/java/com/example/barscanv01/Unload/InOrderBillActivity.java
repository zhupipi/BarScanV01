package com.example.barscanv01.Unload;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.barscanv01.Adapter.InOrderDetailAdapter;
import com.example.barscanv01.Bean.InOrderBean;
import com.example.barscanv01.Bean.InOrderDetailBean;
import com.example.barscanv01.Bean.ReceivedCarResonInfo;
import com.example.barscanv01.Bean.ReceivedDelivieryBillInfo;
import com.example.barscanv01.Bean.ReceivedDetailBarcodeInfo;
import com.example.barscanv01.Bean.ReceivedInOrderInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.DeliveryBillByPlateService;
import com.example.barscanv01.ServiceAPI.GetCarResonService;
import com.example.barscanv01.ServiceAPI.GetDetailBarcodeService;
import com.example.barscanv01.ServiceAPI.GetInOrdeByIdService;
import com.example.barscanv01.ServiceAPI.GetInOrderByOrderNoService;
import com.example.barscanv01.ServiceAPI.GetInOrderforPDAbyPlateService;
import com.example.barscanv01.Util.CarPlateUtil;
import com.example.barscanv01.Util.InOrderDetailSortUtil;
import com.example.barscanv01.Util.InOrderScanedUtil;
import com.example.barscanv01.Util.RetrofitBuildUtil;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
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
    @BindView(R.id.inorder_act_count)
    TextView actCount;
    @BindView(R.id.inorder_count)
    TextView count;
    @BindView(R.id.inorder_act_weight)
    TextView actWeight;


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
        myApp = (MyApp) getApplication();
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
                if (s.length() == 12) {
                    String inOrderNo = s.toString().trim();
                    Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                    GetInOrderByOrderNoService getInOrderByOrderNoService = retrofit.create(GetInOrderByOrderNoService.class);
                    Call<ReceivedInOrderInfo> call = getInOrderByOrderNoService.getInOrderbyNo(inOrderNo);
                    call.enqueue(new Callback<ReceivedInOrderInfo>() {
                        @Override
                        public void onResponse(Call<ReceivedInOrderInfo> call, Response<ReceivedInOrderInfo> response) {
                            if (response.body().getAttributes().getInOrder() != null) {
                                if (response.body().getAttributes().getInOrderDetailList() != null) {
                                    if (checkInOrderProcess(response.body().getAttributes().getInOrder())) {
                                        manageInOrder(response.body().getAttributes().getInOrder(), response.body().getAttributes().getInOrderDetailList());
                                        carPlate.setText(carPlateUtil.getplateNum(InOrderSingleton.getInstance().getInOrder().getPlateNo()) + " ");
                                        carPlateSpinner.setSelection(carPlateUtil.getId(InOrderSingleton.getInstance().getInOrder().getPlateNo()));
                                        showDetailData();
                                        showWeight();
                                        InOrderScanedUtil scanedUtil = new InOrderScanedUtil(InOrderSingleton.getInstance().getInOrder(), InOrderBillActivity.this);
                                        scanedUtil.upDateInOrder();
                                        scanedUtil.upDateAreaInOut();
                                    }
                                } else {
                                    Toast.makeText(InOrderBillActivity.this, "该车牌在该用户工作库区无对应退货单明细", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(InOrderBillActivity.this, "该车牌无对应退货单", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReceivedInOrderInfo> call, Throwable t) {

                        }
                    });
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(InOrderBillActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else if (s.length() == 8) {
                    carPlate.setText(s.toString());
                    billNo.setText("");
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
                            Toast.makeText(InOrderBillActivity.this, "请输入正确车牌号", Toast.LENGTH_SHORT).show();
                        }
                        if (id < 31) {
                            carPlateSpinner.setSelection(id - 1);
                            s = s.delete(0, 2);
                        }
                    } else if (s.length() == 6) {
                        final String finalPlate = ((String) carPlateSpinner.getSelectedItem()) + s.toString().trim();
                        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
                        GetInOrderforPDAbyPlateService getInOrderforPDAbyPlateService = retrofit.create(GetInOrderforPDAbyPlateService.class);
                        Call<ReceivedInOrderInfo> call = getInOrderforPDAbyPlateService.getInOrder(finalPlate, myApp.getCurrentAreaBean().getAreaNo());
                        call.enqueue(new Callback<ReceivedInOrderInfo>() {
                            @Override
                            public void onResponse(Call<ReceivedInOrderInfo> call, Response<ReceivedInOrderInfo> response) {
                                if (response.body().getAttributes().getInOrder() != null) {
                                    if (response.body().getAttributes().getInOrderDetailList() != null) {
                                        if (checkInOrderProcess(response.body().getAttributes().getInOrder())) {
                                            manageInOrder(response.body().getAttributes().getInOrder(), response.body().getAttributes().getInOrderDetailList());
                                            billNo.setText(InOrderSingleton.getInstance().getInOrder().getInOrderNo() + " ");
                                            showDetailData();
                                            showWeight();
                                            InOrderScanedUtil scanedUtil = new InOrderScanedUtil(InOrderSingleton.getInstance().getInOrder(), InOrderBillActivity.this);
                                            scanedUtil.upDateInOrder();
                                            scanedUtil.upDateAreaInOut();
                                        }
                                    } else {
                                        Toast.makeText(InOrderBillActivity.this, "该车牌在该用户工作库区无对应退货单明细", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //Toast.makeText(InOrderBillActivity.this, "该车牌无对应退货单", Toast.LENGTH_SHORT).show();
                                    unInOrderDetail(finalPlate);
                                }
                            }

                            @Override
                            public void onFailure(Call<ReceivedInOrderInfo> call, Throwable t) {

                            }
                        });
                    }
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(InOrderBillActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    billNo.setText(s.toString());
                    carPlate.setText("");
                }
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InOrderSingleton.getInstance().getInOrder().getId() != null) {
                    if (!InOrderSingleton.getInstance().getInOrder().getProcess().equals("5")) {
                        Intent intent = new Intent(InOrderBillActivity.this, UnLoadActivity.class);
                        intent.putExtra("actWeight", actWeight.getText().toString().trim());
                        startActivityForResult(intent, HAVE_INORDER_DETAIL);

                    } else {
                        Toast.makeText(InOrderBillActivity.this, "该退货单已卸货完成", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InOrderBillActivity.this, "请录入退货单号或车牌信息", Toast.LENGTH_SHORT).show();
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

    private void unInOrderDetail(String finalPlate) {
        Retrofit retrofit = new RetrofitBuildUtil().getRetrofit();
        DeliveryBillByPlateService deliveryBillByPlateService = retrofit.create(DeliveryBillByPlateService.class);
        Call<ReceivedDelivieryBillInfo> call = deliveryBillByPlateService.getDeliveryBillByPlate(finalPlate, myApp.getCurrentAreaBean().getAreaNo());
        call.enqueue(new Callback<ReceivedDelivieryBillInfo>() {
            @Override
            public void onResponse(Call<ReceivedDelivieryBillInfo> call, Response<ReceivedDelivieryBillInfo> response) {
                if (response.body().getAttributes().getOutOrder() != null && response.body().getAttributes().getOutOrder().getProcess().equals("4")) {
                    InOrderSingleton.getInstance().setNoDetailOutOrder(response.body().getAttributes().getOutOrder());
                    AlertDialog.Builder builder = new AlertDialog.Builder(InOrderBillActivity.this);
                    builder.setTitle("注意")
                            .setMessage("该发货单未完成装车，如果需要卸货，请和客户协商好卸车")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(InOrderBillActivity.this, NoDetailUnLoadActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                } else {
                    Toast.makeText(InOrderBillActivity.this, "该车牌无对应退货单", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedDelivieryBillInfo> call, Throwable t) {

            }
        });

    }

    private void showDetailData() {
        detailAdapter = new InOrderDetailAdapter(InOrderBillActivity.this, InOrderSingleton.getInstance().getInOrderDetailList(), myApp.getCurrentDepot());
        detailView.setAdapter(detailAdapter);
    }

    /**
     * 待确定
     **/
    private void manageInOrder(InOrderBean inOrder, List<InOrderDetailBean> inOrderDetailList) {
        InOrderSingleton.getInstance().setInOrder(inOrder);
        if (inOrderDetailList != null) {
            InOrderDetailSortUtil sortUtil = new InOrderDetailSortUtil(inOrderDetailList, InOrderBillActivity.this);
            InOrderSingleton.getInstance().setInOrderDetailList(sortUtil.getFinalInOrderDetails());
        } else {
            InOrderSingleton.getInstance().setInOrderDetailList(null);
        }

    }

    private void showWeight() {
        getDetailBarcode();
        double detailActWeight = 0;
        int act_count = 0;
        int detail_count = 0;
        for (InOrderDetailBean detail : InOrderSingleton.getInstance().getInOrderDetailList()) {
            detailActWeight = detailActWeight + detail.getActWeight();
            act_count = act_count + detail.getActCount();
            detail_count = detail_count + detail.getCount();
        }
        detailActWeight = Math.round(detailActWeight * 1000);
        detailActWeight = detailActWeight / 1000;
        count.setText(String.valueOf(detail_count));
        actCount.setText(String.valueOf(act_count));
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
                    Toast.makeText(InOrderBillActivity.this, "该退货单未打印", Toast.LENGTH_SHORT).show();
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
