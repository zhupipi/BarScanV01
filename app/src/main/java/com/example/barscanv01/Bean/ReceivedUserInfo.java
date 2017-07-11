package com.example.barscanv01.Bean;

import java.util.List;

/**
 * Created by zhupipi on 2017/6/25.
 */

public class ReceivedUserInfo {

    /**
     * attributes : {"depotlist":[{"id":"402884435cc8ee8c015cc8f4538f0006","description":"1场区1号库房","createBy":"admin","createName":null,"createDate":1498020074383,"updateName":"管理员","updateBy":"admin","updateDate":1498188711100,"depotName":"1号库房","depotNo":"A01","areaId":"402885f45ccf0eb1015ccf0eb1800000"}],"user":{"officePhone":"","mobilePhone":"","realName":"测试员3","signature":null,"userName":"test3","status":1,"deviceId":null,"browser":null,"deleteFlag":0,"departId":null,"currentDepart":{"address":null,"description":null,"orgType":null,"tspdepart":null,"tsdeparts":[],"departname":null,"orgCode":null,"mobile":null,"fax":null,"departOrder":null,"id":null},"userKey":"库管员","id":"402884435cb4a6b5015cb4c148800038"}}
     * obj : null
     * msg : 操作成功
     * success : true
     */

    private AttributesBean attributes;

    private String msg;
    private boolean success;

    public AttributesBean getAttributes() {
        return attributes;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class AttributesBean {
        /**
         * depotlist : [{"id":"402884435cc8ee8c015cc8f4538f0006","description":"1场区1号库房","createBy":"admin","createName":null,"createDate":1498020074383,"updateName":"管理员","updateBy":"admin","updateDate":1498188711100,"depotName":"1号库房","depotNo":"A01","areaId":"402885f45ccf0eb1015ccf0eb1800000"}]
         * user : {"officePhone":"","mobilePhone":"","realName":"测试员3","signature":null,"userName":"test3","status":1,"deviceId":null,"browser":null,"deleteFlag":0,"departId":null,"currentDepart":{"address":null,"description":null,"orgType":null,"tspdepart":null,"tsdeparts":[],"departname":null,"orgCode":null,"mobile":null,"fax":null,"departOrder":null,"id":null},"userKey":"库管员","id":"402884435cb4a6b5015cb4c148800038"}
         */

        private UserBean user;
        private List<DepotBean> depotlist;

        public UserBean getUser() {
            return user;
        }

        public List<DepotBean> getDepotlist() {
            return depotlist;
        }

        /*public static class UserBean {
            *//**
             * officePhone :
             * mobilePhone :
             * realName : 测试员3
             * signature : null
             * userName : test3
             * status : 1
             * deviceId : null
             * browser : null
             * deleteFlag : 0
             * departId : null
             * currentDepart : {"address":null,"description":null,"orgType":null,"tspdepart":null,"tsdeparts":[],"departname":null,"orgCode":null,"mobile":null,"fax":null,"departOrder":null,"id":null}
             * userKey : 库管员
             * id : 402884435cb4a6b5015cb4c148800038
             *//*


            private String mobilePhone;
            private String realName;
            private String userName;
            private int status;
            private String deviceId;
            private String userKey;
            private String id;


            public String getMobilePhone() {
                return mobilePhone;
            }

            public void setMobilePhone(String mobilePhone) {
                this.mobilePhone = mobilePhone;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }


            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getDeviceId() {
                return deviceId;
            }


            public String getUserKey() {
                return userKey;
            }

            public void setUserKey(String userKey) {
                this.userKey = userKey;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }
        }*/
    }


   /* public static class DepotBean {
        *//**
         * id : 402884435cc8ee8c015cc8f4538f0006
         * description : 1场区1号库房
         * createBy : admin
         * createName : null
         * createDate : 1498020074383
         * updateName : 管理员
         * updateBy : admin
         * updateDate : 1498188711100
         * depotName : 1号库房
         * depotNo : A01
         * areaId : 402885f45ccf0eb1015ccf0eb1800000
         *//*

        private String id;
        private String description;
        private String depotName;
        private String depotNo;
        private String areaId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDepotName() {
            return depotName;
        }

        public void setDepotName(String depotName) {
            this.depotName = depotName;
        }

        public String getDepotNo() {
            return depotNo;
        }

        public void setDepotNo(String depotNo) {
            this.depotNo = depotNo;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }
    }*/
}

