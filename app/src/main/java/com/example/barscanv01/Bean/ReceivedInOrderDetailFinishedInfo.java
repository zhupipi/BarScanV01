package com.example.barscanv01.Bean;

/**
 * Created by zhulin on 2017/8/5.
 */

public class ReceivedInOrderDetailFinishedInfo {

    /**
     * attributes : {"result":true}
     * jsonStr : {"msg":"操作成功","success":true,"attributes":{"result":true}}
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
         * result : true
         */

        private boolean result;

        public boolean getResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }
}
