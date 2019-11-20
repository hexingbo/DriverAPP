package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.UpdatePwdModule;
import com.lesso.module.me.mvp.contract.UpdatePwdContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.UpdatePwdActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = UpdatePwdModule.class, dependencies = AppComponent.class)
public interface UpdatePwdComponent {
    void inject(UpdatePwdActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UpdatePwdComponent.Builder view(UpdatePwdContract.View view);

        UpdatePwdComponent.Builder appComponent(AppComponent appComponent);

        UpdatePwdComponent build();
    }
}