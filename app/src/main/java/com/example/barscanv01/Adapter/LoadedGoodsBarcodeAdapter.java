package com.example.barscanv01.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;

import java.util.List;

/**
 * Created by zhulin on 2017/8/29.
 */

public class LoadedGoodsBarcodeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GoodsBarcodeBean> datas1;
    private List<String> datas2;
    private String tag;
    private MyApp myApp;
    private String depotNo;

    public LoadedGoodsBarcodeAdapter(Context context, List<GoodsBarcodeBean> datas1, List<String> datas2, String tag, String depotNo) {
        this.context = context;
        this.datas1 = datas1;
        this.datas2 = datas2;
        this.tag = tag;
        this.depotNo = depotNo;
    }


    @Override
    public LoadedGoodsBarcodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LoadedGoodsBarcodeViewHolder viewHolder = new LoadedGoodsBarcodeViewHolder(LayoutInflater.from(context).inflate(R.layout.load_goods_content_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (tag) {
            case "ORDER_LOAD_CONTENT":
                ((LoadedGoodsBarcodeViewHolder) holder).barcode.setText(datas1.get(position).getBarcode());
                ((LoadedGoodsBarcodeViewHolder) holder).goodName.setText(datas1.get(position).getGoodsName());
                ((LoadedGoodsBarcodeViewHolder) holder).modle.setText(datas1.get(position).getSpecificationModel());
                ((LoadedGoodsBarcodeViewHolder) holder).customer.setText(datas2.get(position));
                break;
            case "DEPOT_LOAD_CONTENT":
                if (datas1.get(position).getDepotNo().equals(depotNo)) {
                    ((LoadedGoodsBarcodeViewHolder) holder).barcode.setText(datas1.get(position).getBarcode());
                    ((LoadedGoodsBarcodeViewHolder) holder).goodName.setText(datas1.get(position).getGoodsName());
                    ((LoadedGoodsBarcodeViewHolder) holder).modle.setText(datas1.get(position).getSpecificationModel());
                    ((LoadedGoodsBarcodeViewHolder) holder).customer.setText(datas2.get(position));
                }
        }


    }

    @Override
    public int getItemCount() {
        return datas1.size();
    }

    class LoadedGoodsBarcodeViewHolder extends RecyclerView.ViewHolder {
        TextView barcode;
        TextView goodName;
        TextView modle;
        TextView customer;

        public LoadedGoodsBarcodeViewHolder(View view) {
            super(view);
            barcode = (TextView) view.findViewById(R.id.load_content_item_barcode);
            goodName = (TextView) view.findViewById(R.id.load_content_item_good_name);
            modle = (TextView) view.findViewById(R.id.load_content_item_modle);
            customer = (TextView) view.findViewById(R.id.load_content_item_customer);
        }
    }
}
