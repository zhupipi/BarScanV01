package com.example.barscanv01.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barscanv01.Bean.GoodsManageDetailBean;
import com.example.barscanv01.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by zhulin on 2017/8/23.
 */

public class GoodAddDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GoodsManageDetailBean> datas;

    public GoodAddDetailAdapter(Context context, List<GoodsManageDetailBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public GoodAddDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GoodAddDetailViewHolder holder = new GoodAddDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.add_goods_detail, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GoodAddDetailViewHolder) holder).goodName.setText(datas.get(position).getGoodsName());
        ((GoodAddDetailViewHolder) holder).goodModle.setText(datas.get(position).getSpecificationModel());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class GoodAddDetailViewHolder extends RecyclerView.ViewHolder {
        TextView goodName;
        TextView goodModle;

        public GoodAddDetailViewHolder(View view) {
            super(view);
            goodName = (TextView) view.findViewById(R.id.add_good_detail_good_name);
            goodModle = (TextView) view.findViewById(R.id.add_good_detail_good_modle);
        }
    }
}
