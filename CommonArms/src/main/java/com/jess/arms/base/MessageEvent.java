package com.jess.arms.base;

import java.io.Serializable;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/18
 * 描    述：EventBus通信的事件类
 * =============================================
 */
public class MessageEvent<T> implements Serializable {
    private String type;
    private T content;

    public MessageEvent(String type) {
        this.type = type;
    }

    public MessageEvent(String type, T content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

