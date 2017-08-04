package com.example.barscanv01.Unload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.barscanv01.Bean.ReceivedInOrderInfo;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.ServiceAPI.GetCarResonService;
import com.example.barscanv01.ServiceAPI.GetInOrderforPDAbyPlateService;
import com.example.barscanv01.Util.CarPlateUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_order_bill);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("卸车信息获取");
        myApp=(MyApp)getApplication();
        scanManager=ScanManager.getInstance();
        scanManager.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_FILLING);
        scanManager.enableBeep();
        carPlateUtil=new CarPlateUtil();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,carPlateUtil.getProvinces());
        carPlateSpinner.setAdapter(adapter);
        detailView.setLayoutManager(new LinearLayoutManager(this));
        detailView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
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
                if(s.length()==10){
                    String inOrderNo=s.toString().trim();
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
                if(s.length()==8){
                    try{
                        String plate = s.toString();
                        String province = plate.substring(0, 2);
                        int id = Integer.parseInt(province);
                        carPlateSpinner.setSelection(id - 1);
                        s = s.delete(0, 2);
                        String finalPlate=((String) carPlateSpinner.getSelectedItem())+s;
                        finalPlate.trim();
                        Retrofit retrofit=new RetrofitBuildUtil().getRetrofit();
                        GetInOrderforPDAbyPlateService getInOrderforPDAbyPlateService=retrofit.create(GetInOrderforPDAbyPlateService.class);
                        Call<ReceivedInOrderInfo> call=getInOrderforPDAbyPlateService.getInOrder(finalPlate);
                        call.enqueue(new Callback<ReceivedInOrderInfo>() {
                            @Override
                            public void onResponse(Call<ReceivedInOrderInfo> call, Response<ReceivedInOrderInfo> response) {
                                if(response.body().getAttributes().getInOrder()!=null){
                                    manageInOrder(response.body().getAttributes().getInOrder(),response.body().getAttributes().getInOrderDetailList());
                                    billNo.setText(InOrderSingleton.getInstance().getInOrder().getInOrderNo()+" ");
                                    showDetailData();

                                }else{
                                    Toast.makeText(InOrderBillActivity.this,"改车牌无对应退货单",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ReceivedInOrderInfo> call, Throwable t) {

                            }
                        });

                    }catch(Exception e){
                        Toast.makeText(InOrderBillActivity.this,"请扫描正确的车牌号",Toast.LENGTH_SHORT).show();
                        Log.d("eeee",e.getMessage());
                    }
                }

            }
        });
    }

    private void showDetailData() {
        detailAdapter=new InOrderDetailAdapter(InOrderBillActivity.this,InOrderSingleton.getInstance().getInOrderDetailList());
        detailView.setAdapter(detailAdapter);
    }

    private void manageInOrder(InOrderBean inOrder, List<InOrderDetailBean> inOrderDetailList) {
        InOrderSingleton.getInstance().setInOrder(inOrder);
        InOrderSingleton.getInstance().setInOrderDetailList(inOrderDetailList);
    }
}
