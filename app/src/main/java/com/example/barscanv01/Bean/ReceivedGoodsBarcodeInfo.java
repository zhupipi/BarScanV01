package com.example.barscanv01.Bean;

/**
 * Created by zhupipi on 2017/7/3.
 */

public class ReceivedGoodsBarcodeInfo {

    /**
     * attributes : {"goodsBarcode":{"id":"4028849f5d062208015d062208bd0000","flag":"0","status":"0","createBy":"admin","createName":"管理员","createDate":1499046480000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","specificationModel":"5.26*4.16*6","goodsId":"402885f45ce351da015ce352cb6c0001","barcode":"25641660020170703","goodsName":"1","batchNo":"201700703A0825","actWeight":"2.5","positionNo":"P01","qcStatus":"0"}}
     * msg : 操作成功
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
        /**
         * goodsBarcode : {"id":"4028849f5d062208015d062208bd0000","flag":"0","status":"0","createBy":"admin","createName":"管理员","createDate":1499046480000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","specificationModel":"5.26*4.16*6","goodsId":"402885f45ce351da015ce352cb6c0001","barcode":"25641660020170703","goodsName":"1","batchNo":"201700703A0825","actWeight":"2.5","positionNo":"P01","qcStatus":"0"}
         */

        private GoodsBarcodeBean goodsBarcode;

        public GoodsBarcodeBean getGoodsBarcode() {
            return goodsBarcode;
        }

        public void setGoodsBarcode(GoodsBarcodeBean goodsBarcode) {
            this.goodsBarcode = goodsBarcode;
        }


 }
}
