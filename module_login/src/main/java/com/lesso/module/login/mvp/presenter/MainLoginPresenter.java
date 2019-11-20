package com.lesso.module.login.mvp.presenter;

import android.app.Application;
import android.widget.TextView;

import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lesso.module.login.BuildConfig;
import com.lesso.module.login.R;
import com.lesso.module.login.mvp.contract.MainLoginContract;
import com.lesso.module.login.mvp.model.entity.LoginResultBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.armscomponent.commonres.enums.LoginType;
import me.jessyan.armscomponent.commonres.enums.SmsCodeType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.observer.MyHttpResultObserver;
import me.jessyan.armscomponent.commonsdk.core.EventBusHub;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.CountDownTimerUtils;
import me.jessyan.armscomponent.commonsdk.utils.SaveOrClearUserInfo;
import me.jessyan.armscomponent.commonsdk.utils.Utils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/13/2019 20:36
 * ================================================
 */
@ActivityScope
public class MainLoginPresenter extends BasePresenter<MainLoginContract.Model, MainLoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private CountDownTimerUtils countDownTimer;
    private boolean isFirst;

    public void setFirst(boolean first) {
        isFirst = first;
        LogUtils.debugInfo("hxb---->", "isFirst：" + isFirst);
    }

    @Inject
    public MainLoginPresenter(MainLoginContract.Model model, MainLoginContract.View rootView) {
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


    private LoginType loginType = LoginType.LoginType_Pwd;

    /**
     * 设置登录方式+并改变界面view（密码/手机号）
     *
     * @param type
     */
    public void setLoginType(LoginType type) {
        loginType = type;
        mRootView.changeLoginTypeView(loginType);
    }

    /**
     * 获取当前的登录方式（密码/手机号）
     *
     * @return
     */
    public LoginType getLoginType() {
        return loginType;
    }

    /**
     * 用户登录权限检测
     *
     * @param phone
     * @param pwd
     * @param sms
     */
    public void submitLoginInfoValue(String phone, String pwd, String sms) {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                postLoginUserApp(phone, pwd, sms, DeviceUtils.getDeviceId(mRootView.getActivity()));
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("Request permissions failure");
                postLoginUserApp(phone, pwd, sms, "");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("Need to go to the settings");
                postLoginUserApp(phone, pwd, sms, "");
            }
        }, mRootView.getRxPermissions(), mErrorHandler);

    }

    /**
     * 用户登录
     *
     * @param phone
     * @param pwd
     * @param sms
     * @param deviceId
     */
    private void postLoginUserApp(String phone, String pwd, String sms, String deviceId) {
        if (ArmsUtils.isEmpty(phone)) {
            ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_phone_number));
            return;
        }
        switch (loginType) {
            case LoginType_SmsCode:
                if (ArmsUtils.isEmpty(sms)) {
                    ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_sms_number));
                    return;
                }
                if (sms.length() != 4) {
                    ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_sms_number_length));
                    return;
                }
                break;
            case LoginType_Pwd:
                if (ArmsUtils.isEmpty(pwd)) {
                    ArmsUtils.snackbarText(mRootView.getActivity().getResources().getString(R.string.login_hint_input_pwd));
                    return;
                }
                break;
        }

        mModel.postLoginUserApp(phone, pwd, sms, loginType)
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
                .subscribe(new MyHttpResultObserver<HttpResult<LoginResultBean>>(mErrorHandler) {

                    @Override
                    public void onResult(HttpResult<LoginResultBean> result) {
                        LogUtils.debugInfo("hxb---->","isFirst："+isFirst);
                        //登录成功
                        SaveOrClearUserInfo.saveUserInfo(result.getData().getToken(), result.getData().getUserId(), result.getData().getVerifyStatus(), phone, deviceId);
                        if (isFirst) {
                            Utils.navigation(mRootView.getActivity(), RouterHub.APP_MainStartActivity);
                        } else
                            EventBusManager.getInstance().post(new MessageEvent(EventBusHub.TAG_LOGIN_SUCCESS));
                        mRootView.getActivity().finish();
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
        mModel.sendSMSCode(userPhone, SmsCodeType.SmsCodeType_Login)
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

    /**
     * 注册新用户
     */
    public void goMainRegisterUserActivity() {
        mModel.goMainRegisterUserActivity();
    }

    /**
     * 找回密码
     */
    public void goMainForgetPasswordActivity() {
        mModel.goMainForgetPasswordActivity();
    }


}
