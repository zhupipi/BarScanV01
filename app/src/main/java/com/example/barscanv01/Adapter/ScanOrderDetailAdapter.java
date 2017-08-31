package com.example.barscanv01.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.Bean.OutOrderDetailBean;
import com.example.barscanv01.R;

import java.util.List;

/**
 * Created by zhulin on 2017/8/31.
 */

public class ScanOrderDetailAdapter extends RecyclerView.Adapter {
    private List<OutOrderDetailBean> datas;
    private Context context;


    public ScanOrderDetailAdapter(Context context, List<OutOrderDetailBean> datas) {
        this.context = context;
        this.datas = datas;

    }


    @Override
    public ScanOrderDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ScanOrderDetailViewHolder viewHolder = new ScanOrderDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.out_order_detail_item, parent, false));
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int act_count = 0;
        ((ScanOrderDetailViewHolder) holder).good.setText(datas.get(position).getGoodsName());
        ((ScanOrderDetailViewHolder) holder).modle.setText(datas.get(position).getSpecificationModel());
        ((ScanOrderDetailViewHolder) holder).count.setText("" + datas.get(position).getCount());
        ((ScanOrderDetailViewHolder) holder).customerName.setText(datas.get(position).getCustomerName());
        if (datas.get(position).getActCount() == null) {
            ((ScanOrderDetailViewHolder) holder).actcount.setText("0");
            datas.get(position).setActCount("0");
        } else {
            double act_count1 = Double.valueOf(datas.get(position).getActCount());
            act_count = (int) act_count1;
            ((ScanOrderDetailViewHolder) holder).actcount.setText(String.valueOf(act_count));
        }
        if (datas.get(position).getFinishStatus().equals("0")) {
            ((ScanOrderDetailViewHolder) holder).good.setTextColor(Color.RED);
            ((ScanOrderDetailViewHolder) holder).modle.setTextColor(Color.RED);
            ((ScanOrderDetailViewHolder) holder).count.setTextColor(Color.RED);
            ((ScanOrderDetailViewHolder) holder).customerName.setTextColor(Color.RED);
            ((ScanOrderDetailViewHolder) holder).actcount.setTextColor(Color.RED);
        }
        if (datas.get(position).getCount() == act_count || datas.get(position).getFinishStatus().equals("1")) {
            ((ScanOrderDetailViewHolder) holder).good.setTextColor(Color.BLACK);
            ((ScanOrderDetailViewHolder) holder).modle.setTextColor(Color.BLACK);
            ((ScanOrderDetailViewHolder) holder).count.setTextColor(Color.BLACK);
            ((ScanOrderDetailViewHolder) holder).customerName.setTextColor(Color.BLACK);
            ((ScanOrderDetailViewHolder) holder).actcount.setTextColor(Color.BLACK);
        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ScanOrderDetailViewHolder extends RecyclerView.ViewHolder {
        TextView good;
        TextView modle;
        TextView count;
        TextView actcount;
        TextView customerName;

        public ScanOrderDetailViewHolder(View view) {
            super(view);
            good = (TextView) view.findViewById(R.id.out_order_detial_good);
            modle = (TextView) view.findViewById(R.id.out_order_detial_modle);
            count = (TextView) view.findViewById(R.id.out_order_detial_count);
            actcount = (TextView) view.findViewById(R.id.out_order_detial_actcount);
            customerName = (TextView) view.findViewById(R.id.out_order_detial_customerName);
        }
    }

}
