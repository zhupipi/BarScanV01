package com.example.barscanv01.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zhulin on 2017/8/12.
 */

public class TranslateScanResultAdapter extends RecyclerView.Adapter {
    Context context;
    List<GoodsBarcodeBean> datas;

    public interface OnItemClickListener {
        void OnItemLongClick(View view, int postion);
    }

    private ScanResultAdapter.OnItemClickListener mItemClickListener;

    public TranslateScanResultAdapter(Context context,List<GoodsBarcodeBean> datas){
        this.context=context;
        this.datas=datas;

    }

    public void setOnItemClickListener(ScanResultAdapter.OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public TranslateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TranslateViewHolder viewHolder = new TranslateViewHolder(LayoutInflater.from(context).inflate(R.layout.translate_scan_result_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((TranslateViewHolder) holder).modle.setText(datas.get(position).getSpecificationModel());
        ((TranslateViewHolder) holder).barcode.setText(datas.get(position).getBarcode());
        ((TranslateViewHolder) holder).goodName.setText(datas.get(position).getGoodsName());
        ((TranslateViewHolder) holder).weight.setText(String.valueOf(datas.get(position).getAdjustWeight()));
        ((TranslateViewHolder) holder).batchNo.setText(datas.get(position).getBatchNo());
        if (mItemClickListener != null) {
            ((TranslateViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mItemClickListener.OnItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class TranslateViewHolder extends RecyclerView.ViewHolder {
        TextView barcode;
        TextView modle;
        TextView goodName;
        TextView batchNo;
        TextView weight;

        public TranslateViewHolder(View view) {
            super(view);
            barcode = (TextView) view.findViewById(R.id.translate_scan_result_barcode);
            modle = (TextView) view.findViewById(R.id.translate_scan_result_modle);
            goodName = (TextView) view.findViewById(R.id.translate_scan_result_name);
            batchNo = (TextView) view.findViewById(R.id.translate_scan_result_batch_no);
            weight = (TextView) view.findViewById(R.id.translate_scan_result_weight);
        }
    }
}
