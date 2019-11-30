package com.lesso.module.me.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.mvp.contract.MyCarsListManagerContract;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;
import com.lesso.module.me.mvp.model.entity.CarJoinReq;
import com.lesso.module.me.mvp.model.entity.QueryCarJoinReq;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:38
 * Description:
 * ================================================
 */
@ActivityScope
public class MyCarsListManagerPresenter extends BasePresenter<MyCarsListManagerContract.Model, MyCarsListManagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyCarsListManagerPresenter(MyCarsListManagerContract.Model model,
                                      MyCarsListManagerContract.View rootView) {
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

    public void getCarJoinList( int page, int pageSize) {
        mModel.getCarJoinList(new QueryCarJoinReq(DataHelper.getStringSF(mApplication, Constants.SP_USER_ID), page, pageSize))
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
              .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
              .subscribe(new MyHttpResultObserver<HttpResult<List<CarJoinBean>>>(mErrorHandler) {
                  @Override
                  public void onResult(HttpResult<List<CarJoinBean>> result) {
                      mRootView.onCarJoinList(result.getData(), page);
                  }

                  @Override
                  public void onError(Throwable t) {
                      super.onError(t);
                      mRootView.onCarJoinList(new ArrayList<>(), page);
                  }
              });
    }

    public void joinCar(CarJoinReq carJoinReq) {
        mModel.joinCar(carJoinReq)
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
              .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
              .subscribe(new MyHttpResultObserver<HttpResult>(mErrorHandler) {
                  @Override
                  public void onResult(HttpResult result) {
                      mRootView.onJoinCarSucceed();
                  }
              });
    }
}
