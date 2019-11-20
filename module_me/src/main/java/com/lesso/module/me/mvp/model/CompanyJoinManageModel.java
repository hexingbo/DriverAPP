package com.lesso.module.me.mvp.model;

import android.app.Application;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.me.mvp.contract.CompanyJoinManageContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:28
 * Description:
 * ================================================
 */
@ActivityScope
public class CompanyJoinManageModel extends BaseModel implements CompanyJoinManageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CompanyJoinManageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult<List<CompanyJoinBean>>> getSeachCompanyJoinList(@Nullable String companyName) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).getSeachCompanyJoinList(companyName);
    }

    @Override
    public Observable<HttpResult<List<CompanyJoinBean>>> getRecommendCompanyJoinList(int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).getRecommendCompanyJoinList(pageSize);
    }
}