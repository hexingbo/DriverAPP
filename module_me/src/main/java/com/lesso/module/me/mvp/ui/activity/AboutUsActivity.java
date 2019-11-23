package com.lesso.module.me.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.AppUtils;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.lesso.module.me.R;
import com.lesso.module.me.R2;
import com.lesso.module.me.di.component.DaggerAboutUsComponent;
import com.lesso.module.me.mvp.contract.AboutUsContract;
import com.lesso.module.me.mvp.presenter.AboutUsPresenter;
import com.skateboard.zxinglib.CaptureActivity;
import com.ycbjie.ycupdatelib.PermissionUtils;
import com.ycbjie.ycupdatelib.UpdateFragment;
import com.ycbjie.ycupdatelib.UpdateUtils;

import java.io.File;

import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:关于我们
 * ================================================
 */
public class AboutUsActivity extends BaseActivity<AboutUsPresenter> implements AboutUsContract.View {

    //这个是你的包名
    private static final String apkName = "apk";
    private static final String firstUrl = "https://download.fir.im/apps/5dd1f571f9454805df438861/install?download_token=ef15171d45758de7425af4f81c029eee&release_id=5dd3616ff9454853f29d3aaf";
    private static final String url = "http://img1.haowmc.com/hwmc/test/android_";
    private static final String[] mPermission = {
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.VIBRATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE};


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
        ArmsUtils.statuInScreen(this);//全屏,并且沉侵式状态栏
        setTitle(R.string.me_name_about_us);

        PermissionUtils.init(this);
        boolean granted = PermissionUtils.isGranted(mPermission);
        if (!granted) {
            PermissionUtils permission = PermissionUtils.permission(mPermission);
            permission.callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {

                }

                @Override
                public void onDenied() {
                    PermissionUtils.openAppSettings();
                    Toast.makeText(AboutUsActivity.this, "请允许权限", Toast.LENGTH_SHORT).show();
                }
            });
            permission.request();
        }
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
    public Activity getActivity() {
        return this;
    }


    @OnClick({R2.id.ll_code_two, R2.id.ll_check_update_app, R2.id.ll_use_help, R2.id.ll_agreement})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.ll_code_two) {
            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(intent, 1001);
        } else if (view.getId() == R.id.ll_check_update_app) {
            //设置自定义下载文件路径
            UpdateUtils.APP_UPDATE_DOWN_APK_PATH = "apk" + File.separator + "downApk";
            String desc = getResources().getString(R.string.update_content_info);
            /*
             * @param isForceUpdate             是否强制更新
             * @param desc                      更新文案
             * @param url                       下载链接
             * @param apkFileName               apk下载文件路径名称
             * @param packName                  包名
             */
            UpdateFragment.showFragment(AboutUsActivity.this,
                    false, firstUrl, apkName, desc, AppUtils.getPackageName(getActivity()));
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
