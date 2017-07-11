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
import com.example.barscanv01.SaleLoad.DeliveryBillActivity;

import java.util.List;

/**
 * Created by zhupipi on 2017/6/30.
 */

public class OutOrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<OutOrderDetailBean> datas;
    private DepotBean depot;
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public OutOrderDetailAdapter(Context context, List<OutOrderDetailBean> datas,DepotBean depot) {
        mContext=context;
        this.datas=datas;
        this.depot=depot;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       MyViewHolder holder=new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.out_order_detail_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder)holder).good.setText(datas.get(position).getGoodsName());
        ((MyViewHolder)holder).modle.setText(datas.get(position).getSpecificationModel());
        ((MyViewHolder)holder).count.setText(""+datas.get(position).getCount());
        ((MyViewHolder)holder).customerName.setText(datas.get(position).getCustomerName());
        if(datas.get(position).getActCount()==null){
            ((MyViewHolder)holder).actcount.setText("0");
        }else{
            ((MyViewHolder)holder).actcount.setText(datas.get(position).getActCount());
        }

        if(datas.get(position).getDepotNo().equals(depot.getDepotNo())&&datas.get(position).getFinishStatus().equals("0")){
            ((MyViewHolder)holder).good.setTextColor(Color.RED);
            ((MyViewHolder)holder).modle.setTextColor(Color.RED);
            ((MyViewHolder)holder).count.setTextColor(Color.RED);
            ((MyViewHolder)holder).customerName.setTextColor(Color.RED);
            ((MyViewHolder)holder).actcount.setTextColor(Color.RED);
        }

        if (mOnItemClickLitener != null){
            //((MyViewHolder)holder).itemView.setBackgroundResource(R.drawable.recycler_bg);
            ((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postion=holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,position);
                    //mOnItemClickLitener.onItemClick(holder.itemView,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView good;
        TextView modle;
        TextView count;
        TextView actcount;
        TextView customerName;
        public MyViewHolder(View view){
            super(view);
            good=(TextView)view.findViewById(R.id.out_order_detial_good);
            modle=(TextView)view.findViewById(R.id.out_order_detial_modle);
            count=(TextView)view.findViewById(R.id.out_order_detial_count);
            actcount=(TextView)view.findViewById(R.id.out_order_detial_actcount);
            customerName=(TextView)view.findViewById(R.id.out_order_detial_customerName);
        }

    }
}
