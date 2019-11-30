package com.lesso.module.me.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.lesso.module.me.mvp.contract.ChoiceJoinCompanyCarsContract;
import com.lesso.module.me.mvp.model.ChoiceJoinCompanyCarsModel;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:42
 * Description:
 * ================================================
 */
@Module
public abstract class ChoiceJoinCompanyCarsModule {

    @Binds
    abstract ChoiceJoinCompanyCarsContract.Model bindChoiceJoinCompanyCarsModel(ChoiceJoinCompanyCarsModel model);
}