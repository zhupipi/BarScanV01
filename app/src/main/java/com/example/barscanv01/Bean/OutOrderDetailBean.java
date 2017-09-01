package com.example.barscanv01.Bean;

/**
 * Created by zhupipi on 2017/6/30.
 */

public class OutOrderDetailBean {

    /**
     * address : 邯郸
     * id : 402884435cf69ef6015cf69ff3850001
     * count : 200
     * specificationModel : 8*2.0*20
     * actCount : null
     * outOrderId : 402884435cf69ef6015cf69ff37a0000
     * createBy : admin
     * createName : 管理员
     * createDate : 1498786296710
     * updateName : null
     * updateBy : null
     * updateDate : null
     * areaNo : C02
     * customerName : 友发集团
     * customerCode : C01
     * depotNo : A02
     * goodsName : 镀锌管2
     * goodsCode : GOOD2
     * weight : 300
     * actWeight : null
     */

    private String address;
    private String id;
    private int count;
    private String specificationModel;
    private String actCount;
    private String outOrderId;
    private String areaNo;
    private String customerName;
    private String customerCode;
    private String depotNo;
    private String goodsName;
    private String goodsCode;
    private float weight;
    private String actWeight;
    private String finishStatus;
    private boolean focus;
    private boolean removePromise;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSpecificationModel() {
        return specificationModel;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
    }

    //!!!将ActCount设为String 明天需验证
    public String getActCount() {
        return actCount;
    }

    public void setActCount(String actCount) {
        this.actCount = actCount;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getDepotNo() {
        return depotNo;
    }

    public void setDepotNo(String depotNo) {
        this.depotNo = depotNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getActWeight() {
        return actWeight;
    }

    public void setActWeight(String actWeight) {
        this.actWeight = actWeight;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public boolean isRemovePromise() {
        return removePromise;
    }

    public void setRemovePromise(boolean removePromise) {
        this.removePromise = removePromise;
    }
}
