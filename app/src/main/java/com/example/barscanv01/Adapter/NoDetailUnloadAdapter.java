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

import java.util.List;

/**
 * Created by zhulin on 2017/8/16.
 */

public class NoDetailUnloadAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GoodsBarcodeBean> datas;
    private List<DetailBarcodeBean> detailBarcodeList;

    public NoDetailUnloadAdapter(Context context, List<GoodsBarcodeBean> datas, List<DetailBarcodeBean> detailBarcodeList) {
        this.context = context;
        this.datas = datas;
        this.detailBarcodeList = detailBarcodeList;
    }

    @Override
    public NoDetailUnloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NoDetailUnloadViewHolder holder = new NoDetailUnloadViewHolder(LayoutInflater.from(context).inflate(R.layout.no_detail_unload_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NoDetailUnloadViewHolder) holder).barcode.setText(datas.get(position).getBarcode());
        ((NoDetailUnloadViewHolder) holder).good.setText(datas.get(position).getGoodsName());
        ((NoDetailUnloadViewHolder) holder).actWeight.setText(String.valueOf(datas.get(position).getAdjustWeight()));
        ((NoDetailUnloadViewHolder) holder).modle.setText(datas.get(position).getSpecificationModel());
        for (DetailBarcodeBean detailBarcode : detailBarcodeList) {
            if (detailBarcode.getBarcode() != null) {
                if (detailBarcode.getBarcode().equals(datas.get(position).getBarcode())) {
                    ((NoDetailUnloadViewHolder) holder).customerName.setText(datas.get(position).getGoodsName());
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class NoDetailUnloadViewHolder extends RecyclerView.ViewHolder {
        TextView good;
        TextView modle;
        TextView barcode;
        TextView customerName;
        TextView actWeight;

        public NoDetailUnloadViewHolder(View view) {
            super(view);
            good = (TextView) view.findViewById(R.id.no_detail_unload_good);
            modle = (TextView) view.findViewById(R.id.no_detail_unload_modle);
            barcode = (TextView) view.findViewById(R.id.no_detail_unload_barcode);
            customerName = (TextView) view.findViewById(R.id.no_detail_unload_customerName);
            actWeight = (TextView) view.findViewById(R.id.no_detail_unload_act_weight);
        }
    }
}
