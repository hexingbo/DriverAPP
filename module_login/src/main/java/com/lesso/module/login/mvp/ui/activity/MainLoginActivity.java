package com.lesso.module.login.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.lesso.module.login.R;
import com.lesso.module.login.R2;
import com.lesso.module.login.di.component.DaggerMainLoginComponent;
import com.lesso.module.login.mvp.contract.MainLoginContract;
import com.lesso.module.login.mvp.presenter.MainLoginPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.enums.LoginType;
import me.jessyan.armscomponent.commonres.other.ClearEditText;
import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.CountDownTimerUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/13/2019 20:36
 * ================================================
 */
@Route(path = RouterHub.Loging_MainLoginActivity)
public class MainLoginActivity extends BaseActivity<MainLoginPresenter> implements MainLoginContract.View, CountDownTimerUtils.CountDownTimerRun {

    @Inject
    Dialog mDialog;
    @Inject
    RxPermissions mRxPermissions;

    @BindView(R2.id.et_user_phone)
    ClearEditText etUserPhone;
    @BindView(R2.id.tv_send_number)
    TextView tvSendNumber;
    @BindView(R2.id.et_userPwd)
    ClearEditText etUserPwd;
    @BindView(R2.id.lv_input_pwd)
    LinearLayout lvInputPwd;
    @BindView(R2.id.et_checkNumebr)
    ClearEditText etCheckNumebr;
    @BindView(R2.id.lv_input_checkNumber)
    LinearLayout lvInputCheckNumber;

    @BindView(R2.id.lv_login_type_wx)
    LinearLayout lvLoginTypeWx;
    @BindView(R2.id.lv_login_type_pwd)
    LinearLayout lvLoginTypePwd;
    @BindView(R2.id.lv_login_type_sms)
    LinearLayout lvLoginTypeSms;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        boolean isFirst = getIntent().getBooleanExtra("isFirst", true);
        mPresenter.setFirst(isFirst);
        btnSubmit.setText(R.string.login_lab_login);
        etUserPhone.setText(DataHelper.getStringSF(this, Constants.SP_LOGIN_USER));
        changeLoginTypeView(mPresenter.getLoginType());
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        mDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void changeLoginTypeView(LoginType loginType) {
        lvLoginTypeSms.setVisibility(View.GONE);
        lvLoginTypePwd.setVisibility(View.GONE);
        lvInputPwd.setVisibility(View.GONE);
        lvInputCheckNumber.setVisibility(View.GONE);
        tvSendNumber.setVisibility(View.GONE);
        switch (loginType) {
            case LoginType_Pwd:
                lvLoginTypeSms.setVisibility(View.VISIBLE);
                lvInputPwd.setVisibility(View.VISIBLE);
                break;
            case LoginType_SmsCode:

                lvLoginTypePwd.setVisibility(View.VISIBLE);
                lvInputCheckNumber.setVisibility(View.VISIBLE);
                tvSendNumber.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public CountDownTimerUtils.CountDownTimerRun getCountDownTimerRun() {
        return this;
    }


    @OnClick(R2.id.btn_submit)
    public void onClickSubmitLoginView() {
        // 确认登录
        mPresenter.submitLoginInfoValue(etUserPhone.getText().toString().trim(),
                etUserPwd.getText().toString().trim(), etCheckNumebr.getText().toString().trim());
    }

    @OnClick(R2.id.tv_send_number)
    public void onClickSendSmsCodeView() {
        // 发送短信验证码
        // 发送成功进入倒计时
        mPresenter.submitSendSmsValue(etUserPhone.getText().toString().trim(), tvSendNumber);
    }

    @OnClick(R2.id.tv_forget_pwd)
    public void onClickForgetPwdView() {
        //  忘记密码
        mPresenter.goMainForgetPasswordActivity();
    }

    @OnClick(R2.id.tv_phone_register)
    public void onClickRegisterUserView() {
        //用户注册
        mPresenter.goMainRegisterUserActivity();
    }

    @OnClick(R2.id.lv_login_type_wx)
    public void onClickLoginTypeWxView() {
        // 微信登录方式

    }

    @OnClick(R2.id.lv_login_type_pwd)
    public void onClickLoginTypePwdView() {
        //密码登录方式
        mPresenter.setLoginType(LoginType.LoginType_Pwd);
    }

    @OnClick(R2.id.lv_login_type_sms)
    public void onClickLoginTypeSmsCodeView() {
        // 短信验证登录方式
        mPresenter.setLoginType(LoginType.LoginType_SmsCode);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        etUserPhone.setEnabled(false);
    }

    @Override
    public void onFinish() {
        etUserPhone.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        AppManager.getAppManager().appExit();
    }

    @Override
    protected void getEventBusHub_Activity(MessageEvent message) {

    }
}