package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhulin on 2017/8/3.
 */

public class ReceivedInOrderInfo {

    /**
     * attributes : {"inOrderDetailList":[{"number":7,"address":"江苏省南京市雨花区十字大街389号","id":"402884cd5da76d07015da7989fc20013","count":1,"areaNo":"C01","depotNo":"A01","customerCode":"101001","customerName":"南京范氏工贸有限公司","weight":805.7056,"goodsName":"结构圆镀管","goodsCode":"131015130","diameter":20,"thickness":1.3,"meterWeight":0.5995,"numberCount":217,"actWeight":null,"finishStatus":"0","actCount":null,"inOrderId":"402884cd5da76d07015da7989fc20012","actNumber":null,"specificationModel":"4分*1.3"},{"number":7,"address":"","id":"402884cd5da76d07015da7989fc20014","count":3,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","weight":863.0496,"goodsName":"结构圆镀管","goodsCode":"131015140","diameter":20,"thickness":1.4,"meterWeight":0.6421,"numberCount":217,"actWeight":null,"finishStatus":"0","actCount":null,"inOrderId":"402884cd5da76d07015da7989fc20012","actNumber":null,"specificationModel":"4分*1.4"}],"inOrder":{"id":"402884cd5da76d07015da7989fc20012","date":1501755126000,"status":"0","areaNo":"C01","plateNo":"冀B8B3K7","depotNo":"A01","outOrderNo":"5544778899","inOrderNo":"99887766","process":"0"}}
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
         * inOrderDetailList : [{"number":7,"address":"江苏省南京市雨花区十字大街389号","id":"402884cd5da76d07015da7989fc20013","count":1,"areaNo":"C01","depotNo":"A01","customerCode":"101001","customerName":"南京范氏工贸有限公司","weight":805.7056,"goodsName":"结构圆镀管","goodsCode":"131015130","diameter":20,"thickness":1.3,"meterWeight":0.5995,"numberCount":217,"actWeight":null,"finishStatus":"0","actCount":null,"inOrderId":"402884cd5da76d07015da7989fc20012","actNumber":null,"specificationModel":"4分*1.3"},{"number":7,"address":"","id":"402884cd5da76d07015da7989fc20014","count":3,"areaNo":"C01","depotNo":"A01","customerCode":"6666","customerName":"杨阳集团有限公司1","weight":863.0496,"goodsName":"结构圆镀管","goodsCode":"131015140","diameter":20,"thickness":1.4,"meterWeight":0.6421,"numberCount":217,"actWeight":null,"finishStatus":"0","actCount":null,"inOrderId":"402884cd5da76d07015da7989fc20012","actNumber":null,"specificationModel":"4分*1.4"}]
         * inOrder : {"id":"402884cd5da76d07015da7989fc20012","date":1501755126000,"status":"0","areaNo":"C01","plateNo":"冀B8B3K7","depotNo":"A01","outOrderNo":"5544778899","inOrderNo":"99887766","process":"0"}
         */

        private InOrderBean inOrder;
        private List<InOrderDetailBean> inOrderDetailList;

        public InOrderBean getInOrder() {
            return inOrder;
        }

        public void setInOrder(InOrderBean inOrder) {
            this.inOrder = inOrder;
        }

        public List<InOrderDetailBean> getInOrderDetailList() {
            return inOrderDetailList;
        }

        public void setInOrderDetailList(List<InOrderDetailBean> inOrderDetailList) {
            this.inOrderDetailList = inOrderDetailList;
        }
    }
}
