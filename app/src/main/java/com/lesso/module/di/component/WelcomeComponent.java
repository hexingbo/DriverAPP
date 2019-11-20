package com.lesso.module.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.di.module.WelcomeModule;
import com.lesso.module.mvp.contract.WelcomeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.mvp.ui.activity.WelcomeActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/14 20:51
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = WelcomeModule.class, dependencies = AppComponent.class)
public interface WelcomeComponent {
    void inject(WelcomeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WelcomeComponent.Builder view(WelcomeContract.View view);

        WelcomeComponent.Builder appComponent(AppComponent appComponent);

        WelcomeComponent build();
    }
}