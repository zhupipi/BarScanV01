package com.example.barscanv01.TitleChangeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barscanv01.R;

/**
 * Created by zhulin on 2017/8/18.
 */

public class OrderNoDetailTitleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.order_no_detail_title_frg,container,false);
        return view;
    }
}
