package com.example.barscanv01.Bean;

import java.util.Date;

/**
 * Created by zhupipi on 2017/7/11.
 */

public class BizlogBean {

    /** 车牌号 */
    private String plateNo;
    /** 单据号 */
    private String orderNo;
    /** 时间 format = "yyyy-MM-dd" */
    private Date date;
    /** 位置 */
    private String location;
    /** 仓库名称 */
    private String depotName;
    /** 场区名称 */
    private String areaName;
    /** 库位名称 */
    private String positionName;
    /** 进程 */
    private String process;
    /** 进程描述 */
    private String remark;

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
