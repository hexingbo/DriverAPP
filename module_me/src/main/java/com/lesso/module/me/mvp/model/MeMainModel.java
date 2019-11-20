package com.lesso.module.me.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lesso.module.me.mvp.contract.MeMainContract;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 10:04
 * 描    述：
 * =============================================
 */
@ActivityScope
public class MeMainModel extends BaseModel implements MeMainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MeMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}