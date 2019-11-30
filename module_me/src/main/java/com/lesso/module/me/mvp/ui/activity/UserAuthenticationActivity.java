package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerUserAuthenticationComponent;
import com.lesso.module.me.mvp.contract.UserAuthenticationContract;
import com.lesso.module.me.mvp.model.entity.SubmitDriverVerifyBean;
import com.lesso.module.me.mvp.presenter.UserAuthenticationPresenter;
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
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonres.other.ClearEditText;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.Utils;
import me.jessyan.armscomponent.commonsdk.utils.ViewFocusUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 15:31
 * Description:用户认证
 * ================================================
 */
@Route(path = RouterHub.Me_UserAuthenticationActivity)
public class UserAuthenticationActivity extends BaseActivity<UserAuthenticationPresenter> implements UserAuthenticationContract.View, TextWatcher {

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
    @BindView(R2.id.img_add_card_user_delete_s)
    ImageView imgAddCardUserDeleteS;
    @BindView(R2.id.img_card_user_n)
    ImageView imgCardUserN;
    @BindView(R2.id.img_add_card_user_n)
    ImageView imgAddCardUserN;
    @BindView(R2.id.img_add_card_user_delete_n)
    ImageView imgAddCardUserDeleteN;
    @BindView(R2.id.img_card_user_get_card)
    ImageView imgCardUserGetCard;
    @BindView(R2.id.img_add_card_user_get_card)
    ImageView imgAddCardUserGetCard;
    @BindView(R2.id.img_card_user_get_card_delete)
    ImageView imgAddCardUserGetCardDelete;
    @BindView(R2.id.img_card_driver_s)
    ImageView imgCardDriverS;
    @BindView(R2.id.img_add_card_driver_s)
    ImageView imgAddCardDriverS;
    @BindView(R2.id.img_add_card_driver_delete_s)
    ImageView imgAddCardDriverDeleteS;
    @BindView(R2.id.img_card_driver_n)
    ImageView imgCardDriverN;
    @BindView(R2.id.img_add_card_driver_n)
    ImageView imgAddCardDriverN;
    @BindView(R2.id.img_add_card_driver_delete_n)
    ImageView imgAddCardDriverDeleteN;
    @BindView(R2.id.btn_submit)
    TextView btnSubmit;

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
        setTitle(R.string.module_me_user_authentication);
        btnSubmit.setText(getString(R.string.module_me_name_submit));
        initViewState();
        mPresenter.setComeInFromTheMainStart(getIntent().getBooleanExtra("isComeInFromTheMainStart", false));
        etUserCardNumber.addTextChangedListener(this);
        mPresenter.getSubmitDriverVerifyBean();//初始化数据

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        etDriverCardNumber.setText(s);
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
    public void onBackPressed() {
        if (!mPresenter.getComeInFromTheMainStart()) {
            Utils.navigation(this, RouterHub.APP_MainStartActivity);
        }
        super.onBackPressed();
    }

    @Override
    public void killMyself() {
        finish();
    }


    private void initViewState() {
        setViewAddCardUserS(true);
        setViewAddCardUserN(true);
        setViewAddCardUserGetCard(true);
        setViewAddCardDriverN(true);
        setViewAddCardDriverS(true);
    }

    /**
     * 身份证正面照View的操作
     *
     * @param addPicture
     */
    private void setViewAddCardUserS(boolean addPicture) {
        imgAddCardUserS.setVisibility(addPicture ? View.VISIBLE : View.GONE);
        imgAddCardUserDeleteS.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        imgCardUserS.setEnabled(!addPicture);
    }

    /**
     * 身份证背面照View的操作
     *
     * @param addPicture
     */
    private void setViewAddCardUserN(boolean addPicture) {
        imgAddCardUserN.setVisibility(addPicture ? View.VISIBLE : View.GONE);
        imgAddCardUserDeleteN.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        imgCardUserN.setEnabled(!addPicture);
    }

    /**
     * 驾驶证主页照View的操作
     *
     * @param addPicture
     */
    private void setViewAddCardDriverS(boolean addPicture) {
        imgAddCardDriverS.setVisibility(addPicture ? View.VISIBLE : View.GONE);
        imgAddCardDriverDeleteS.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        imgCardDriverS.setEnabled(!addPicture);
    }

    /**
     * 驾驶证副页照View的操作
     *
     * @param addPicture
     */
    private void setViewAddCardDriverN(boolean addPicture) {
        imgAddCardDriverN.setVisibility(addPicture ? View.VISIBLE : View.GONE);
        imgAddCardDriverDeleteN.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        imgCardDriverN.setEnabled(!addPicture);
    }

    /**
     * 手持身份照View的操作
     *
     * @param addPicture
     */
    private void setViewAddCardUserGetCard(boolean addPicture) {
        imgAddCardUserGetCard.setVisibility(addPicture ? View.VISIBLE : View.GONE);
        imgAddCardUserGetCardDelete.setVisibility(!addPicture ? View.VISIBLE : View.GONE);
        imgCardUserGetCard.setEnabled(!addPicture);
    }


    @OnClick({R2.id.btn_submit})
    public void onViewOtherClicked(View view) {
        mPresenter.postDriverVerify(etUserName.getText().toString().trim(),
                etUserCardNumber.getText().toString().trim(),
                etDriverCardNumber.getText().toString().trim());
    }


