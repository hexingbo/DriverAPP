package com.lesso.module.me.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.mvp.contract.CompanyJoinDetailInfoContract;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:32
 * Description:物流公司详情
 * ================================================
 */
@ActivityScope
public class CompanyJoinDetailInfoPresenter extends BasePresenter<CompanyJoinDetailInfoContract.Model, CompanyJoinDetailInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private String companyId;

    @Inject
    public CompanyJoinDetailInfoPresenter(CompanyJoinDetailInfoContract.Model model, CompanyJoinDetailInfoContract.View rootView) {
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

    public void getCompanyDetail(String companyId) {
        if (ArmsUtils.isEmpty(companyId)) {
            mRootView.setCompanyJoinBean(null);
            mRootView.showMessage(AppManagerUtil.appContext().getResources().getString(me.jessyan.armscomponent.commonres.R.string.public_name_data_error));
            return;
        }
        this.companyId = companyId;
        mModel.getCompanyDetail(companyId)
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
                .subscribe(new MyHttpResultObserver<HttpResult<CompanyJoinBean>>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult<CompanyJoinBean> result) {
                        mRootView.setCompanyJoinBean(result.getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.setCompanyJoinBean(null);
                    }
                });
    }

    public void postCompanyJoin() {
        if (ArmsUtils.isEmpty(companyId)) {
            mRootView.showMessage(AppManagerUtil.appContext().getResources().getString(me.jessyan.armscomponent.commonres.R.string.public_name_data_error));
            return;
        }
        mModel.postCompanyJoin(companyId, DataHelper.getStringSF(AppManagerUtil.appContext(), Constants.SP_USER_ID), "1")//加盟类型 0：物流公司邀请；1：司机主动加入
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
                    }

                });
    }
}
