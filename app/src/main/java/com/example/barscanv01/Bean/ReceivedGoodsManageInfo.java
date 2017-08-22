package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhupipi on 2017/7/21.
 */

public class ReceivedGoodsManageInfo {

    /**
     * attributes : {"goodsManageDetailList":[{"id":"4028856b5d981656015d9c9337e3010c","goodsmanageId":"4028856b5d981656015d9c9337e3010b","createName":"测试人员","createBy":"demo","createDate":1501570480100,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"180*3.5*6","goodsName":"热镀锌钢管","goodsCode":"101006"},{"id":"4028856b5da1f8c6015da5bc2f0c001d","goodsmanageId":"4028856b5da1f8c6015da5bc2f0c001c","createName":"测试人员","createBy":"demo","createDate":1501724159757,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"180*3.5*6","goodsName":"热镀锌钢管","goodsCode":"101002"},{"id":"8a8880865dedcea2015dfa6570dc02b9","goodsmanageId":"8a8880865dedcea2015dfa6570db02b8","createName":"测试G2","createBy":"00102","createDate":1503144538333,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"4分*1.3","goodsName":"结构圆镀管","goodsCode":"131015130"},{"id":"8a88808a5dff371d015e0294cc44005b","goodsmanageId":"8a88808a5dff371d015e0294cc44005a","createName":"测试人员","createBy":"demo","createDate":1503281859653,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"4分*1.3","goodsName":"普通圆镀管","goodsCode":"132015130"},{"id":"8a88808a5dff371d015e0294cc45005c","goodsmanageId":"8a88808a5dff371d015e0294cc44005a","createName":"测试人员","createBy":"demo","createDate":1503281859653,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"4分*1.5","goodsName":"普通圆镀管","goodsCode":"132015150"},{"id":"8a88808a5dff371d015e0294cc45005d","goodsmanageId":"8a88808a5dff371d015e0294cc44005a","createName":"测试人员","createBy":"demo","createDate":1503281859653,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"4分*2.0","goodsName":"普通圆镀管","goodsCode":"132015200"},{"id":"8a88808a5dff371d015e029b9038006d","goodsmanageId":"8a88808a5dff371d015e029b9038006c","createName":"测试人员","createBy":"demo","createDate":1503282303033,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"19.6*0.621*6","goodsName":"镀锌管4分1.3","goodsCode":"1320041301"},{"id":"8a88808a5dff371d015e029b9039006e","goodsmanageId":"8a88808a5dff371d015e029b9038006c","createName":"测试人员","createBy":"demo","createDate":1503282303033,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"20*1.5*6","goodsName":"镀锌管4分1.5","goodsCode":"1320041501"},{"id":"8a88808a5dff371d015e029b9039006f","goodsmanageId":"8a88808a5dff371d015e029b9038006c","createName":"测试人员","createBy":"demo","createDate":1503282303033,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"20.5*2*6","goodsName":"镀锌管4分2.0","goodsCode":"1320042001"},{"id":"8a88808e5ddf0480015ddf640d520013","goodsmanageId":"8a88808e5ddf0480015ddf640d520012","createName":"刘士昊","createBy":"liushihao","createDate":1502691462483,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"165*4.25*6","goodsName":"热镀锌焊管","goodsCode":"D60425"},{"id":"8a88808e5ddf0480015ddf640d530014","goodsmanageId":"8a88808e5ddf0480015ddf640d520012","createName":"刘士昊","createBy":"liushihao","createDate":1502691462483,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"114*3.5*6","goodsName":"热镀锌焊管","goodsCode":"D4035"},{"id":"8a88808e5ddf0480015ddf640d530015","goodsmanageId":"8a88808e5ddf0480015ddf640d520012","createName":"刘士昊","createBy":"liushihao","createDate":1502691462483,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"48*3.25*6","goodsName":"热镀锌焊管","goodsCode":"D15325"},{"id":"8a88808e5ddf0480015ddf640d530016","goodsmanageId":"8a88808e5ddf0480015ddf640d520012","createName":"刘士昊","createBy":"liushihao","createDate":1502691462483,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"165*4.25*6","goodsName":"热镀锌燃气管","goodsCode":"DR60425"},{"id":"8a88808e5ddf0480015ddf640d540017","goodsmanageId":"8a88808e5ddf0480015ddf640d520012","createName":"刘士昊","createBy":"liushihao","createDate":1502691462483,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"114*3.5*6","goodsName":"热镀锌燃气管","goodsCode":"DR4035"},{"id":"8a88808e5ddf0480015ddf640d540018","goodsmanageId":"8a88808e5ddf0480015ddf640d520012","createName":"刘士昊","createBy":"liushihao","createDate":1502691462483,"updateName":null,"updateBy":null,"updateDate":null,"specificationModel":"48*3.25*6","goodsName":"热镀锌燃气管","goodsCode":"DR15325"}]}
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
        private List<GoodsManageDetailBean> goodsManageDetailList;

        public List<GoodsManageDetailBean> getGoodsManageDetailList() {
            return goodsManageDetailList;
        }

        public void setGoodsManageDetailList(List<GoodsManageDetailBean> goodsManageDetailList) {
            this.goodsManageDetailList = goodsManageDetailList;
        }
    }
}
