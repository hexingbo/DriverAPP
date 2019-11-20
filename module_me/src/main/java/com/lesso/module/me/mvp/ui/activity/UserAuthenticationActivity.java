package com.lesso.module.me.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerUserAuthenticationComponent;
import com.lesso.module.me.mvp.contract.UserAuthenticationContract;
import com.lesso.module.me.mvp.presenter.UserAuthenticationPresenter;

import com.lesso.module.me.R;


import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.other.CircleImageView;
import me.jessyan.armscomponent.commonres.other.ClearEditText;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 15:31
 * Description:用户认证
 * ================================================
 */
public class UserAuthenticationActivity extends BaseActivity<UserAuthenticationPresenter> implements UserAuthenticationContract.View {

    @BindView(R2.id.et_user_name)
    ClearEditText etUserName;
    @BindView(R2.id.et_user_card_number)
    ClearEditText etUserCardNumber;
    @BindView(R2.id.et_driver_card_number)
    ClearEditText etDriverCardNumber;
    @BindView(R2.id.img_card_user_s)
    ImageView imgCardUserS;
    @BindView(R2.id.img_add_card_user_s)
    ImageView imgAddCardUserS;
    @BindView(R2.id.img_card_user_n)
    ImageView imgCardUserN;
    @BindView(R2.id.img_add_card_user_n)
    ImageView imgAddCardUserN;
    @BindView(R2.id.img_card_user_get_card)
    ImageView imgCardUserGetCard;
    @BindView(R2.id.img_add_card_user_get_card)
    ImageView imgAddCardUserGetCard;
    @BindView(R2.id.img_card_driver_s)
    ImageView imgCardDriverS;
    @BindView(R2.id.img_add_card_driver_s)
    ImageView imgAddCardDriverS;
    @BindView(R2.id.img_card_driver_n)
    ImageView imgCardDriverN;
    @BindView(R2.id.img_add_card_driver_n)
    ImageView imgAddCardDriverN;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserAuthenticationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_authentication; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        setTitle(R.string.module_me_user_authentication);
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

    @OnClick({R2.id.img_card_user_s, R2.id.img_card_user_n, R2.id.img_card_user_get_card, R2.id.img_card_driver_s, R2.id.img_card_driver_n})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_card_user_s) {
            showMessage("身份证正面照");
        } else if (view.getId() == R.id.img_card_user_n) {
            showMessage("身份证背面照");
        } else if (view.getId() == R.id.img_card_user_get_card) {
            showMessage("手持身份证正面照");
        } else if (view.getId() == R.id.img_card_driver_s) {
            showMessage("驾驶证正面照");
        } else if (view.getId() == R.id.img_card_driver_n) {
            showMessage("驾驶证背面照");
        }
    }
}
