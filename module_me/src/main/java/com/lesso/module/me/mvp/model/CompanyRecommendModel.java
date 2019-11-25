package com.lesso.module.me.mvp.model;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.me.mvp.contract.CompanyRecommendContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.lesso.module.me.mvp.model.entity.SubmitCompanyJoiningBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:41
 * Description:
 * ================================================
 */
@ActivityScope
public class CompanyRecommendModel extends BaseModel implements CompanyRecommendContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CompanyRecommendModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult<List<CompanyJoinBean>>> getRecommendCompanyJoinList(int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).getRecommendCompanyJoinList(pageSize);
    }

    @Override
    public Observable<HttpResult> postCompanyJoin(@NonNull String companyId, @NonNull String driverId, @NonNull String joinType) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postCompanyJoin(new SubmitCompanyJoiningBean(companyId,driverId,joinType));
    }

}