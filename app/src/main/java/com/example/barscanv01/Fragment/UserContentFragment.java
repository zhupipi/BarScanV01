package com.example.barscanv01.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barscanv01.Bean.AreaBean;
import com.example.barscanv01.Bean.DepotBean;
import com.example.barscanv01.MyApp;
import com.example.barscanv01.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.barscanv01.R.id.delivery_bill_nodetail_result_show;
import static com.example.barscanv01.R.id.transition_current_scene;
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
    EditText deviceName;
    TextView deviceId;
    Button deviceNameChangedButton;
    TextView areaName;

    MyApp myApp;
    ArrayList<String> sprinnerWarehouseList;
    List<AreaBean> areaList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_content_frg, container, false);
        return view;
    }

    @Override
    public void onStart() {
        myApp = (MyApp) getActivity().getApplication();
        super.onStart();
        sharedPreferences = getContext().getSharedPreferences("device_name", 0);
        editor = sharedPreferences.edit();
        userRealName = (TextView) getView().findViewById(R.id.user_content_user_realname);
        userRealName.setText(myApp.getUserBean().getRealName());
        userKey = (TextView) getView().findViewById(R.id.user_content_userKey);
        userKey.setText(myApp.getUserBean().getUserKey());
        userName = (TextView) getView().findViewById(R.id.user_content_username2);
        userName.setText(myApp.getUserBean().getUserName());
        areaName = (TextView) getView().findViewById(R.id.user_content_area_name);
        areaList = new ArrayList<AreaBean>();
        getDepotandArea();
        getDeviceInfo();
        sharedPreferences1 = getActivity().getSharedPreferences("currentDepot", 0);
        editor1 = sharedPreferences.edit();
        initalDepot();
    }

    private void initalDepot() {
        int id = sharedPreferences.getInt("currentId", 0);
        if (myApp.getDepotlist().size() > 0) {
            if(myApp.getDepotlist().size()>id) {
                depotSpinner.setSelection(id);
            }
        }
    }

    private void getDepotandArea() {
        sprinnerWarehouseList = new ArrayList<String>();
        depotSpinner = (Spinner) getView().findViewById(R.id.user_content_spinner);
        areaList = myApp.getArealist();
        //mySpinner.setPrompt("选择工作库区");
        if (myApp.getDepotlist() != null) {
            List<DepotBean> depotBeanList = myApp.getDepotlist();
            for (int i = 0; i < depotBeanList.size(); i++) {
                sprinnerWarehouseList.add(depotBeanList.get(i).getDepotName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, sprinnerWarehouseList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            depotSpinner.setAdapter(adapter);
        }
        depotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myApp.setCurrentDepot(myApp.getDepotlist().get(position));
                if (myApp.getDepotlist().size() == areaList.size()) {
                    myApp.setCurrentAreaBean(areaList.get(position));
                    areaName.setText(areaList.get(position).getAreaName());
                    editor1.putInt("currentId", position);
                    editor1.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDeviceInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String device_id = telephonyManager.getDeviceId();
        deviceId = (TextView) getView().findViewById(R.id.device_id);
        deviceId.setText(device_id);
        deviceName = (EditText) getView().findViewById(R.id.edit_device_name);
        String device_name = sharedPreferences.getString("device_name", "");
        if (!device_name.equals("")) {
            deviceName.setText(device_name);
        }
        deviceNameChangedButton = (Button) getView().findViewById(R.id.change_device_name);
        deviceNameChangedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("device_name", deviceName.getText().toString());
                editor.commit();
                Toast.makeText(getActivity(), "更改设备名称成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        editor.clear();
    }
}
