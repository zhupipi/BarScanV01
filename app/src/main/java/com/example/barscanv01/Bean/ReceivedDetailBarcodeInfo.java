package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhulin on 2017/8/4.
 */

public class ReceivedDetailBarcodeInfo {
    /**
     * attributes : {"DetailBarcodeEntityList":[{"id":"402884cd5da76d07015da79082e80009","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288d5044f","orderDetailId":"402884cd5da6afab015da6ba6c9e0004","barcode":"20170721002","batchNumber":"2017072101","weight":3.8556,"scanUserName":"test3","scanDate":1501754852070},{"id":"402884cd5da76d07015da7908317000a","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288d5044f","orderDetailId":"402884cd5da6afab015da6ba6c9e0004","barcode":"20170721001","batchNumber":"2017072101","weight":3.8623,"scanUserName":"test3","scanDate":1501754852117},{"id":"402884cd5da76d07015da7908353000b","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288d5044f","orderDetailId":"402884cd5da6afab015da6ba6c9f0005","barcode":"20170721006","batchNumber":"2017072101","weight":3.853,"scanUserName":"test3","scanDate":1501754852177},{"id":"402884cd5da76d07015da79083a4000c","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288d5044f","orderDetailId":"402884cd5da6afab015da6ba6c9f0005","barcode":"20170721005","batchNumber":"2017072101","weight":3.8544,"scanUserName":"test3","scanDate":1501754852257},{"id":"402884cd5da76d07015da79083cc000d","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"101001","customerName":"南京范氏工贸有限公司","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288cc044e","orderDetailId":"402884cd5da6afab015da6ba6c9e0003","barcode":"20170721101","batchNumber":"2017072102","weight":3.597,"scanUserName":"test3","scanDate":1501754852300},{"id":"402884cd5da76d07015da79083f7000e","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"101001","customerName":"南京范氏工贸有限公司","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288cc044e","orderDetailId":"402884cd5da6afab015da6ba6c9e0003","barcode":"20170721103","batchNumber":"2017072101","weight":3.5972,"scanUserName":"test3","scanDate":1501754852340}]}
     * msg : 明细条码表查询成功
     * success : true
     */

    private AttributesBean attributes;
    private String msg;
    private boolean success;

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class AttributesBean {
        private List<DetailBarcodeBean> DetailBarcodeEntityList;

        public List<DetailBarcodeBean> getDetailBarcodeEntityList() {
            return DetailBarcodeEntityList;
        }

        public void setDetailBarcodeEntityList(List<DetailBarcodeBean> DetailBarcodeEntityList) {
            this.DetailBarcodeEntityList = DetailBarcodeEntityList;
        }
    }
    /**
     * attributes : {"DetailBarcodeEntityList":[{"id":"402884cd5da76d07015da79082e80009","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288d5044f","orderDetailId":"402884cd5da6afab015da6ba6c9e0004","barcode":"20170721002","batchNumber":"2017072101","weight":3.8556,"scanUserName":"test3","scanDate":1501754852070},{"id":"402884cd5da76d07015da7908317000a","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288d5044f","orderDetailId":"402884cd5da6afab015da6ba6c9e0004","barcode":"20170721001","batchNumber":"2017072101","weight":3.8623,"scanUserName":"test3","scanDate":1501754852117},{"id":"402884cd5da76d07015da7908353000b","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288d5044f","orderDetailId":"402884cd5da6afab015da6ba6c9f0005","barcode":"20170721006","batchNumber":"2017072101","weight":3.853,"scanUserName":"test3","scanDate":1501754852177},{"id":"402884cd5da76d07015da79083a4000c","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288d5044f","orderDetailId":"402884cd5da6afab015da6ba6c9f0005","barcode":"20170721005","batchNumber":"2017072101","weight":3.8544,"scanUserName":"test3","scanDate":1501754852257},{"id":"402884cd5da76d07015da79083cc000d","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"101001","customerName":"南京范氏工贸有限公司","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288cc044e","orderDetailId":"402884cd5da6afab015da6ba6c9e0003","barcode":"20170721101","batchNumber":"2017072102","weight":3.597,"scanUserName":"test3","scanDate":1501754852300},{"id":"402884cd5da76d07015da79083f7000e","flag":"0","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerCode":"101001","customerName":"南京范氏工贸有限公司","orderId":"402884cd5da6afab015da6ba6c9e0002","goodsId":"40285c815da67e6d015da68288cc044e","orderDetailId":"402884cd5da6afab015da6ba6c9e0003","barcode":"20170721103","batchNumber":"2017072101","weight":3.5972,"scanUserName":"test3","scanDate":1501754852340}]}
     * msg : 明细条码表查询成功
     * success : true
     */


}
