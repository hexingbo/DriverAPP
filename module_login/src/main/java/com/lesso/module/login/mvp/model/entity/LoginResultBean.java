package com.lesso.module.login.mvp.model.entity;

import java.io.Serializable;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/10/28
 * 描    述：LoginResultBean
 * =============================================
 */
public class LoginResultBean implements Serializable {

    private String token;
    private String userId;
    private String verifyStatus;//空（未认证）D（认证通过）F（认证不通过）

    @Override
    public String toString() {
        return "LoginResultBean{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", verifyStatus='" + verifyStatus + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
}
