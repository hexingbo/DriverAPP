package com.lesso.module.me.mvp.presenter;

import android.app.Application;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.me.BuildConfig;
import com.lesso.module.me.R;
import com.lesso.module.me.mvp.contract.MainMyContract;
import com.lesso.module.me.mvp.model.entity.UserInfoBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.bean.ImageLookBean;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import me.jessyan.armscomponent.commonsdk.utils.ImageViewLookImgsUtils;
import me.jessyan.armscomponent.commonsdk.utils.SaveOrClearUserInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 11:24
 * 描    述：
 * =============================================
 */
@FragmentScope
public class MainMyPresenter extends BasePresenter<MainMyContract.Model, MainMyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainMyPresenter(MainMyContract.Model model, MainMyContract.View rootView) {
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
     * 退出登录
     */
    public void getUserInfo() {
        mModel.getUserInfo(DataHelper.getStringSF(mRootView.getActivity(), Constants.SP_USER_ID))
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
                .subscribe(new MyHttpResultObserver<HttpResult<UserInfoBean>>(mErrorHandler) {
                    @Override
                    public void onResult(HttpResult<UserInfoBean> result) {
                        if (!ArmsUtils.isEmpty(result.getData())) {
                            mRootView.setUserInfo(result.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    /**
     * 退出登录
     */
    public void postLoginOut() {
        mModel.postLoginOut(DataHelper.getStringSF(mApplication, Constants.SP_ACCOUNT),
                DataHelper.getStringSF(mApplication, Constants.SP_DEVICE_ID))
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
                        SaveOrClearUserInfo.clearUserInfo();
                        ARouter.getInstance().build(RouterHub.Loging_MainLoginActivity).navigation(mApplication);
                    }
                });
    }

    public void setUserHeadImager(ImageView view, String url) {
        userHead = url;
        LogUtils.debugInfo("hxb--->", url);
        if (!ArmsUtils.isEmpty(url))
            mImageLoader.loadImage(mApplication, CommonImageConfigImpl
                    .builder()
                    .url(url)
                    .errorPic(R.mipmap.ic_head_default)
                    .placeholder(R.mipmap.ic_head_default)
                    .transformation(new CircleCrop())
                    .imageView(view)
                    .build());
    }

    private String userHead;

    public void openExternalPreview() {
        ImageViewLookImgsUtils.init().lookImgs(AppManagerUtil.getCurrentActivity(), userHead);
    }
}
