package me.jessyan.armscomponent.commonsdk.base.bean;

/**
 * @Author :hexingbo
 * @Date :2019/8/9
 * @FileName： MessageEvent
 * @Describe :
 */
public class MessageEvent<T> {
    private String message;
    private T value;

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(String message, T value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}


