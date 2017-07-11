package com.example.barscanv01.Bean;

import java.util.Date;

/**
 * Created by zhupipi on 2017/7/5.
 */

public class DetailBarcodeEntity {
    private java.lang.String id;
    //产品ID
    private java.lang.String goodsId;
    /** 通知单ID */
    private java.lang.String orderId;
    /** 通知单明细ID */
    private java.lang.String orderDetailId;
    /** 厂区编号 */
    private java.lang.String areaNo;
    /** 库房编号 */
    private java.lang.String depotNo;
    /** 产品条形码 */
    private java.lang.String barcode;
    /** 批次号 */
    private java.lang.String batchNumber;
    /** 重量 */
    private java.math.BigDecimal weight;
    /** 标志 */
    private java.lang.String flag;
    /** 扫码人员 */
    private java.lang.String scanUserName;
    /** 扫码时间 */
    //@Excel(name = "扫码时间", format = "yyyy-MM-dd")
    private java.util.Date scanDate;
    /** 客户编码 */
    private java.lang.String customerCode;
    /** 客户名称 */
    private java.lang.String customerName;

    public java.lang.String getId()
    {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             主键
     */
    public void setId(java.lang.String id)
    {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 产品ID
     */

    public java.lang.String getGoodsId()
    {
        return this.goodsId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             产品ID
     */
    public void setGoodsId(java.lang.String goodsId)
    {
        this.goodsId = goodsId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 通知单ID
     */

    public java.lang.String getOrderId()
    {
        return this.orderId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             通知单ID
     */
    public void setOrderId(java.lang.String orderId)
    {
        this.orderId = orderId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 通知单明细ID
     */

    public java.lang.String getOrderDetailId()
    {
        return this.orderDetailId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             通知单明细ID
     */
    public void setOrderDetailId(java.lang.String orderDetailId)
    {
        this.orderDetailId = orderDetailId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 厂区编号
     */

    public java.lang.String getAreaNo()
    {
        return this.areaNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             厂区编号
     */
    public void setAreaNo(java.lang.String areaNo)
    {
        this.areaNo = areaNo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 库房编号
     */

    public java.lang.String getDepotNo()
    {
        return this.depotNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             库房编号
     */
    public void setDepotNo(java.lang.String depotNo)
    {
        this.depotNo = depotNo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 产品条形码
     */

    public java.lang.String getBarcode()
    {
        return this.barcode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             产品条形码
     */
    public void setBarcode(java.lang.String barcode)
    {
        this.barcode = barcode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 批次号
     */

    public java.lang.String getBatchNumber()
    {
        return this.batchNumber;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             批次号
     */
    public void setBatchNumber(java.lang.String batchNumber)
    {
        this.batchNumber = batchNumber;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 重量
     */

    public java.math.BigDecimal getWeight()
    {
        return this.weight;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal
     *             重量
     */
    public void setWeight(java.math.BigDecimal weight)
    {
        this.weight = weight;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 标志
     */

    public java.lang.String getFlag()
    {
        return this.flag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             标志
     */
    public void setFlag(java.lang.String flag)
    {
        this.flag = flag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 扫码人员
     */

    public java.lang.String getScanUserName()
    {
        return this.scanUserName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             扫码人员
     */
    public void setScanUserName(java.lang.String scanUserName)
    {
        this.scanUserName = scanUserName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 扫码时间
     */

    public java.util.Date getScanDate()
    {
        return this.scanDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date
     *             扫码时间
     * @param date
     */
    public void setScanDate(Date date)
    {
        this.scanDate = scanDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 客户编码
     */

    public java.lang.String getCustomerCode()
    {
        return this.customerCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             客户编码
     */
    public void setCustomerCode(java.lang.String customerCode)
    {
        this.customerCode = customerCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 客户名称
     */

    public java.lang.String getCustomerName()
    {
        return this.customerName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String
     *             客户名称
     */
    public void setCustomerName(java.lang.String customerName)
    {
        this.customerName = customerName;
    }
}
