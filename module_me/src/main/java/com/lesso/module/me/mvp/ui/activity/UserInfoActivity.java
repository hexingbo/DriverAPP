package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerUserInfoComponent;
import com.lesso.module.me.mvp.contract.UserInfoContract;
import com.lesso.module.me.mvp.model.entity.DriverVerifyDetailBean;
import com.lesso.module.me.mvp.presenter.UserInfoPresenter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.dialog.MaterialDialog;
import me.jessyan.armscomponent.commonres.dialog.MyHintDialog;
import me.jessyan.armscomponent.commonres.enums.AuthenticationStatusType;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonres.other.CircleImageView;
import me.jessyan.armscomponent.commonres.other.ClearEditText;
import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static org.openjdk.tools.sjavac.Util.set;


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

    @BindView(R2.id.img_verify_status)
    ImageView imgVerifyStatus;
    @BindView(R2.id.public_toolbar_text_rigth)
    TextView publicToolbarTextRigth;
    @BindView(R2.id.img_user_head)
    ImageView imgUserHead;
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
        return new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                mPresenter.getPictureSelector();
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

    @Override
    public void setDriverVerifyDetailBean(DriverVerifyDetailBean bean) {
        if (bean != null) {
            etUserName.setText(bean.getDriverBy());
            setUserCardNumber(bean.getIdno());
            setDriverCardNumber(bean.getDriverno());
            mPresenter.setImageViewPicture(bean.getIdCardUrl(), imgCardUserS, R.mipmap.ic_card_user_s);
            mPresenter.setImageViewPicture(bean.getIdCardBackUrl(), imgCardUserN, R.mipmap.ic_card_user_n);
            mPresenter.setImageViewPicture(bean.getDriverCardUrl(), imgCardDriverS, R.mipmap.ic_card_driver_s);
            mPresenter.setImageViewPicture(bean.getDriverCardBackUrl(), imgCardDriverN, R.mipmap.ic_card_driver_n);
            mPresenter.setImageViewPicture(bean.getLifePhotoUrl(), imgCardUserGetCard, R.mipmap.ic_card_user_get_card);
            mPresenter.setImageViewHeadPicture(bean.getHeadUrl(), imgUserHead);
            setViewVerifyStatus(bean.getVerifyStatus());
        }
    }

    private void setViewVerifyStatus(String verifyStatus) {
        if (verifyStatus.equals(AuthenticationStatusType.D.name())) {
            //审批通过
            imgVerifyStatus.setImageResource(R.mipmap.ic_user_attestationed);
        } else if (verifyStatus.equals(AuthenticationStatusType.F.name())) {
            //审批未通过
            imgVerifyStatus.setImageResource(R.mipmap.ic_user_attestation_error);
        } else {
            imgVerifyStatus.setImageResource(R.mipmap.ic_user_attestation_not);
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
        if (view.getId() == R.id.public_toolbar_text_rigth) {
            mPresenter.postSaveDriverVerifyInfo(etUserName.getText().toString().trim(),
                    etUserCardNumber.getText().toString().trim(), etDriverCardNumber.getText().toString().trim());
        } else {
            UploadFileUserCardType fileTypes;
            if (view.getId() == R.id.img_user_head) {
                showMessage("头像");
                fileTypes = UploadFileUserCardType.HeadPhoto;
            } else if (view.getId() == R.id.img_card_user_s) {
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

    }

    @Override
    public void setImageViewPicture(String filePath, UploadFileUserCardType fileTypes) {

        if (fileTypes == UploadFileUserCardType.HeadPhoto) {
            mPresenter.setImageViewHeadPicture(filePath, imgUserHead);
        } else {
            ImageView imageView = null;
            int red;
            switch (fileTypes) {
                case IdCard:
                    imageView = imgCardUserS;
                    red = R.mipmap.ic_card_user_s;
                    break;
                case IdCardBack:
                    imageView = imgCardUserN;
                    red = R.mipmap.ic_card_user_n;
                    break;
                case LifePhoto:
                    imageView = imgAddCardUserGetCard;
                    red = R.mipmap.ic_card_user_get_card;
                    break;
                case DriverCard:
                    imageView = imgAddCardDriverS;
                    red = R.mipmap.ic_card_driver_s;
                    break;
                case DriverCardBack:
                    imageView = imgAddCardDriverN;
                    red = R.mipmap.ic_card_driver_n;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: have not mipmap resId");
            }
            if (red != 0)
                mPresenter.setImageViewPicture(filePath, imageView, red);

        }
    }

    @Override
    public void setUserCardNumber(String userCardNumber) {
        etUserCardNumber.setText(userCardNumber);
    }

    @Override
    public void setDriverCardNumber(String driverCardNumber) {
        etDriverCardNumber.setText(driverCardNumber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回四种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    // 4.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    for (LocalMedia localMedia : selectList) {
                        if (localMedia.isCut()){
                            String path = localMedia.getCutPath();
                            Log.i("hxb：", "图片保存路径==>" + path);
                            //上传头像
                            File file = new File(path);
                            mPresenter.postUploadFile(file);
                        }

                    }

                    break;
            }
        }
    }


}
