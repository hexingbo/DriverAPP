package com.lesso.module.me.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.me.mvp.contract.ChoiceJoinCompanyCarsContract;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:42
 * Description:
 * ================================================
 */
@ActivityScope
public class ChoiceJoinCompanyCarsModel extends BaseModel implements ChoiceJoinCompanyCarsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ChoiceJoinCompanyCarsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}