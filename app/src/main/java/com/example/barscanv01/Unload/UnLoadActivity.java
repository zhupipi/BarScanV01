package com.example.barscanv01.Unload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnLoadActivity extends AppCompatActivity {
    @BindView(R.id.un_load_bill_no)
    TextView billNo;
    @BindView(R.id.un_load_car_plate)
    TextView carPlate;
    @BindView(R.id.un_load_weight)
    TextView weight;
    @BindView(R.id.un_load_act_weight)
    TextView actWeight;
    @BindView(R.id.un_load_result_view)
    RecyclerView resultView;
    @BindView(R.id.un_load_toolbar)
    Toolbar toolbar;
    @BindView(R.id.un_load_confirm_button)
    Button confirmButton;
    @BindView(R.id.un_load_cancel_button)
    Button cancelButton;

    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_load);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("卸货扫码");
        myApp= (MyApp) getApplication();
    }
}
