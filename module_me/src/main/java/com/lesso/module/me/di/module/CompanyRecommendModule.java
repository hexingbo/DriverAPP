package com.lesso.module.me.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.lesso.module.me.mvp.contract.CompanyRecommendContract;
import com.lesso.module.me.mvp.model.CompanyRecommendModel;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:41
 * Description:
 * ================================================
 */
@Module
public abstract class CompanyRecommendModule {

    @Binds
    abstract CompanyRecommendContract.Model bindCompanyRecommendModel(CompanyRecommendModel model);
}