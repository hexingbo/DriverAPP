package me.jessyan.armscomponent.commonsdk.base.bean;

/**
 * @Author :hexingbo
 * @Date :2019/8/4
 * @FileName： HttpResult
 * @Describe :
 */
public class HttpResult<T> extends MyHttpResult {

    /**
     * errcode : 0
     * msg : ok
     * data : {"access_token":"c21eeddc-98fa-4efb-b045-21c3aa9bdbaf"}
     */
    private T rows;

    public T getData() {
        return rows;
    }

    public void setData(T data) {
        this.rows = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + getErrcode() +
                ", msg='" + getMsg() + '\'' +
                "data=" + rows +
                '}';
    }
}