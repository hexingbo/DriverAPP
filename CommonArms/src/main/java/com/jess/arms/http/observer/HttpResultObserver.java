package com.jess.arms.http.observer;

import com.jess.arms.base.MyHttpResult;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author :hexingbo
 * @Date :2019/11/21
 * @FileName： HttpResultObserver
 * @Describe :
 */
public  class HttpResultObserver<T extends MyHttpResult> extends ErrorHandleSubscriber<T> {


    public HttpResultObserver(RxErrorHandler rxErrorHandler) {
        super(rxErrorHandler);
    }

    @Override
    public void onNext(T t) {

    }
}
