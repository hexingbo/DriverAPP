package com.lesso.module.di.module;

import dagger.Binds;
import dagger.Module;

import com.lesso.module.mvp.contract.WelcomeContract;
import com.lesso.module.mvp.model.WelcomeModel;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 20:51
 * Description:
 * ================================================
 */
@Module
public abstract class WelcomeModule {

    @Binds
    abstract WelcomeContract.Model bindWelcomeModel(WelcomeModel model);
}