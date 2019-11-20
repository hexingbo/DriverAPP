package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerUserInfoComponent;
import com.lesso.module.me.mvp.contract.UserInfoContract;
import com.lesso.module.me.mvp.model.entity.DriverVerifyDetailBean;
import com.lesso.module.me.mvp.presenter.UserInfoPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonres.other.CircleImageView;
import me.jessyan.armscomponent.commonres.other.ClearEditText;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:个人资料
 * ================================================
 */
public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View {

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

    @BindView(R2.id.public_toolbar_text_rigth)
    TextView publicToolbarTextRigth;
    @BindView(R2.id.img_user_head)
    CircleImageView imgUserHead;
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
        DaggerUserInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        setTitle(R.string.me_name_user_info);
        publicToolbarTextRigth.setText(getString(R.string.module_me_name_save));
        setAddViewGONE();
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mPresenter.getDriverVerifyDetail();
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
    public PermissionUtil.RequestPermission getRequestPermission() {
        return null;
    }

    @Override
    public void setDriverVerifyDetailBean(DriverVerifyDetailBean bean) {
        if (bean != null) {
            etUserName.setText(bean.getDriverBy());
            etUserCardNumber.setText(bean.getIdno());
            etDriverCardNumber.setText(bean.getDriverno());
            loadImageData(bean.getIdCardUrl(), imgCardUserS);
            loadImageData(bean.getIdCardBackUrl(), imgCardUserN);
            loadImageData(bean.getDriverCardUrl(), imgAddCardDriverS);
            loadImageData(bean.getDriverCardBackUrl(), imgAddCardDriverN);
            loadImageData(bean.getLifePhotoUrl(), imgAddCardUserGetCard);
            loadImageData(bean.getHeadUrl(), imgUserHead);
        }
    }

    private void setAddViewGONE() {
        imgAddCardUserN.setVisibility(View.GONE);
        imgAddCardUserS.setVisibility(View.GONE);
        imgAddCardDriverS.setVisibility(View.GONE);
        imgAddCardDriverN.setVisibility(View.GONE);
        imgAddCardUserGetCard.setVisibility(View.GONE);
    }

    @OnClick({R2.id.public_toolbar_text_rigth, R2.id.img_user_head, R2.id.img_card_user_s,
            R2.id.img_card_user_n, R2.id.img_card_user_get_card, R2.id.img_card_driver_s, R2.id.img_card_driver_n})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_user_head) {
            showMessage("头像");
        } else if (view.getId() == R.id.img_card_user_s) {
            showMessage("身份证正面照");
        } else if (view.getId() == R.id.img_card_user_n) {
            showMessage("身份证背面照");
        } else if (view.getId() == R.id.img_card_user_get_card) {
            showMessage("手持身份证正面照");
        } else if (view.getId() == R.id.img_card_driver_s) {
            showMessage("驾驶证正面照");
        } else if (view.getId() == R.id.img_card_driver_n) {
            showMessage("驾驶证背面照");
        } else if (view.getId() == R.id.public_toolbar_text_rigth) {
            showMessage("保存");
        }
    }

    private void loadImageData(String url, ImageView imageView) {
        if (!ArmsUtils.isEmpty(url)) {
            ArmsUtils.obtainAppComponentFromContext(getActivity()).imageLoader().loadImage(getActivity(),
                    CommonImageConfigImpl
                            .builder()
                            .url(url)
                            .imageView(imageView)
                            .build());
        }
    }
}
