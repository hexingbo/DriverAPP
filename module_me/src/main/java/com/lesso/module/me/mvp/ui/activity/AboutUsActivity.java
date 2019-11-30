package com.lesso.module.me.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppUtils;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.LogUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerAboutUsComponent;
import com.lesso.module.me.mvp.contract.AboutUsContract;
import com.lesso.module.me.mvp.model.entity.UpdateDetailBean;
import com.lesso.module.me.mvp.presenter.AboutUsPresenter;
import com.skateboard.zxinglib.CaptureActivity;
import com.skateboard.zxinglib.QrCodeUtil;
import com.ycbjie.ycupdatelib.UpdateFragment;
import com.ycbjie.ycupdatelib.UpdateUtils;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.core.Constants;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:关于我们
 * ================================================
 */
public class AboutUsActivity extends BaseActivity<AboutUsPresenter> implements AboutUsContract.View {

    @Inject
    Dialog dialog;

    @BindView(R2.id.tv_version)
    TextView tvVersion;
    @BindView(R2.id.tv_value)
    TextView tvValue;
    @BindView(R2.id.img_logo)
    ImageView imgLogo;

    private int versionCode;
    private String versionName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutUsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_us; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.me_name_about_us);
        UpdateUtils.APP_UPDATE_DOWN_APK_PATH = "apk" + File.separator + "downApk";
        getVersionInfo();

        tvVersion.setText(String.format("version %s", versionName));
    }

    private void getVersionInfo() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionCode = packageInfo.versionCode;
        versionName = packageInfo.versionName;
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
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
    public void onCheckVersion(boolean isNewVersion, UpdateDetailBean.AndroidBean android) {
        DataHelper.setStringSF(getActivity(), Constants.SP_Update_APK_URL, android.getUpdateUrl());
        if (!isNewVersion) {
            showMessage(getString(R.string.is_newest_version));
        } else {
            /*
             * @param isForceUpdate             是否强制更新
             * @param desc                      更新文案
             * @param url                       下载链接
             * @param apkFileName               apk下载文件路径名称
             * @param packName                  包名
             */
            //测试链接
            UpdateFragment.showFragment(AboutUsActivity.this,
                    true, android.getUpdateUrl(), "lessodriver_apk", android.getUpdateContent(),
                    AppUtils.getPackageName(getActivity()));
        }
    }

    @OnClick({R2.id.ll_code_two, R2.id.ll_check_update_app, R2.id.ll_use_help, R2.id.ll_agreement})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.ll_code_two) {
            String url = ArmsUtils.isEmpty(DataHelper.getStringSF(getActivity(), Constants.SP_Update_APK_URL)) ? "https://fir.im/driverapp" : DataHelper.getStringSF(getActivity(), Constants.SP_Update_APK_URL);
            Bitmap qrCode = QrCodeUtil.createQRCode(url);
            imgLogo.setImageBitmap(qrCode);
//            Intent intent = new Intent(getActivity(), CaptureActivity.class);
//            startActivityForResult(intent, 1001);
        } else if (view.getId() == R.id.ll_check_update_app) {
            mPresenter.checkVersionDetail(versionCode);
        } else if (view.getId() == R.id.ll_use_help) {

        } else if (view.getId() == R.id.ll_agreement) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra(CaptureActivity.KEY_DATA);
            LogUtils.warnInfo("hxb--->", "qrcodeResult:" + result);
        }
    }

}
