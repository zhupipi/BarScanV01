package com.example.barscanv01.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupipi on 2017/7/3.
 */

public class ScanResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<GoodsBarcodeBean> datas;
    public interface OnItemClickListener{
        void OnItemLongClick(View view,int postion);
    }
    private OnItemClickListener mItemClickListener;
    public void  setOnItemClickListener(OnItemClickListener itemClickListener){
        this.mItemClickListener=itemClickListener;
    }
    public ScanResultAdapter(Context context,List<GoodsBarcodeBean> datas){
        this.mContext=context;
        this.datas=datas;
    }

    @Override
    public ScanResultViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        ScanResultViewHodler hodler=new ScanResultViewHodler(LayoutInflater.from(mContext).inflate(R.layout.scan_result_item,parent,false));
        return hodler;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((ScanResultViewHodler)holder).barcode.setText(datas.get(position).getBarcode());
        ((ScanResultViewHodler)holder).actWeight.setText(String.valueOf(datas.get(position).getActWeight()));
        ((ScanResultViewHodler) holder).goodName.setText(datas.get(position).getGoodsName());
        ((ScanResultViewHodler)holder).modle.setText(datas.get(position).getSpecificationModel());
        if(mItemClickListener!=null){
            ((ScanResultViewHodler)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=holder.getLayoutPosition();
                    mItemClickListener.OnItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ScanResultViewHodler extends RecyclerView.ViewHolder{
        TextView barcode;
        TextView goodName;
        TextView actWeight;
        TextView modle;
        public ScanResultViewHodler(View view){
            super(view);
            barcode= (TextView) view.findViewById(R.id.scan_result_bar_code);
            goodName=(TextView)view.findViewById(R.id.scan_result_good_name);
            actWeight=(TextView)view.findViewById(R.id.scan_reuslt_act_weight);
            modle=(TextView)view.findViewById(R.id.scan_result_modle);
        }
    }
}
