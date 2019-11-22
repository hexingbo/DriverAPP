package com.lesso.module.login.mvp.model.entity;


import android.support.annotation.Nullable;

/**
 * @Author :hexingbo
 * @Date :2019/11/14
 * @FileName： SubmitLoginBean
 * @Describe :
 */
public class SubmitFindPwdBean {

    private String account;//手机号
    private String code;//验证码
    private String password;// 密码

    public SubmitFindPwdBean(@Nullable String account,@Nullable String code,@Nullable String password) {
        this.account = account;
        this.code = code;
        this.password = password;
    }
}
