package com.example.barscanv01.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barscanv01.Bean.DetailBarcodeBean;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.R;
import com.example.barscanv01.Unload.InOrderSingleton;

import java.util.List;

/**
 * Created by zhulin on 2017/8/4.
 */

public class InOrderScanResultAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodsBarcodeBean> datas;

    public InOrderScanResultAdapter(Context mContext,List<GoodsBarcodeBean> datas){
        this.mContext=mContext;
        this.datas=datas;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder=new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.un_load_result_item,parent,false));
        return  holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).barcode.setText(datas.get(position).getBarcode());
        ((MyViewHolder)holder).goodName.setText(datas.get(position).getGoodsName());
        ((MyViewHolder)holder).modle.setText(datas.get(position).getSpecificationModel());
        ((MyViewHolder)holder).weight.setText(String.valueOf(datas.get(position).getActWeight()));
        for(DetailBarcodeBean detail: InOrderSingleton.getInstance().getDetailBarcodeList()){
            if(detail.getBarcode()!=null) {
                if (detail.getBarcode().equals(datas.get(position).getBarcode())) {
                    ((MyViewHolder) holder).customerName.setText(detail.getCustomerName());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView barcode;
        TextView goodName;
        TextView modle;
        TextView customerName;
        TextView weight;

        public MyViewHolder(View view) {
            super(view);
            barcode= (TextView) view.findViewById(R.id.un_load_result_barcode);
            goodName=(TextView)view.findViewById(R.id.un_load_result_good_name);
            modle=(TextView)view.findViewById(R.id.un_load_result_modle);
            customerName=(TextView)view.findViewById(R.id.un_load_result_customerName);
            weight=(TextView)view.findViewById(R.id.un_load_result_act_weight);
        }
    }
}
