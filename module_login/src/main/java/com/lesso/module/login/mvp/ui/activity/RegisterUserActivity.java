package com.lesso.module.login.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.login.R;
import com.lesso.module.login.R2;
import com.lesso.module.login.di.component.DaggerRegisterUserComponent;
import com.lesso.module.login.mvp.contract.RegisterUserContract;
import com.lesso.module.login.mvp.presenter.RegisterUserPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.other.ClearEditText;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.CountDownTimerUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 19:01
 * Description:
 * ================================================
 */
@Route(path = RouterHub.Loging_RegisterUserActivity)
public class RegisterUserActivity extends BaseActivity<RegisterUserPresenter> implements RegisterUserContract.View, CountDownTimerUtils.CountDownTimerRun {


    @Inject
    Dialog mDialog;
    @Inject
    RxPermissions mRxPermissions;

    @BindView(R2.id.et_user_name)
    ClearEditText etUserName;
    @BindView(R2.id.et_userPwd)
    ClearEditText etUserPwd;
    @BindView(R2.id.et_user_phone)
    ClearEditText etUserPhone;
    @BindView(R2.id.et_checkNumebr)
    ClearEditText etCheckNumebr;
    @BindView(R2.id.tv_send_number)
    TextView tvSendNumber;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterUserComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register_user; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setTitle(R.string.login_lab_register_user);
        btnSubmit.setText(R.string.login_lab_register);
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
    public void onClickSubmitView() {
        // 确认登录
        mPresenter.postRegisterUserApp(etUserName.getText().toString().trim(), etUserPhone.getText().toString().trim(),
                etCheckNumebr.getText().toString().trim(), etUserPwd.getText().toString().trim());
    }

    @OnClick(R2.id.tv_send_number)
    public void onClickSendSmsCodeView() {
        // 发送成功进入倒计时
        mPresenter.submitSendSmsValue(etUserPhone.getText().toString().trim(), tvSendNumber);
    }


    @Override
    public void onTick(long millisUntilFinished) {
        if (etUserPhone != null)
            etUserPhone.setEnabled(false);
    }

    @Override
    public void onFinish() {
        if (etUserPhone != null)
            etUserPhone.setEnabled(true);
    }
}
