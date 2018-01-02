package com.example.barscanv01.Bean;

/**
 * Created by zhulin on 2017/12/30.
 */

public class ParamBean {

    /**
     * attributes : {"param":"0.0025"}
     * success : true
     * obj : null
     * msg : 操作成功
     */

    private AttributesBean attributes;
    private boolean success;
    private String msg;

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class AttributesBean {
        /**
         * param : 0.0025
         */

        private String param;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }
}
