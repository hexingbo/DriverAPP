package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerUserAuthenticationComponent;
import com.lesso.module.me.mvp.contract.UserAuthenticationContract;
import com.lesso.module.me.mvp.presenter.UserAuthenticationPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonres.other.ClearEditText;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 15:31
 * Description:用户认证
 * ================================================
 */
@Route(path = RouterHub.Me_UserAuthenticationActivity)
public class UserAuthenticationActivity extends BaseActivity<UserAuthenticationPresenter> implements UserAuthenticationContract.View {

    @Inject
    RxPermissions mRxPermissions;
    @Inject
    Dialog mDialog;
    @Inject
    MyHintDialog mMyHintDialog;
    @Inject
    MaterialDialog mPermissionDialog;

    @BindString(R2.string.permission_request_location)
    String mStrPermission;

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
        UploadFileUserCardType fileTypes;
        if (view.getId() == R.id.img_card_user_s) {
            fileTypes = UploadFileUserCardType.IdCard;
            showMessage("身份证正面照");
        } else if (view.getId() == R.id.img_card_user_n) {
            fileTypes = UploadFileUserCardType.IdCardBack;
            showMessage("身份证背面照");
        } else if (view.getId() == R.id.img_card_user_get_card) {
            fileTypes = UploadFileUserCardType.LifePhoto;
            showMessage("手持身份证正面照");
        } else if (view.getId() == R.id.img_card_driver_s) {
            fileTypes = UploadFileUserCardType.DriverCard;
            showMessage("驾驶证正面照");
        } else if (view.getId() == R.id.img_card_driver_n) {
            fileTypes = UploadFileUserCardType.DriverCardBack;
            showMessage("驾驶证背面照");
        } else fileTypes = UploadFileUserCardType.LifePhoto;

        mPresenter.checkPermission(fileTypes);
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
    public PermissionUtil.RequestPermission getRequestPermission() {
        return new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.

            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                //如果用户拒绝了其中一个授权请求，则提醒用户
//                showMessage("Request permissions failure");
                showMessage(mStrPermission);
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                //如果用户拒绝了其中一个授权请求，且勾选了不再提醒，则需要引导用户到权限管理页面开启
//                showMessage("Need to go to the settings");
                mPermissionDialog.setMessage("请前往设置中心开启相应权限");
                mPermissionDialog.show();
            }
        };
    }
}
