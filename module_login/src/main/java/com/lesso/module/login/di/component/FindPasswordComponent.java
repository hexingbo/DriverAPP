package com.lesso.module.login.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.login.di.module.FindPasswordModule;
import com.lesso.module.login.mvp.contract.FindPasswordContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.login.mvp.ui.activity.FindPasswordActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 19:01
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = FindPasswordModule.class, dependencies = AppComponent.class)
public interface FindPasswordComponent {
    void inject(FindPasswordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FindPasswordComponent.Builder view(FindPasswordContract.View view);

        FindPasswordComponent.Builder appComponent(AppComponent appComponent);

        FindPasswordComponent build();
    }
}