package me.jessyan.armscomponent.commonsdk.base.bean;

/**
 * @Author :hexingbo
 * @Date :2019/8/4
 * @FileName： MyHttpResult
 * @Describe :
 */
public class MyHttpResult {
    /**
     * code : 0
     *
     * info : ok
     * data : {"access_token":"c21eeddc-98fa-4efb-b045-21c3aa9bdbaf"}
     */


    /**
     * code
     * * 1001：token为空
     * * 1002：token无效
     * * 1003：token过期
     * * 1004：token中的用户无效
     * * 1005：签名为空
     * * 1006：时间戳为空
     */
    private int code;
    private int total;
    private String info;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getErrcode() {
        return code;
    }

    public void setErrcode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return info;
    }

    public void setMsg(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", total='" + total + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
