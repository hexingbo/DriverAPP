package com.lesso.module.login.mvp.presenter;

import android.app.Application;
import android.widget.TextView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.login.BuildConfig;
import com.lesso.module.login.R;
import com.lesso.module.login.mvp.contract.FindPasswordContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.enums.SmsCodeType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.utils.CountDownTimerUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 19:01
 * Description:
 * ================================================
 */
@ActivityScope
public class FindPasswordPresenter extends BasePresenter<FindPasswordContract.Model, FindPasswordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private CountDownTimerUtils countDownTimer;

    @Inject
    public FindPasswordPresenter(FindPasswordContract.Model model, FindPasswordContract.View rootView) {
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
     * 用户注册
     *
     * @param account
     * @param sms
     * @param password
     */
    public void postRegisterUserApp(String account, String sms, String password) {
        if (ArmsUtils.isEmpty(account)) {
            ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_phone_number));
            return;
        }
        if (ArmsUtils.isEmpty(sms)) {
            ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_sms_number));
            return;
        }
        if (sms.length() != 4) {
            ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_sms_number_length));
            return;
        }
        if (ArmsUtils.isEmpty(password)) {
            ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_pwd));
            return;
        }
        mModel.postFindPasswordApp(account, sms, password)
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
                        ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.tv_find_pwd_to_login));
                        AppManagerUtil.getCurrentActivity().finish();
                    }
                });
    }

    /**
     * 获取短信验证码检测提交数据
     *
     * @param userPhone
     * @param tvSendNumber
     * @return
     */
    public boolean submitSendSmsValue(String userPhone, TextView tvSendNumber) {
        if (ArmsUtils.isEmpty(userPhone)) {
            ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_phone_number));
            return false;
        }
        if (userPhone.length() != 11) {
            ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_phone_number_length));
            return false;
        }
//        if (!RegexUtils.isMobileSimple(userPhone)) {
//            RingToast.show("请输入正确的手机号");
//            return false;
//        }
        if (countDownTimer == null && tvSendNumber != null) {
            countDownTimer = new CountDownTimerUtils(tvSendNumber, 60000, 1000);
            CountDownTimerUtils.CountDownTimerRun countDownTimerRun = mRootView.getCountDownTimerRun();
            if (!ArmsUtils.isEmpty(countDownTimerRun))
                countDownTimer.setTimerRun(countDownTimerRun);
        }
        countDownTimer.start();
        sendSMSCode(userPhone);
        return true;
    }

    /**
     * 获取短信验证码
     *
     * @param userPhone
     */
    private void sendSMSCode(String userPhone) {
        if (ArmsUtils.isEmpty(userPhone)) {
            ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_phone_number));
            return;
        }
        mModel.sendSMSCode(userPhone, SmsCodeType.SmsCodeType_FindLoginPwd)
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
