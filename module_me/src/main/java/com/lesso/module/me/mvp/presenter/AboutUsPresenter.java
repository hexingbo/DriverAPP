package com.lesso.module.me.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.mvp.contract.AboutUsContract;
import com.lesso.module.me.mvp.model.entity.UpdateDetailBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:
 * ================================================
 */
@ActivityScope
public class AboutUsPresenter extends BasePresenter<AboutUsContract.Model, AboutUsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AboutUsPresenter(AboutUsContract.Model model, AboutUsContract.View rootView) {
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

    public void checkVersionDetail(int currentVersionCode) {
        mModel.checkVersionDetail("D")
              .subscribeOn(Schedulers.io())
              .retryWhen(new RetryWithDelay(
                      //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                      BuildConfig.HTTP_MaxRetries, BuildConfig.HTTP_RetryDelaySecond))
              .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
              .doOnSubscribe(disposable -> mRootView.showLoading())
              .subscribeOn(AndroidSchedulers.mainThread())
              .observeOn(AndroidSchedulers.mainThread())
              .doFinally(() -> mRootView.hideLoading())
              .subscribe(new MyHttpResultObserver<HttpResult<UpdateDetailBean>>(mErrorHandler) {
                  @Override
                  public void onResult(HttpResult<UpdateDetailBean> result) {
                      if(!ArmsUtils.isEmpty(result.getData())
                              && result.getData().getAndroid() != null) {
                          UpdateDetailBean.AndroidBean android = result.getData().getAndroid();
                          boolean isNewVersion = android.getVersionCode() > currentVersionCode;
                          mRootView.onCheckVersion(isNewVersion, android);
                      }
                  }
              });
    }
}
