package com.lesso.module.login.mvp.model.entity;


/**
 * @Author :hexingbo
 * @Date :2019/11/14
 * @FileName： SubmitLoginBean
 * @Describe :
 */
public class SubmitLoginBean {

    private String account;//手机号
    private String code;//验证码
    private String deviceNo;//设备号
    private String password;// 密码
    private int loginType;//登录类型 账号密码登录：1 账号验证码登录：2
    private String loginDevice = "App";//登录设备
    private String appType = "driver";//应用类型
    private String platform = "Android";//登录平台

    public SubmitLoginBean(String account, String code, String password, String deviceNo, int loginType) {
        this.account = account;
        this.code = code;
        this.deviceNo = deviceNo;
        this.password = password;
        this.loginType = loginType;
    }
}
