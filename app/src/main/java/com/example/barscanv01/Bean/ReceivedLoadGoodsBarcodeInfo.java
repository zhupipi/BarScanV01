package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhulin on 2017/8/2.
 */

public class ReceivedLoadGoodsBarcodeInfo {

    /**
     * attributes : {"GoodsBarcodeEndtityList":[{"id":"4028856b5d5f4670015d6332529a0005","flag":"0","status":"1","specificationModel":"180*3.5*6","createBy":"admin","createName":"管理员","createDate":1500607828000,"updateName":"管理员","updateBy":"admin","updateDate":1500607937000,"depotNo":"A01","goodsId":"sdfsdfsa","barcode":"20170721001","goodsName":"热镀锌铜钢管","positionNo":"P02","actWeight":989.5,"batchNo":"2017072101","qcStatus":"0"},{"id":"4028856b5d5f4670015d646f0402000a","flag":"0","status":"0","specificationModel":"180*3.5*6","createBy":"admin","createName":"管理员","createDate":1500628583000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","goodsId":"afafdafaffad","barcode":"20170721006","goodsName":"热镀锌铜钢管","positionNo":"P02","actWeight":998.2,"batchNo":"2017072101","qcStatus":"0"},{"id":"4028856b5d5f4670015d646d4cf70009","flag":"0","status":"1","specificationModel":"180*3.5*6","createBy":"admin","createName":"管理员","createDate":1500628471000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","goodsId":"djfdskfkajla","barcode":"20170721005","goodsName":"热镀锌铜钢管","positionNo":"P02","actWeight":100,"batchNo":"2017072101","qcStatus":"0"},{"id":"4028856b5d5f4670015d646c60b00008","flag":"0","status":"0","specificationModel":"180*3.5*6","createBy":"admin","createName":"管理员","createDate":1500628410000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","goodsId":"adfafdsafsadf","barcode":"20170721004","goodsName":"热镀锌铜钢管","positionNo":"P02","actWeight":100.21,"batchNo":"20170721001","qcStatus":"0"},{"id":"4028856b5d5f4670015d647ea6bc000b","flag":"0","status":"0","specificationModel":"400*5*6","createBy":"admin","createName":"管理员","createDate":1500629608000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","goodsId":"dfjgfkfljd","barcode":"20170721101","goodsName":"钢塑复合管","positionNo":"P02","actWeight":50.2,"batchNo":"2017072102","qcStatus":"0"},{"id":"4028856b5d5f4670015d6481c85d000d","flag":"0","status":"0","specificationModel":"400*5*6","createBy":"admin","createName":"管理员","createDate":1500629813000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","goodsId":"safafafafafd","barcode":"20170721103","goodsName":"钢塑复合管","positionNo":"P02","actWeight":62.3,"batchNo":"2017072101","qcStatus":"0"},{"id":"4028856b5d5f4670015d6480ac73000c","flag":"0","status":"0","specificationModel":"400*5*6","createBy":"admin","createName":"管理员","createDate":1500629740000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","goodsId":"sfjsjlfsljfl","barcode":"20170721102","goodsName":"钢塑复合管","positionNo":"P02","actWeight":53.2,"batchNo":"20170702","qcStatus":"0"},{"id":"4028856b5d5f4670015d6333a8b00006","flag":"0","status":"1","specificationModel":"180*3.5*6","createBy":"admin","createName":"管理员","createDate":1500607916000,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A01","goodsId":"safdafasaafafsad","barcode":"20170721002","goodsName":"热镀锌铜钢管","positionNo":"P02","actWeight":1000.3,"batchNo":"2017072101","qcStatus":"0"},{"id":"4028856b5d5f4670015d6332529a0005","flag":"0","status":"1","specificationModel":"180*3.5*6","createBy":"admin","createName":"管理员","createDate":1500607828000,"updateName":"管理员","updateBy":"admin","updateDate":1500607937000,"depotNo":"A01","goodsId":"sdfsdfsa","barcode":"20170721001","goodsName":"热镀锌铜钢管","positionNo":"P02","actWeight":989.5,"batchNo":"2017072101","qcStatus":"0"}]}
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
        private List<GoodsBarcodeBean> GoodsBarcodeEndtityList;

        private List<String> customerList;

        public List<GoodsBarcodeBean> getGoodsBarcodeEndtityList() {
            return GoodsBarcodeEndtityList;
        }

        public void setGoodsBarcodeEndtityList(List<GoodsBarcodeBean> GoodsBarcodeEndtityList) {
            this.GoodsBarcodeEndtityList = GoodsBarcodeEndtityList;
        }

        public List<String> getCustomerList() {
            return customerList;
        }

        public void setCustomerList(List<String> customerList) {
            this.customerList = customerList;
        }

    }
}
