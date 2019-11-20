package com.lesso.module.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.mvp.contract.WelcomeContract;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 20:51
 * Description:
 * ================================================
 */
@ActivityScope
public class WelcomeModel extends BaseModel implements WelcomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WelcomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}