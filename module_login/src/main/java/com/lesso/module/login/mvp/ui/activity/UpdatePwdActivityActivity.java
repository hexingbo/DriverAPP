package com.lesso.module.login.mvp.ui.activity;

import android.app.Activity;
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
import com.lesso.module.login.di.component.DaggerUpdatePwdActivityComponent;
import com.lesso.module.login.mvp.contract.UpdatePwdActivityContract;
import com.lesso.module.login.mvp.presenter.UpdatePwdActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.other.ClearEditText;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:47
 * Description:
 * ================================================
 */
@Route(path = RouterHub.Loging_UpdatePwdActivityActivity)
public class UpdatePwdActivityActivity extends BaseActivity<UpdatePwdActivityPresenter> implements UpdatePwdActivityContract.View {

    @BindView(R2.id.et_pwd_old)
    ClearEditText etPwdOld;
    @BindView(R2.id.et_pwd_new)
    ClearEditText etPwdNew;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUpdatePwdActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_update_pwd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.module_login_name_update_pwd);
        btnSubmit.setText(getString(R.string.module_login_name_update));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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


    @OnClick(R2.id.btn_submit)
    public void onViewClicked() {
        mPresenter.postUpdatePassword(etPwdOld.getText().toString().trim(),etPwdNew.getText().toString().trim());
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
