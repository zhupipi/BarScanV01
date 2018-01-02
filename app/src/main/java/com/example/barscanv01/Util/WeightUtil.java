package com.example.barscanv01.Util;

import com.example.barscanv01.MyApp;

/**
 * Created by zhulin on 2018/1/1.
 */

public class WeightUtil {

    public static float adjsutWeighting(float actWeight,float actParam){
        float adjustWeight=0;
        adjustWeight=actWeight*actParam;
        float temp=Math.round(adjustWeight*1000);
        adjustWeight=temp/1000;
        return adjustWeight;
    }
}
