package com.example.barscanv01.Bean;

/**
 * Created by zhupipi on 2017/6/30.
 */

public class OutOrderBean {

        /**
         * id : 402884435cf69ef6015cf69ff37a0000
         * status : 0
         * grossWeightDate : null
         * createBy : admin
         * createName : 管理员
         * createDate : 1498786296707
         * updateName : 管理员
         * updateBy : admin
         * updateDate : 1498786299153
         * plateNo : 津A00000
         * outOrderNo : LORDERNO2
         * process : 1
         * tare : null
         * tareDate : null
         * grossWeight : null
         * saleOrderNo : SEALNO2
         */

        private String id;
        private String status;
        private String plateNo;
        private String outOrderNo;
        private String process;
        private String saleOrderNo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getOutOrderNo() {
            return outOrderNo;
        }

        public void setOutOrderNo(String outOrderNo) {
            this.outOrderNo = outOrderNo;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getSaleOrderNo() {
            return saleOrderNo;
        }

        public void setSaleOrderNo(String saleOrderNo) {
            this.saleOrderNo = saleOrderNo;
        }
}

