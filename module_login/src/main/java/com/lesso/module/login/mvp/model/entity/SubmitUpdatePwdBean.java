package com.lesso.module.login.mvp.model.entity;


import android.support.annotation.Nullable;

/**
 * @Author :hexingbo
 * @Date :2019/11/14
 * @FileName： SubmitLoginBean
 * @Describe :
 */
public class SubmitUpdatePwdBean {

    private String oldPassword;//旧密码
    private String newPassword;//新密码
    private String driverId;// 用户id

    /**
     * 修改密码提交参数
     *
     * @param oldPassword
     * @param newPassword
     * @param driverId
     */
    public SubmitUpdatePwdBean(@Nullable String oldPassword, @Nullable String newPassword, @Nullable String driverId) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.driverId = driverId;
    }
}
