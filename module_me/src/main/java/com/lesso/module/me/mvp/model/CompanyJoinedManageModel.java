package com.lesso.module.me.mvp.model;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.me.mvp.contract.CompanyJoinedManageContract;
import com.lesso.module.me.mvp.model.api.service.ModuleMeService;
import com.lesso.module.me.mvp.model.entity.CompanyJoinedBean;
import com.lesso.module.me.mvp.model.entity.SubmitCompanyJoinedActionBean;
import com.lesso.module.me.mvp.model.entity.SubmitCompanyJoinedListBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.CompanyActionType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:14
 * Description:
 * ================================================
 */
@ActivityScope
public class CompanyJoinedManageModel extends BaseModel implements CompanyJoinedManageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CompanyJoinedManageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<HttpResult<List<CompanyJoinedBean>>> postCompanyJoinedList(int current, int size, @NonNull String driverId) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postCompanyJoinedList(
                new SubmitCompanyJoinedListBean(current, size, new SubmitCompanyJoinedListBean.ConditionBean(driverId)));
    }

    @Override
    public Observable<HttpResult> postCompanyJoinedAction(@NonNull String companyId, @NonNull String driverId, @NonNull String joinId, @Nullable CompanyActionType operatorType) {
        return mRepositoryManager.obtainRetrofitService(ModuleMeService.class).postCompanyJoinedAction(new SubmitCompanyJoinedActionBean(companyId, driverId, joinId, operatorType));
    }
}