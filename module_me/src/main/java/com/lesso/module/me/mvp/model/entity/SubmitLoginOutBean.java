package com.lesso.module.me.mvp.model.entity;

import android.support.annotation.NonNull;

/**
 * @Author :hexingbo
 * @Date :2019/11/15
 * @FileName： SubmitLoginOutBean
 * @Describe :
 */
public class SubmitLoginOutBean {

    private String account;//手机号
    private String deviceNo;//设备号
    private String appType = "driver";//应用类型 goods：货主端driver：司机端,cars：车主端
    private String loginDevice = "App";//登录设备 App：手机,Web：网站,PC：电脑
    private String platform = "Android";//登录平台 LessoManager：联塑后台；WebManager：平台后台，IOS, Android, Web：平台,

    public SubmitLoginOutBean(@NonNull String account,@NonNull String deviceNo) {
        this.account = account;
        this.deviceNo = deviceNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
