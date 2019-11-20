package com.lesso.module.me.di.module;

import dagger.Binds;
import dagger.Module;

import com.lesso.module.me.mvp.contract.MeMainContract;
import com.lesso.module.me.mvp.model.MeMainModel;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 10:04
 * 描    述：
 * =============================================
 */
@Module
public abstract class MeMainModule {

    @Binds
    abstract MeMainContract.Model bindMeMainModel(MeMainModel model);
}