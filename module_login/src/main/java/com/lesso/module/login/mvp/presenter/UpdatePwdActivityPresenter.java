package com.lesso.module.login.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.login.BuildConfig;
import com.lesso.module.login.R;
import com.lesso.module.login.mvp.contract.UpdatePwdActivityContract;
import com.lesso.module.login.mvp.model.entity.SubmitUpdatePwdBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.http.observer.MyHttpResultObserver;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:47
 * Description:
 * ================================================
 */
@ActivityScope
public class UpdatePwdActivityPresenter extends BasePresenter<UpdatePwdActivityContract.Model, UpdatePwdActivityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UpdatePwdActivityPresenter(UpdatePwdActivityContract.Model model, UpdatePwdActivityContract.View rootView) {
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
     * 修改密码提交参数
     *  @param pwdOld
     * @param pwdNew
     * @param pwdConfirm
     */
    public void postUpdatePassword(String pwdOld, String pwdNew, String pwdConfirm) {
        if (ArmsUtils.isEmpty(pwdOld)) {
            ArmsUtils.makeText(mApplication, mRootView.getActivity().getResources().getString(R.string.login_hint_input_pwd_old));
            return;
        }
        if (ArmsUtils.isEmpty(pwdNew)) {
            ArmsUtils.makeText(mApplication, mRootView.getActivity().getResources().getString(R.string.login_hint_input_pwd_new));
            return;
        }

        if(!TextUtils.equals(pwdNew, pwdConfirm)) {
            ArmsUtils.makeText(mApplication, mApplication.getResources().getString(R.string.password_not_equals));
            return;
        }

        mModel.postUpdatePassword(new SubmitUpdatePwdBean(pwdOld, pwdNew, DataHelper.getStringSF(mApplication, Constants.SP_USER_ID)))
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
                        mRootView.showMessage(mApplication.getString(R.string.module_login_name_update_success));
                        ARouter.getInstance().build(RouterHub.Loging_MainLoginActivity)
                                .withBoolean("isFirst", false).navigation(AppManagerUtil.getCurrentActivity());
                        AppManagerUtil.getCurrentActivity().finish();
                    }
                });
    }
}
