package com.lesso.module.me.mvp.model;

import android.Manifest;
import android.app.Application;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.me.mvp.contract.UserInfoContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.DriverVerifyDetailBean;
import com.lesso.module.me.mvp.model.entity.SubmitDriverVerifyDetailBean;
import com.lesso.module.me.mvp.model.entity.SubmitUploadDriverHeadFileBean;
import com.lesso.module.me.mvp.model.entity.SubmitUploadDriverInfoFileBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:
 * ================================================
 */
@ActivityScope
public class UserInfoModel extends BaseModel implements UserInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public void checkPermission(RxPermissions rxPermissions, PermissionUtil.RequestPermission requestPermission, RxErrorHandler mErrorHandler) {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.requestPermission(requestPermission, (RxPermissions) requestPermission, mErrorHandler,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public Observable<HttpResult> postUploadDriverInfoFile(@Nullable UploadFileUserCardType fileTypes, @Nullable List<File> fileArr) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postUploadDriverInfoFile(new SubmitUploadDriverInfoFileBean(fileTypes, fileArr));
    }

    @Override
    public Observable<HttpResult<DriverVerifyDetailBean>> getDriverVerifyDetail(String guid) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).getDriverVerifyDetail(new SubmitDriverVerifyDetailBean(guid));
    }

    @Override
    public Observable<HttpResult> postUploadDriverHeadFile(@Nullable String currentUserId, @Nullable File personFile) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postUploadDriverHeadFile(new SubmitUploadDriverHeadFileBean(currentUserId, personFile));
    }

    @Override
    public Observable<HttpResult> postSaveDriverVerifyInfo(@Nullable DriverVerifyDetailBean bean) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postSaveDriverVerifyInfo(bean);
    }
}