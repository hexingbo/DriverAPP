package me.jessyan.armscomponent.commonsdk.base.observer;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.utils.AppManagerUtil;

import me.jessyan.armscomponent.commonsdk.base.bean.MyHttpResult;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.SaveOrClearUserInfo;
import me.jessyan.armscomponent.commonsdk.utils.Utils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * @Author :hexingbo
 * @Date :2019/10/6
 * @FileName： MyHttpResultObserver
 * @Describe :
 */
public abstract class MyHttpResultObserver<T extends MyHttpResult> extends ErrorHandleSubscriber<T> {

    public MyHttpResultObserver(RxErrorHandler rxErrorHandler) {
        super(rxErrorHandler);
    }

    @Override
    public void onNext(T httpResult) {
        switch (HttpResultEnum.getCodeType(httpResult.getErrcode())) {
            case Success_Code://请求成功
                onResult(httpResult);
                break;
            case Login_Error_Code://登录失效
                onError(new Throwable("登录失效，请重新登录"));
                SaveOrClearUserInfo.clearUserInfo();
                ARouter.getInstance().build(RouterHub.Loging_MainLoginActivity)
                        .withBoolean("isFirst",false).navigation(AppManagerUtil.getCurrentActivity());
                break;
            case Other_Code://其他
                onError(new Throwable(httpResult.getMsg()));
                break;
        }
    }

    public abstract void onResult(T result);

    /**
     * code
     * * 1001：token为空
     * * 1002：token无效
     * * 1003：token过期
     * * 1004：token中的用户无效
     * * 1005：签名为空
     * * 1006：时间戳为空
     */
    public enum HttpResultEnum {
        Success_Code, Login_Error_Code, Other_Code;

        public static HttpResultEnum getCodeType(int code) {
            HttpResultEnum resultEnum;
            switch (code) {
                case 200:
                    resultEnum = Success_Code;
                    break;
                case 1001:
                case 1002:
                case 1003:
                case 1004:
                case 1005:
                case 1006:
                    resultEnum = Login_Error_Code;
                    break;
                default:
                    resultEnum = Other_Code;
                    break;
            }
            return resultEnum;
        }
    }
}
