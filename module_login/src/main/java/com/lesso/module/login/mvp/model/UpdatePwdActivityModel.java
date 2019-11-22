package com.lesso.module.login.mvp.model;

import android.app.Application;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.login.mvp.contract.UpdatePwdActivityContract;
import com.lesso.module.login.mvp.model.api.service.ModuleLoginService;
import com.lesso.module.login.mvp.model.entity.SubmitUpdatePwdBean;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:47
 * Description:
 * ================================================
 */
@ActivityScope
public class UpdatePwdActivityModel extends BaseModel implements UpdatePwdActivityContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UpdatePwdActivityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult> postUpdatePassword(@Nullable SubmitUpdatePwdBean bean) {
        return mRepositoryManager.obtainRetrofitService(ModuleLoginService.class).postUpdatePassword(bean);
    }
}