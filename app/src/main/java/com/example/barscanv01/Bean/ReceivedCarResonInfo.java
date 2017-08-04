package com.example.barscanv01.Bean;

/**
 * Created by zhulin on 2017/8/3.
 */

public class ReceivedCarResonInfo {

    /**
     * attributes : {"reson":"10"}
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
         * reson : 10
         */

        private String reson;

        public String getReson() {
            return reson;
        }

        public void setReson(String reson) {
            this.reson = reson;
        }
    }
}
