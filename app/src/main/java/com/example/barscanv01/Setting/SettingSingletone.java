package com.example.barscanv01.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhulin on 2017/8/20.
 */

public class SettingSingletone {

    private boolean addResult;
    private boolean removeResult;
    private boolean addParamPromise;
    private float param;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static SettingSingletone instance = null;

    public SettingSingletone(Context context) {
        sharedPreferences = context.getSharedPreferences("add_remove_result", 0);
        mEditor = sharedPreferences.edit();
        addResult = sharedPreferences.getBoolean("add_result", false);
        removeResult = sharedPreferences.getBoolean("remove_result", false);
        addParamPromise = sharedPreferences.getBoolean("add_param_promise", false);
        param = sharedPreferences.getFloat("param", 0);
    }

    public static SettingSingletone getInstance(Context context) {
        synchronized (SettingSingletone.class) {
            if (instance == null) {
                instance = new SettingSingletone(context);
            }
        }
        return instance;
    }

    public boolean getAddResult() {
        addResult = sharedPreferences.getBoolean("add_result", false);
        return addResult;
    }

    public void setAddResult(boolean addResult) {
        this.addResult = addResult;
        mEditor.putBoolean("add_result", addResult);
        mEditor.commit();
    }

    public boolean getRemoveResult() {
        removeResult = sharedPreferences.getBoolean("remove_result", false);
        return removeResult;
    }

    public void setRemoveResult(boolean removeResult) {
        this.removeResult = removeResult;
        mEditor.putBoolean("remove_result", removeResult);
        mEditor.commit();
    }

    public boolean getAddParamPromiseResult() {
        addParamPromise = sharedPreferences.getBoolean("add_param_promise", false);
        return addParamPromise;
    }

    public void setAddParamPromiseResult(boolean addParamPromise) {
        this.addParamPromise = addParamPromise;
        mEditor.putBoolean("add_param_promise", addParamPromise);
        mEditor.commit();
    }

    public float getParam() {
        param = sharedPreferences.getFloat("param", 0);
        return param;
    }

    public void setParam(float param) {
        this.param = param;
        mEditor.putFloat("param", param);
        mEditor.commit();
    }
}
