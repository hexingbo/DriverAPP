package com.lesso.module.login.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/14
 * @FileName： SendSmsCodeBean
 * @Describe :
 */
public class SendSmsCodeBean {

    private String mobile;
    private int msgType;

    public SendSmsCodeBean(String mobile, int msgType) {
        this.mobile = mobile;
        this.msgType = msgType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
}
