package com.lesso.module.message.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.message.BuildConfig;
import com.lesso.module.message.mvp.contract.MessageDetailContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 12:25
 * Description:
 * ================================================
 */
@ActivityScope
public class MessageDetailPresenter extends BasePresenter<MessageDetailContract.Model, MessageDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MessageDetailPresenter(MessageDetailContract.Model model, MessageDetailContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    /**
     * 标记已读
     *
     * @param userMsgId
     */
    public void postUpdateMessageRead(String userMsgId) {
        if (!ArmsUtils.isEmpty(userMsgId))
            mModel.postUpdateMessageRead(userMsgId)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(
                            //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                            BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
                    .doOnSubscribe(disposable -> {
                        mRootView.showLoading();//显示下拉刷新的进度条
                    }).subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    })
                    //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new MyHttpResultObserver<HttpResult>(mErrorHandler) {
                        @Override
                        public void onResult(HttpResult result) {
                            EventBusManager.getInstance().post(new MessageEvent(EventBusHub.Message_MessageManagerList_UpdateData));
                        }
                    });
    }

}
