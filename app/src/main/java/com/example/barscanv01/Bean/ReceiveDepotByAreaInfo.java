package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhulin on 2017/8/14.
 */

public class ReceiveDepotByAreaInfo {

    /**
     * attributes : {"DepotList":[{"id":"402884435cc8ee8c015cc8f4538f0006","description":"1场区1号库房","createName":"管理员","createBy":"admin","createDate":1498020074383,"updateName":"管理员","updateBy":"admin","updateDate":1498188711100,"depotNo":"A01","depotName":"1号库房","areaId":"402885f45ccf0eb1015ccf0eb1800000"},{"id":"402884435cd7c8df015cd7cd7f7d000c","description":"1场区2号库房","createName":"管理员","createBy":"admin","createDate":1498269187967,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"A02","depotName":"2号库房","areaId":"402885f45ccf0eb1015ccf0eb1800000"},{"id":"4028856b5d5e31d1015d5f51d7c30182","description":"","createName":"测试人员","createBy":"demo","createDate":1500542785477,"updateName":null,"updateBy":null,"updateDate":null,"depotNo":"6666","depotName":"杨阳仓库","areaId":"402885f45ccf0eb1015ccf0eb1800000"}]}
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
        private List<DepotBean> DepotList;

        public List<DepotBean> getDepotList() {
            return DepotList;
        }

        public void setDepotList(List<DepotBean> DepotList) {
            this.DepotList = DepotList;
        }


    }
}
