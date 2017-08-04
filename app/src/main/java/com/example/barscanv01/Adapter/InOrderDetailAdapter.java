package com.example.barscanv01.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barscanv01.Bean.InOrderDetailBean;
import com.example.barscanv01.R;

import java.util.List;

/**
 * Created by zhulin on 2017/8/3.
 */

public class InOrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<InOrderDetailBean> datas;

    public InOrderDetailAdapter(Context mContext,List<InOrderDetailBean> datas){
        this.mContext=mContext;
        this.datas=datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder=new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.in_order_detail_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).good.setText(datas.get(position).getGoodsName());
        ((MyViewHolder)holder).count.setText(datas.get(position).getCount()+"");
        ((MyViewHolder)holder).customerName.setText(datas.get(position).getCustomerName());
        ((MyViewHolder)holder).modle.setText(datas.get(position).getSpecificationModel());
        ((MyViewHolder)holder).actcount.setText(datas.get(position).getActCount()+"");
        if(datas.get(position).getFinishStatus().equals("0")){
            ((MyViewHolder)holder).good.setTextColor(Color.RED);
            ((MyViewHolder)holder).count.setTextColor(Color.RED);
            ((MyViewHolder)holder).customerName.setTextColor(Color.RED);
            ((MyViewHolder)holder).modle.setTextColor(Color.RED);
            ((MyViewHolder)holder).actcount.setTextColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView good;
        TextView modle;
        TextView count;
        TextView actcount;
        TextView customerName;
        public MyViewHolder(View view) {
            super(view);
            good= (TextView) view.findViewById(R.id.in_order_detial_good);
            modle=(TextView) view.findViewById(R.id.in_order_detial_modle);
            count= (TextView) view.findViewById(R.id.in_order_detial_count);
            actcount= (TextView) view.findViewById(R.id.in_order_detial_actcount);
            customerName=(TextView)view.findViewById(R.id.in_order_detial_customerName);
        }
    }
}
