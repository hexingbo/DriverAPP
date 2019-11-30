package com.lesso.module.me.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lesso.module.me.di.component.DaggerChoiceJoinCompanyCarsComponent;
import com.lesso.module.me.mvp.contract.ChoiceJoinCompanyCarsContract;
import com.lesso.module.me.mvp.presenter.ChoiceJoinCompanyCarsPresenter;

import com.lesso.module.me.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:42
 * Description:选择加盟车辆
 * ================================================
 */
public class ChoiceJoinCompanyCarsActivity extends BaseActivity<ChoiceJoinCompanyCarsPresenter> implements ChoiceJoinCompanyCarsContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChoiceJoinCompanyCarsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choice_join_company_cars; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
}
