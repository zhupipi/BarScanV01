package com.example.barscanv01.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barscanv01.Adapter.ScanResultAdapter;
import com.example.barscanv01.Bean.GoodsBarcodeBean;
import com.example.barscanv01.R;
import com.example.barscanv01.Adapter.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by zhupipi on 2017/7/3.
 */

public class ScanResultFragment extends Fragment {
    public RecyclerView resultView;
    public ArrayList<GoodsBarcodeBean> scanResultArryList;
    public ScanResultAdapter scanResultAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.scan_result_frg,container,false);
        scanResultArryList=new ArrayList<GoodsBarcodeBean>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        resultView=(RecyclerView)getView().findViewById(R.id.scan_result_recyclerView);
        resultView.setLayoutManager(new LinearLayoutManager(getActivity()));
        resultView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        resultView.setItemAnimator(new DefaultItemAnimator());
    }
 /*   public void initalData(){
        GoodsBarcodeBean title=new GoodsBarcodeBean();
        title.setBarcode("条形码");
        title.setSpecificationModel("产品规格");
        title.setActWeight("实际重量");
        title.setGoodsName("产品名称");
        scanResultArryList.add(title);
        showData();

    }*/
    public void showData(){
        if(scanResultAdapter==null){
            scanResultAdapter=new ScanResultAdapter(getActivity(),scanResultArryList);
            resultView.setAdapter(scanResultAdapter);
            scanResultAdapter.setOnItemClickListener(new ScanResultAdapter.OnItemClickListener() {
                @Override
                public void OnItemLongClick(View view, int postion) {
                    showPopMenu(view,postion);
                }
            });
        }else{
            scanResultAdapter.notifyDataSetChanged();
        }
    }

    private void showPopMenu(View view, final int postion) {
        PopupMenu popupMenu=new PopupMenu(getActivity(),view);
        popupMenu.getMenuInflater().inflate(R.menu.delete_menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                deleteData(postion);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    public void addData(GoodsBarcodeBean good){
        scanResultArryList.add(good);
        showData();
    }
    public void deleteData(int position){
        scanResultArryList.remove(position);
        showData();
    }
    public void cleanData(){
        scanResultArryList.clear();
        showData();
    }

    public ArrayList<GoodsBarcodeBean> getScanResultArryList() {
        return scanResultArryList;
    }

}
