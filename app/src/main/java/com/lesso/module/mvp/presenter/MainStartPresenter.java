package com.lesso.module.mvp.presenter;

import android.Manifest;
import android.app.Application;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.armscomponent.commonsdk.utils.MapManagerUtils;
import me.jessyan.armscomponent.commonsdk.utils.MapUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.mvp.contract.MainStartContract;

import java.util.ArrayList;
import java.util.List;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/10/02 16:30
 * 描    述：
 * =============================================
 */
@ActivityScope
public class MainStartPresenter extends BasePresenter<MainStartContract.Model, MainStartContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainStartPresenter(MainStartContract.Model model, MainStartContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public ArrayList<CustomTabEntity> getTabEntity() {
        return mModel.getTabEntity();
    }

    public void checkPermission() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        if (mRootView.getRequestPermission() != null&& mRootView.getRxPermissions()!=null)
            PermissionUtil.requestPermission(mRootView.getRequestPermission(), mRootView.getRxPermissions(), mErrorHandler,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE);
    }

}
