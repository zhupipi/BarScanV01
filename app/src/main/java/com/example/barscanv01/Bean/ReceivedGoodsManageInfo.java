package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhupipi on 2017/7/21.
 */

public class ReceivedGoodsManageInfo {

    /**
     * attributes : {"goodsList":[{"id":"402884435d5ecfd6015d5f1f8a5e000d","date":1500625885000,"specificationModel":"180*3.5*6","updateDate":null,"goodsName":"热镀锌钢管","goodsCode":"101003","createBy":"admin","createName":"管理员","createDate":1500539488863,"updateName":null,"updateBy":null},{"id":"402884435d5f2073423d5f2153ca0002","date":1500615742000,"specificationModel":"180*3.5*6","updateDate":null,"goodsName":"热镀锌铜钢管","goodsCode":"101007","createBy":null,"createName":null,"createDate":null,"updateName":null,"updateBy":null}]}
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
        private List<GoodsListBean> goodsList;

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * id : 402884435d5ecfd6015d5f1f8a5e000d
             * date : 1500625885000
             * specificationModel : 180*3.5*6
             * updateDate : null
             * goodsName : 热镀锌钢管
             * goodsCode : 101003
             * createBy : admin
             * createName : 管理员
             * createDate : 1500539488863
             * updateName : null
             * updateBy : null
             */

            private String specificationModel;
            private String goodsName;
            private String goodsCode;

            public String getSpecificationModel() {
                return specificationModel;
            }

            public void setSpecificationModel(String specificationModel) {
                this.specificationModel = specificationModel;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }
        }
    }
}
