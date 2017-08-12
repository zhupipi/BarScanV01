package com.example.barscanv01.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;
import com.example.barscanv01.SaleLoad.DeliveryBillActivity;
import com.example.barscanv01.Translate.TranslateActivity;
import com.example.barscanv01.Unload.InOrderBillActivity;

import butterknife.BindView;

/**
 * Created by zhupipi on 2017/6/27.
 */

public class OperationSelectFragment extends Fragment {

//    MyApp myApp;
//    ImageView loadImage;//装车
//    ImageView depotTransferImage;//移库
//    ImageView platterImage;//卸车
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.operation_select_frg,container,false);
//        return view;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        loadImage= (ImageView) getView().findViewById(R.id.load_image);
//        loadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), DeliveryBillActivity.class);
//                startActivity(intent);
//            }
//        });
//        depotTransferImage=(ImageView)getView().findViewById(R.id.depot_transfer_image);
//        platterImage=(ImageView)getView().findViewById(R.id.platter_image);
//        platterImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), InOrderBillActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }
    MyApp myApp;
    Button saleLoadButton;
    Button saleUnloadButton;
    Button changeDepotButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.operation_select_frag,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        saleLoadButton=(Button)getView().findViewById(R.id.operation_sale_load);
        saleUnloadButton=(Button)getView().findViewById(R.id.operation_sale_un_load);
        saleLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DeliveryBillActivity.class);
                startActivity(intent);
            }
        });
        saleUnloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), InOrderBillActivity.class);
                startActivity(intent);
            }
        });
        changeDepotButton=(Button)getView().findViewById(R.id.operation_change_depot);
        changeDepotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TranslateActivity.class);
                startActivity(intent);
            }
        });
    }
}
