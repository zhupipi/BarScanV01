package com.example.barscanv01.Bean;

/**
 * Created by zhupipi on 2017/7/10.
 */

public class AreaBean {
    private String areaName;
    private String areaNo;
    public AreaBean(String areaName,String areaNo){
        this.areaName=areaName;
        this.areaNo=areaNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }
}
