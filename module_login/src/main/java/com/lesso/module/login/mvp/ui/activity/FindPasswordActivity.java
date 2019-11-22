package com.lesso.module.login.mvp.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.login.R;
import com.lesso.module.login.R2;
import com.lesso.module.login.di.component.DaggerFindPasswordComponent;
import com.lesso.module.login.mvp.contract.FindPasswordContract;
import com.lesso.module.login.mvp.presenter.FindPasswordPresenter;

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
@Route(path = RouterHub.Loging_FindPasswordActivity)
public class FindPasswordActivity extends BaseActivity<FindPasswordPresenter> implements FindPasswordContract.View, CountDownTimerUtils.CountDownTimerRun {

    @Inject
    Dialog mDialog;

    @BindView(R2.id.et_user_phone)
    ClearEditText etUserPhone;
    @BindView(R2.id.et_userPwd)
    ClearEditText etUserPwd;
    @BindView(R2.id.et_checkNumebr)
    ClearEditText etCheckNumebr;
    @BindView(R2.id.tv_send_number)
    TextView tvSendNumber;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFindPasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_find_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        setTitle(R.string.login_lab_forget_pwd);
        btnSubmit.setText(R.string.login_lab_forget_pwd);
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
    public Context getActivity() {
        return this;
    }

    @Override
    public CountDownTimerUtils.CountDownTimerRun getCountDownTimerRun() {
        return this;
    }

    @OnClick(R2.id.btn_submit)
    public void onClickSubmitView() {
        //确认登录
        mPresenter.postRegisterUserApp(etUserPhone.getText().toString().trim(),
                etCheckNumebr.getText().toString().trim(), etUserPwd.getText().toString().trim());
    }

    @OnClick(R2.id.tv_send_number)
    public void onClickSendSmsCodeView() {
        // 发送成功进入倒计时
        mPresenter.submitSendSmsValue(etUserPhone.getText().toString().trim(), tvSendNumber);
    }

    @OnClick(R2.id.tv_go_login)
    public void onClickLoginView() {
        finish();
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
