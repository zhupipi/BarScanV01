package com.example.barscanv01.Bean;

/**
 * Created by zhupipi on 2017/7/12.
 */

public class ReceivedCheckOutOrderFinishedInfo {

    /**
     * attributes : {"checkResult":false}
     * success : true
     */

    private AttributesBean attributes;
    private boolean success;

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

    public static class AttributesBean {
        /**
         * checkResult : false
         */

        private boolean checkResult;

        public boolean isCheckResult() {
            return checkResult;
        }

        public void setCheckResult(boolean checkResult) {
            this.checkResult = checkResult;
        }
    }
}
