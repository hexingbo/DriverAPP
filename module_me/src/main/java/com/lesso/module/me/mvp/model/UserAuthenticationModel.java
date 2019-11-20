package com.lesso.module.me.mvp.model;

import android.Manifest;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.me.mvp.contract.UserAuthenticationContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.CompanyJoinedBean;
import com.lesso.module.me.mvp.model.entity.SubmitDriverVerifyBean;
import com.lesso.module.me.mvp.model.entity.SubmitUploadDriverInfoFileBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 15:31
 * Description:
 * ================================================
 */
@ActivityScope
public class UserAuthenticationModel extends BaseModel implements UserAuthenticationContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserAuthenticationModel(IRepositoryManager repositoryManager) {
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
            PermissionUtil.requestPermission(requestPermission,rxPermissions, mErrorHandler,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public Observable<HttpResult> postUploadDriverInfoFile(@Nullable UploadFileUserCardType fileTypes, @Nullable List<File> fileArr) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postUploadDriverInfoFile(new SubmitUploadDriverInfoFileBean(fileTypes, fileArr));
    }

    @Override
    public Observable<HttpResult<CompanyJoinedBean>> postDriverVerify(@NonNull String currentUserId,
                                                                      @NonNull String name, @NonNull String idno,
                                                                      @NonNull String driverno, @NonNull String idCardPath,
                                                                      @NonNull String idCardBackPath, @NonNull String driverCardPath,
                                                                      @NonNull String driverCardBackPath, String lifePhotoPath) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class)
                .postDriverVerify(new SubmitDriverVerifyBean(currentUserId, name, idno, driverno,
                        idCardPath, idCardBackPath, driverCardPath, driverCardBackPath, lifePhotoPath));
    }
}