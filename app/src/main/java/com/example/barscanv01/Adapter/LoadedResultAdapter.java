package com.example.barscanv01.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.R;

import java.util.List;

/**
 * Created by zhulin on 2017/8/2.
 */

public class LoadedResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private List<GoodsBarcodeBean> datas;
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    private OutOrderDetailAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OutOrderDetailAdapter.OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public LoadedResultAdapter(Context context, List<GoodsBarcodeBean> datas) {
        mContext=context;
        this.datas=datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       MyViewHolder holder=new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.detail_goods_barcode_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).barcode.setText(datas.get(position).getBarcode());
        ((MyViewHolder)holder).goodName.setText(datas.get(position).getGoodsName());
        ((MyViewHolder)holder).goodModle.setText(datas.get(position).getSpecificationModel());
        ((MyViewHolder)holder).actWeight.setText(datas.get(position).getActWeight());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView barcode;
        TextView goodName;
        TextView goodModle;
        TextView actWeight;
        public MyViewHolder(View view) {
            super(view);
            barcode= (TextView) view.findViewById(R.id.detail_barcode_barcode);
            goodName= (TextView) view.findViewById(R.id.detail_barcode_goodname);
            goodModle= (TextView) view.findViewById(R.id.detail_barcode_goodmodle);
            actWeight= (TextView) view.findViewById(R.id.detail_barcode_weight);
        }
    }


}
