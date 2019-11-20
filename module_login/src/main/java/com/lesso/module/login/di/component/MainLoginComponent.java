package com.lesso.module.login.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.lesso.module.login.di.module.MainLoginModule;
import com.lesso.module.login.mvp.contract.MainLoginContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.login.mvp.ui.activity.MainLoginActivity;



@ActivityScope
@Component(modules = MainLoginModule.class, dependencies = AppComponent.class)
public interface MainLoginComponent {
    void inject(MainLoginActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        MainLoginComponent.Builder view(MainLoginContract.View view);
        MainLoginComponent.Builder appComponent(AppComponent appComponent);
        MainLoginComponent build();
    }
}