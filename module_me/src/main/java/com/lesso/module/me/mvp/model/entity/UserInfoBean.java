package com.lesso.module.me.mvp.model.entity;

import java.io.Serializable;

/**
 * @Author :hexingbo
 * @Date :2019/3/17
 * @FileName： UserInfoBean
 * @Describe :
 */
public class UserInfoBean implements Serializable {

    private String headUrl;//用户头像地址
    private String driverName;//司机姓名
    private String departNum;//发车数量
    private String driverId;//司机ID
    private String qrCode;//二维码

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDepartNum() {
        return departNum;
    }

    public void setDepartNum(String departNum) {
        this.departNum = departNum;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "headUrl='" + headUrl + '\'' +
                ", driverName='" + driverName + '\'' +
                ", departNum='" + departNum + '\'' +
                ", driverId='" + driverId + '\'' +
                ", qrCode='" + qrCode + '\'' +
                '}';
    }
}
