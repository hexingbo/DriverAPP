package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.UserInfoModule;
import com.lesso.module.me.mvp.contract.UserInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.UserInfoActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = UserInfoModule.class, dependencies = AppComponent.class)
public interface UserInfoComponent {
    void inject(UserInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserInfoComponent.Builder view(UserInfoContract.View view);

        UserInfoComponent.Builder appComponent(AppComponent appComponent);

        UserInfoComponent build();
    }
}