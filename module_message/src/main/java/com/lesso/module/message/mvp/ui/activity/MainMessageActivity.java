package com.lesso.module.message.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lesso.module.message.di.component.DaggerMainMessageComponent;
import com.lesso.module.message.mvp.contract.MainMessageContract;
import com.lesso.module.message.mvp.presenter.MainMessagePresenter;

import com.lesso.module.message.R;
import com.lesso.module.message.mvp.ui.fragment.MessageManagerFragment;


import me.jessyan.armscomponent.commonres.utils.MyFragmentUtils;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/13
 * 描    述：MainMessageActivity
 * =============================================
 */
@Route(path = RouterHub.Message_MainMessageActivity)
public class MainMessageActivity extends BaseActivity<MainMessagePresenter> implements MainMessageContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main_message; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        setTitle(R.string.public_message_center);
        MyFragmentUtils.addFragment(getSupportFragmentManager(), MessageManagerFragment.newInstance(), R.id.fl_content);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
