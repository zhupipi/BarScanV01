package com.example.barscanv01.SaleLoad;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.barscanv01.Fragment.LoadOperationSelectFragment;
import com.example.barscanv01.Fragment.ScanResultFragment;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryBillNoDetailActivity extends AppCompatActivity {
    @BindView(R.id.delivery_bill_nodetail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.delivery_bill_nodetail_billNo)
    TextView billNo;
    @BindView(R.id.delivery_bill_nodetail_plateNo)
    TextView plateNo;
    @BindView(R.id.delivery_bill_nodetail_frag_change)
    FrameLayout fragExchange;

    private FragmentManager fragmentManager;
    private MyApp myApp;
    private ScanManager scanManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_bill_no_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("无发货明细——扫码装车/倒垛");
        myApp=(MyApp)getApplication();
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
        initalData();
        transaction.add(R.id.delivery_bill_nodetail_frag_change,loadOperationSelectFragment,"OPERATION_SELECT");
        transaction.commit();
    }

    private void initalData() {
        billNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getOutOrderNo());
        plateNo.setText(DeliveryBillSingleton.getInstance().getOutOrderBean().getPlateNo());
        scanManager = ScanManager.getInstance();
        scanManager.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_BROADCAST);
    }
}
