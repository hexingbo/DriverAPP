package com.lesso.module.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.lesso.module.BuildConfig;
import com.lesso.module.R;
import com.lesso.module.di.component.DaggerWelcomeComponent;
import com.lesso.module.mvp.contract.WelcomeContract;
import com.lesso.module.mvp.presenter.WelcomePresenter;

import me.jessyan.armscomponent.commonsdk.core.Constants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.Utils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 20:51
 * Description:
 * ================================================
 */
public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWelcomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_welcome; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BuildConfig.IS_BUILD_MODULE) {
                    Utils.navigation(AppManagerUtil.appContext(), RouterHub.APP_MainStartActivity);
                    finish();
                } else {
                    if (ArmsUtils.isEmpty(DataHelper.getStringSF(AppManagerUtil.appContext(), Constants.SP_TOKEN))){
                        ARouter.getInstance().build(RouterHub.Loging_MainLoginActivity) .navigation(AppManagerUtil.getCurrentActivity());
                    }
                    else
                        Utils.navigation(AppManagerUtil.appContext(), RouterHub.APP_MainStartActivity);
                    finish();
                }
            }
        }, 500);//3秒后执行Runnable中的run方法

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
