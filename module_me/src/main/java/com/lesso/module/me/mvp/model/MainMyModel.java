package com.lesso.module.me.mvp.model;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.lesso.module.me.mvp.contract.MainMyContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.SubmitLoginOutBean;
import com.lesso.module.me.mvp.model.entity.UserInfoBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 11:24
 * 描    述：
 * =============================================
 */
@FragmentScope
public class MainMyModel extends BaseModel implements MainMyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainMyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult<UserInfoBean>> getUserInfo(@NonNull String driverId) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).getUserInfo(driverId);
    }

    @Override
    public Observable<HttpResult> postLoginOut(@NonNull String account,@NonNull String deviceNo) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postLoginOutUserApp(new SubmitLoginOutBean(account, deviceNo));
    }
}