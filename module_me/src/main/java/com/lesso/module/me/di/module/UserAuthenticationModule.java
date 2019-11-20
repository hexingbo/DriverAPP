package com.lesso.module.me.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.lesso.module.me.mvp.contract.UserAuthenticationContract;
import com.lesso.module.me.mvp.model.UserAuthenticationModel;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 15:31
 * Description:
 * ================================================
 */
@Module
public abstract class UserAuthenticationModule {

    @Binds
    abstract UserAuthenticationContract.Model bindUserAuthenticationModel(UserAuthenticationModel model);
}