package com.lesso.module.message.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： MessageBean
 * @Describe :
 */
public class MessageBean {

    private String messageType;//消息类型：0=系统消息 1=订单消息 2=物流消息 3=租车消息 4=审核消息
    private String title;//标题
    private String content;//内容
    private String linkUrl;//详情地址
    private int haveRead;//是否已经(0未读 1已读)
    private String userMsgId;//用户消息ID

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(int haveRead) {
        this.haveRead = haveRead;
    }

    public String getUserMsgId() {
        return userMsgId;
    }

    public void setUserMsgId(String userMsgId) {
        this.userMsgId = userMsgId;
    }
}
