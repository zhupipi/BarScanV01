package com.example.barscanv01.Bean;

/**
 * Created by zhupipi on 2017/6/25.
 */

public class UserBean {
    private String mobilePhone;
    private String realName;
    private String userName;
    private int status;
    private String deviceName;
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

    public String getDeviceName() {
        return deviceName;
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

    public void setDeviceId(String deviceName) {
        this.deviceName = deviceName;
    }
}

