package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.MyCarsListManagerModule;
import com.lesso.module.me.mvp.contract.MyCarsListManagerContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.MyCarsListManagerActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:38
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = MyCarsListManagerModule.class, dependencies = AppComponent.class)
public interface MyCarsListManagerComponent {
    void inject(MyCarsListManagerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCarsListManagerComponent.Builder view(MyCarsListManagerContract.View view);

        MyCarsListManagerComponent.Builder appComponent(AppComponent appComponent);

        MyCarsListManagerComponent build();
    }
}