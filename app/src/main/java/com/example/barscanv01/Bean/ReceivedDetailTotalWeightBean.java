package com.example.barscanv01.Bean;

/**
 * Created by zhulin on 2017/7/26.
 */

public class ReceivedDetailTotalWeightBean {

    /**
     * attributes : {"totalWeight":6292.11}
     * msg : 获取发货单明细重量成功
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
         * totalWeight : 6292.11
         */

        private float totalWeight;

        public float getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(float totalWeight) {
            this.totalWeight = totalWeight;
        }
    }
}
