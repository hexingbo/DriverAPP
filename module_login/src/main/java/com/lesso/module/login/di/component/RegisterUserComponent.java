package com.lesso.module.login.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.login.di.module.RegisterUserModule;
import com.lesso.module.login.mvp.contract.RegisterUserContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.login.mvp.ui.activity.RegisterUserActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 19:01
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = RegisterUserModule.class, dependencies = AppComponent.class)
public interface RegisterUserComponent {
    void inject(RegisterUserActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RegisterUserComponent.Builder view(RegisterUserContract.View view);

        RegisterUserComponent.Builder appComponent(AppComponent appComponent);

        RegisterUserComponent build();
    }
}