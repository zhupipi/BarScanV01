package com.example.barscanv01.Util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhupipi on 2017/6/29.
 */

public class CarPlateUtil {
    private List<String> Provinces;
    public CarPlateUtil(){
        Provinces= Arrays.asList("津","冀","陕","渝","京","豫","云","辽","黑","湘","皖","鲁","新","苏","浙","赣","鄂","桂","甘","晋","蒙","沪","吉","闽","贵","粤","青","藏","川","宁","琼");
    }

    public String getProvince(String carPlate){
        String province=null;
        if(carPlate.length()>2){
            carPlate=carPlate.substring(0,2);
            int id=Integer.parseInt(carPlate);
            province=Provinces.get(id-1);
        }
        return province;
    }
    public int getId(String carPlate){
        int id=0;
        if(carPlate.length()>2){
            carPlate=carPlate.substring(0,1);
            id=Provinces.indexOf(carPlate);}
        return  id;
    }
    public String getplateNum(String carPlate){
        String plateNum=null;
        if(carPlate.length()>2) {
            plateNum = carPlate.substring(1, carPlate.length());
        }
        return  plateNum;
    }

    public List<String> getProvinces() {
        return Provinces;
    }

    public void setProvinces(List<String> provinces) {
        Provinces = provinces;
    }
}
