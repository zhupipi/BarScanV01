package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhupipi on 2017/7/6.
 */

public class ReceivedPositionInfo {

    /**
     * attributes : {"positionList":[{"id":"402884435cc8ee8c015cc8f905a6000c","description":"1号仓库1号库位","createBy":"admin","createName":null,"createDate":1498020382117,"updateName":null,"updateBy":"admin","updateDate":1498020399957,"positionName":"1号库位","positionNo":"P01","depotId":"402884435cc8ee8c015cc8f4538f0006"},{"id":"402884435cc8ee8c015cc8f9b2aa000f","description":"1号仓库2号库位","createBy":"admin","createName":null,"createDate":1498020426410,"updateName":"管理员","updateBy":"admin","updateDate":1498185264807,"positionName":"2号库位","positionNo":"P02","depotId":"402884435cc8ee8c015cc8f4538f0006"}]}
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
        private List<PositionBean> positionList;

        public List<PositionBean> getPositionList() {
            return positionList;
        }

        public void setPositionList(List<PositionBean> positionList) {
            this.positionList = positionList;
        }


    }
}
