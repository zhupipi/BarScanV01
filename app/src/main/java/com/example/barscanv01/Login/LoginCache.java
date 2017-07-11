package com.example.barscanv01.Login;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupipi on 2017/6/22.
 */

public class LoginCache {
    SharedPreferences mSharedPreference;
    SharedPreferences.Editor mEditor;
    ArrayList<String> nameList;
    public LoginCache(Context context){
        mSharedPreference=context.getSharedPreferences("userName",0);
        mEditor=mSharedPreference.edit();
        Gson gson=new Gson();
        String nameJson=mSharedPreference.getString("userList",null);
        nameList=gson.fromJson(nameJson, new TypeToken<List<String>>(){}.getType());
        if(nameJson==null){
            nameList=new ArrayList<String>();
        }
//        nameList=gson.fromJson(nameJson, new TypeToken<List<String>>(){}.getType());
    }

    public List<String> getNameList() {
        return nameList;
    }

    public String getIntalName() {
        String name;
        if (nameList.size()>0) {
            name = nameList.get(0);
        } else {
            name = "";
        }
        return name;
    }

    public void putName(String name) {

        for(int i=0;i<nameList.size()-1;i++){
            if(nameList.get(i)!=null){
                if(nameList.get(i).equals(name)){
                    nameList.remove(i);
                }
            }
        }
        nameList.add(0, name);
        if (nameList.size() > 10) {
            nameList.remove(nameList.size() - 1);
        }
        Gson gson = new Gson();
        String nameJson = gson.toJson(nameList);
        mEditor.putString("userList", nameJson);
        mEditor.commit();
    }
}