    @OnClick({R2.id.img_card_user_s,
            R2.id.img_card_user_n, R2.id.img_card_user_get_card,
            R2.id.img_card_driver_s, R2.id.img_card_driver_n})
    public void onViewLookImgClicked(View view) {
        ViewFocusUtils.setRequestFocus(view);
        String path = "";
        if (view.getId() == R.id.img_card_user_s) {
            path = mPresenter.getSubmitDriverVerifyBean().getIdCardUrl();
        } else if (view.getId() == R.id.img_card_user_n) {
            path = mPresenter.getSubmitDriverVerifyBean().getIdCardBackUrl();
        } else if (view.getId() == R.id.img_card_user_get_card) {
            path = mPresenter.getSubmitDriverVerifyBean().getLifePhotoUrl();
        } else if (view.getId() == R.id.img_card_driver_s) {
            path = mPresenter.getSubmitDriverVerifyBean().getDriverCardUrl();
        } else if (view.getId() == R.id.img_card_driver_n) {
            path = mPresenter.getSubmitDriverVerifyBean().getDriverCardBackUrl();
        }
        mPresenter.openExternalPreview(path);
    }

    @OnClick({R2.id.img_add_card_user_delete_s,
            R2.id.img_add_card_user_delete_n, R2.id.img_card_user_get_card_delete,
            R2.id.img_add_card_driver_delete_s, R2.id.img_add_card_driver_delete_n})
    public void onViewDeleteImgClicked(View view) {
        ViewFocusUtils.setRequestFocus(view);
        UploadFileUserCardType fileTypes = null;
        if (view.getId() == R.id.img_add_card_user_delete_s) {
            fileTypes = UploadFileUserCardType.IdCard;
        } else if (view.getId() == R.id.img_add_card_user_delete_n) {
            fileTypes = UploadFileUserCardType.IdCardBack;
        } else if (view.getId() == R.id.img_card_user_get_card_delete) {
            fileTypes = UploadFileUserCardType.LifePhoto;
        } else if (view.getId() == R.id.img_add_card_driver_delete_s) {
            fileTypes = UploadFileUserCardType.DriverCard;
        } else if (view.getId() == R.id.img_add_card_driver_delete_n) {
            fileTypes = UploadFileUserCardType.DriverCardBack;
        }
        setImageViewPicture("", fileTypes, mPresenter.getSubmitDriverVerifyBean());
    }

    @OnClick({R2.id.img_add_card_user_s,
            R2.id.img_add_card_user_n, R2.id.img_add_card_user_get_card,
            R2.id.img_add_card_driver_s, R2.id.img_add_card_driver_n})
    public void onViewAddImgClicked(View view) {
        ViewFocusUtils.setRequestFocus(view);
        UploadFileUserCardType fileTypes = null;
        if (view.getId() == R.id.img_add_card_user_s) {
            fileTypes = UploadFileUserCardType.IdCard;
        } else if (view.getId() == R.id.img_add_card_user_n) {
            fileTypes = UploadFileUserCardType.IdCardBack;
        } else if (view.getId() == R.id.img_add_card_user_get_card) {
            fileTypes = UploadFileUserCardType.LifePhoto;
        } else if (view.getId() == R.id.img_add_card_driver_s) {
            fileTypes = UploadFileUserCardType.DriverCard;
        } else if (view.getId() == R.id.img_add_card_driver_n) {
            fileTypes = UploadFileUserCardType.DriverCardBack;
        }
        mPresenter.checkPermission(fileTypes);

    }


    @Override
    public void setImageViewPicture(String filePath, UploadFileUserCardType fileTypes, SubmitDriverVerifyBean bean) {
        ImageView imageView = imgCardUserS;
        int red = R.mipmap.app_logo;
        switch (fileTypes) {
            case IdCard:
                setViewAddCardUserS(ArmsUtils.isEmpty(filePath));
                imageView = imgCardUserS;
                red = R.mipmap.ic_card_user_s;
                bean.setIdCardUrl(filePath);
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setIdCardPath("");
                }
                break;
            case IdCardBack:
                setViewAddCardUserN(ArmsUtils.isEmpty(filePath));
                imageView = imgCardUserN;
                red = R.mipmap.ic_card_user_n;
                bean.setIdCardBackUrl(filePath);
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setIdCardBackPath("");
                }
                break;
            case LifePhoto:
                setViewAddCardUserGetCard(ArmsUtils.isEmpty(filePath));
                imageView = imgCardUserGetCard;
                red = R.mipmap.ic_card_user_get_card;
                bean.setLifePhotoUrl(filePath);
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setLifePhotoPath("");
                }
                break;
            case DriverCard:
                setViewAddCardDriverS(ArmsUtils.isEmpty(filePath));
                imageView = imgCardDriverS;
                red = R.mipmap.ic_card_driver_s;
                bean.setDriverCardUrl(filePath);
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setDriverCardPath("");
                }
                break;
            case DriverCardBack:
                setViewAddCardDriverN(ArmsUtils.isEmpty(filePath));
                imageView = imgCardDriverN;
                red = R.mipmap.ic_card_driver_n;
                bean.setDriverCardBackUrl(filePath);
                if (ArmsUtils.isEmpty(filePath)) {
                    bean.setDriverCardBackPath("");
                }
                break;
        }
        mPresenter.setImageViewPicture(filePath, imageView, red);

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
                        if (localMedia.isCut()) {
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
