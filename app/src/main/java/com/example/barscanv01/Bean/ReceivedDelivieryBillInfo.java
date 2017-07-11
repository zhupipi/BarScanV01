package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhupipi on 2017/6/30.
 */

public class ReceivedDelivieryBillInfo {

    /**
     * attributes : {"outOrderDetailList":[{"address":"邯郸","id":"402884435cf69ef6015cf69ff3850001","count":200,"specificationModel":"8*2.0*20","actCount":null,"outOrderId":"402884435cf69ef6015cf69ff37a0000","createBy":"admin","createName":"管理员","createDate":1498786296710,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C02","customerName":"友发集团","customerCode":"C01","depotNo":"A02","goodsName":"镀锌管2","goodsCode":"GOOD2","weight":300,"actWeight":null}],"outOrder":{"id":"402884435cf69ef6015cf69ff37a0000","status":"0","grossWeightDate":null,"createBy":"admin","createName":"管理员","createDate":1498786296707,"updateName":"管理员","updateBy":"admin","updateDate":1498786299153,"plateNo":"津A00000","outOrderNo":"LORDERNO2","process":"1","tare":null,"tareDate":null,"grossWeight":null,"saleOrderNo":"SEALNO2"}}
     * msg : 操作成功
     * success : true
     * obj : null
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
         * outOrderDetailList : [{"address":"邯郸","id":"402884435cf69ef6015cf69ff3850001","count":200,"specificationModel":"8*2.0*20","actCount":null,"outOrderId":"402884435cf69ef6015cf69ff37a0000","createBy":"admin","createName":"管理员","createDate":1498786296710,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C02","customerName":"友发集团","customerCode":"C01","depotNo":"A02","goodsName":"镀锌管2","goodsCode":"GOOD2","weight":300,"actWeight":null}]
         * outOrder : {"id":"402884435cf69ef6015cf69ff37a0000","status":"0","grossWeightDate":null,"createBy":"admin","createName":"管理员","createDate":1498786296707,"updateName":"管理员","updateBy":"admin","updateDate":1498786299153,"plateNo":"津A00000","outOrderNo":"LORDERNO2","process":"1","tare":null,"tareDate":null,"grossWeight":null,"saleOrderNo":"SEALNO2"}
         */

        private OutOrderBean outOrder;
        private List<OutOrderDetailBean> outOrderDetailList;

        public OutOrderBean getOutOrder() {
            return outOrder;
        }

        public void setOutOrder(OutOrderBean outOrder) {
            this.outOrder = outOrder;
        }

        public List<OutOrderDetailBean> getOutOrderDetailList() {
            return outOrderDetailList;
        }

        public void setOutOrderDetailList(List<OutOrderDetailBean> outOrderDetailList) {
            this.outOrderDetailList = outOrderDetailList;
        }




    }
}
