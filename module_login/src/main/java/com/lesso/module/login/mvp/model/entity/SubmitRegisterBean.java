package com.lesso.module.login.mvp.model.entity;


/**
 * @Author :hexingbo
 * @Date :2019/11/14
 * @FileName： SubmitLoginBean
 * @Describe :
 */
public class SubmitRegisterBean {

    private String account;//手机号
    private String code;//验证码
    private String name;// 姓名
    private String password;// 密码
    private String deviceNo;//设备号
    private String appType = "driver";//应用类型 goods：货主端driver：司机端,cars：车主端
    private String loginDevice = "App";//登录设备 App：手机,Web：网站,PC：电脑
    private String platform = "Android";//登录平台 LessoManager：联塑后台；WebManager：平台后台，IOS, Android, Web：平台,

    public SubmitRegisterBean(String name, String account, String code,
                              String password, String deviceNo) {
        this.name = name;
        this.account = account;
        this.code = code;
        this.deviceNo = deviceNo;
        this.password = password;
    }
}
