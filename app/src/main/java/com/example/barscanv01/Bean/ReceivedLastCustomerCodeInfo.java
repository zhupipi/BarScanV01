package com.example.barscanv01.Bean;

/**
 * Created by zhupipi on 2017/7/10.
 */

public class ReceivedLastCustomerCodeInfo {

    /**
     * attributes : {"lastCustomerCode":"22"}
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
        /**
         * lastCustomerCode : 22
         */

        private String lastCustomerCode;
        private String lastCustomerName;

        public String getLastCustomerCode() {
            return lastCustomerCode;
        }

        public void setLastCustomerCode(String lastCustomerCode) {
            this.lastCustomerCode = lastCustomerCode;
        }

        public String getLastCustomerName() {
            return lastCustomerName;
        }

        public void setLastCustomerName(String lastCustomerName) {
            this.lastCustomerName = lastCustomerName;
        }
    }
}
