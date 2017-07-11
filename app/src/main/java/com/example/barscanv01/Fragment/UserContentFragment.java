package com.example.barscanv01.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.barscanv01.R.id.user_content_warehouse;

/**
 * Created by zhupipi on 2017/6/26.
 */

public class UserContentFragment extends Fragment {
    TextView userRealName;
    TextView userKey;
    TextView userName;
    Spinner depotSpinner;
    Spinner areaSpinner;
    MyApp myApp;
    ArrayList<String> sprinnerWarehouseList;
    ArrayList<String> sprinnerAreaList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_content_frg,container,false);
        return view;
    }

    @Override
    public void onStart() {
        myApp=(MyApp) getActivity().getApplication();
        super.onStart();
        userRealName= (TextView) getView().findViewById(R.id.user_content_user_realname);
        userRealName.setText(myApp.getUserBean().getRealName());
        userKey=(TextView)getView().findViewById(R.id.user_content_userKey);
        userKey.setText(myApp.getUserBean().getUserKey());
        userName=(TextView)getView().findViewById(R.id.user_content_username2);
        userName.setText(myApp.getUserBean().getUserName());
        sprinnerWarehouseList=new ArrayList<String>();
        sprinnerAreaList=new ArrayList<String>();
        depotSpinner= (Spinner) getView().findViewById(R.id.user_content_spinner);
        //mySpinner.setPrompt("选择工作库区");
        if(myApp.getDepotlist()!=null){
            List<DepotBean> depotBeanList=myApp.getDepotlist();
            for(int i=0;i<depotBeanList.size();i++){
                sprinnerWarehouseList.add(depotBeanList.get(i).getDepotName());
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,sprinnerWarehouseList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            depotSpinner.setAdapter(adapter);
        }
        depotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myApp.setCurrentDepot(myApp.getDepotlist().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
