package com.lesso.module.login.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.lesso.module.login.mvp.contract.UpdatePwdActivityContract;
import com.lesso.module.login.mvp.model.UpdatePwdActivityModel;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:47
 * Description:
 * ================================================
 */
@Module
public abstract class UpdatePwdActivityModule {

    @Binds
    abstract UpdatePwdActivityContract.Model bindUpdatePwdActivityModel(UpdatePwdActivityModel model);
}