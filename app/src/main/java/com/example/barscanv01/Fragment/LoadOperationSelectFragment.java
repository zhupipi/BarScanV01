package com.example.barscanv01.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.barscanv01.R;

/**
 * Created by zhupipi on 2017/7/2.
 */

public class LoadOperationSelectFragment extends Fragment {
    ImageView loadCar;
    ImageView stockTransfer;
    public interface OnItemClickListener {
        void onItemClick(View view,String tag);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.load_operation_select_frg,container,false);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadCar= (ImageView) getView().findViewById(R.id.load_car_image);
        stockTransfer=(ImageView)getView().findViewById(R.id.stock_transfer_image);
        if(mOnItemClickListener!=null){
            loadCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag="OPERATION_LOAD_CAR";
                    mOnItemClickListener.onItemClick(loadCar,tag);
                }
            });
            stockTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String tag="OPERATION_CHANGE_DEPOT";
                    mOnItemClickListener.onItemClick(stockTransfer,tag);
                }
            });
        }
    }
}
