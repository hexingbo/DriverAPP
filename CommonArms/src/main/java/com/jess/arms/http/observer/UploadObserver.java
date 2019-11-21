package com.jess.arms.http.observer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.http.body.ProgressListener;
import com.jess.arms.http.throwable.HttpThrowable;
import com.jess.arms.http.throwable.ThrowableHandler;
import com.jess.arms.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import com.jess.arms.base.MyHttpResult;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/21
 * 描    述：文件上传的请求回调（包含上传进度回调）
 * =============================================
 */
public abstract class UploadObserver<T extends MyHttpResult> implements Observer<T>, ProgressListener {

    private String mUploadUrl;
    private String mQualifier;

    /**
     * 如果不需要监听进度，则使用此构造函数，或者直接改用CommonObserver
     */
    public UploadObserver() {
    }

    /**
     * 如果是普通地监听某个上传的进度，则使用此构造函数
     * @param uploadUrl 上传的URL地址
     */
    public UploadObserver(@NonNull String uploadUrl) {
        this.mUploadUrl = uploadUrl;
    }

    /**
     * 如果是使用同一个URL但根据请求参数的不同而上传不同资源的情况，则使用此构造函数
     * @param uploadUrl 上传的URL地址
     * @param qualifier 用以区分的字符串
     */
    public UploadObserver(@NonNull String uploadUrl, @Nullable String qualifier) {
        this.mUploadUrl = uploadUrl;
        this.mQualifier = qualifier;
    }

    public String getUploadUrl() {
        return mUploadUrl;
    }

    public String getQualifier() {
        return mQualifier;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T httpResult) {
        LogUtils.debugInfo("hxb--->",httpResult.toString());
        onResult(httpResult);
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof Exception) {
            onError(0, ThrowableHandler.handleThrowable(throwable));
        } else {
            onError(0, new HttpThrowable(HttpThrowable.UNKNOWN, "未知错误", throwable));
        }
    }

    @Override
    public void onProgressError(long id, Exception e) {
        onError(id, ThrowableHandler.handleThrowable(e));
    }

    public abstract void onResult(T result);
    //如果progressInfoId为0，则为请求相关的异常，如果不为0，则为上传读写过程的异常
    public abstract void onError(long progressInfoId, HttpThrowable httpThrowable);


}
