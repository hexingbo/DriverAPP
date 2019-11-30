package com.lesso.module.me.mvp.model;

import android.app.Application;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lesso.module.me.mvp.contract.UserInfoContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.DriverVerifyDetailBean;
import com.lesso.module.me.mvp.model.entity.SubmitDriverVerifyDetailBean;
import com.lesso.module.me.mvp.model.entity.UploadCardFileResultBean;
import com.lesso.module.me.mvp.model.entity.UploadHeadFileResultBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.armscomponent.commonsdk.base.bean.RequestBodyBean;
import me.jessyan.armscomponent.commonsdk.utils.RequestBodyUtil;


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
    public Observable<HttpResult<UploadCardFileResultBean>> postUploadDriverInfoFile(@Nullable String currentUserId, @Nullable UploadFileUserCardType fileTypes, @Nullable List<File> fileArr) {
        Map<String, Object> mapValues = new HashMap<>();
        mapValues.put("currentUserId ",currentUserId);//订单id
        mapValues.put("fileTypes ", fileTypes);//订单id
        Map<String, File> map = new HashMap<>();
        map.put("fileArr", fileArr.get(0));
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postUploadDriverInfoFile
                (RequestBodyUtil.getRequestBodyValueAndFile(map, new RequestBodyBean(null, mapValues)));
    }

    @Override
    public Observable<HttpResult<DriverVerifyDetailBean>> getDriverVerifyDetail(String guid) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).getDriverVerifyDetail(new SubmitDriverVerifyDetailBean(guid));
    }

    @Override
    public Observable<HttpResult<UploadHeadFileResultBean>> postUploadDriverHeadFile(@Nullable String currentUserId, @Nullable File personFile) {
        Map<String, Object> mapValues = new HashMap<>();
        mapValues.put("currentUserId ", currentUserId);//订单id
        Map<String, File> map = new HashMap<>();
        map.put("personFile", personFile);
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postUploadDriverHeadFile
                (RequestBodyUtil.getRequestBodyValueAndFile(map, new RequestBodyBean(null, mapValues)));
    }

    @Override
    public Observable<HttpResult> postSaveDriverVerifyInfo(@Nullable DriverVerifyDetailBean bean) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postSaveDriverVerifyInfo(bean);
    }
}