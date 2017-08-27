package com.example.barscanv01.Bean;

/**
 * Created by zhulin on 2017/8/25.
 */

public class CustomerBean {
    private String customerName;
    private String customerCode;
    private String address;

    public CustomerBean(String customerCode, String customerName, String address) {
        this.address = address;
        this.customerName = customerName;
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
