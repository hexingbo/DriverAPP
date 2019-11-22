package com.lesso.module.me.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.di.component.DaggerMeMainComponent;
import com.lesso.module.me.mvp.contract.MeMainContract;
import com.lesso.module.me.mvp.presenter.MeMainPresenter;
import com.lesso.module.me.mvp.ui.fragment.MainMyFragment;

import me.jessyan.armscomponent.commonres.utils.MyFragmentUtils;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 10:04
 * 描    述：
 * =============================================
 */
@Route(path = RouterHub.Me_MeMainActivity)
public class MeMainActivity extends BaseActivity<MeMainPresenter> implements MeMainContract.View {


    private MainMyFragment mainMyFragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMeMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_me_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(me.jessyan.armscomponent.commonres.R.string.public_name_my);
        MyFragmentUtils.addFragment(getSupportFragmentManager(), MainMyFragment.newInstance(), R.id.fl_content);
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
