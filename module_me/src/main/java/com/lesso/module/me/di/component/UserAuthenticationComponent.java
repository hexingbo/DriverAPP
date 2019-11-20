package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.UserAuthenticationModule;
import com.lesso.module.me.mvp.contract.UserAuthenticationContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.UserAuthenticationActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 15:31
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = UserAuthenticationModule.class, dependencies = AppComponent.class)
public interface UserAuthenticationComponent {
    void inject(UserAuthenticationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserAuthenticationComponent.Builder view(UserAuthenticationContract.View view);

        UserAuthenticationComponent.Builder appComponent(AppComponent appComponent);

        UserAuthenticationComponent build();
    }
}