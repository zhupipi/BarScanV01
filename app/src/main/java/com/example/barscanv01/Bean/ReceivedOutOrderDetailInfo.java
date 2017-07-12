package com.example.barscanv01.Bean;

/**
 * Created by zhupipi on 2017/7/12.
 */

public class ReceivedOutOrderDetailInfo {

    /**
     * attributes : {"outOrderDetail":{"address":"21","id":"402888561235655df122s1df2g1h2d","count":5,"createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerName":"天津","customerCode":"21","weight":10,"goodsName":"镀锌管","goodsCode":"GOOD5","actWeight":12,"finishStatus":"0","outOrderId":"402884435d0ce046015d0ce0ae360000","specificationModel":"2*25*6","actCount":5}}
     * msg : 获取OutOrederDetail成功
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
         * outOrderDetail : {"address":"21","id":"402888561235655df122s1df2g1h2d","count":5,"createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null,"updateDate":null,"areaNo":"C01","depotNo":"A01","customerName":"天津","customerCode":"21","weight":10,"goodsName":"镀锌管","goodsCode":"GOOD5","actWeight":12,"finishStatus":"0","outOrderId":"402884435d0ce046015d0ce0ae360000","specificationModel":"2*25*6","actCount":5}
         */

        private OutOrderDetailBean outOrderDetail;

        public OutOrderDetailBean getOutOrderDetail() {
            return outOrderDetail;
        }

        public void setOutOrderDetail(OutOrderDetailBean outOrderDetail) {
            this.outOrderDetail = outOrderDetail;
        }


    }
}
