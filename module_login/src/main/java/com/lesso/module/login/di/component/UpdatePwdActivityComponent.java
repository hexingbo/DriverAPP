package com.lesso.module.login.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.login.di.module.UpdatePwdActivityModule;
import com.lesso.module.login.mvp.contract.UpdatePwdActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.login.mvp.ui.activity.UpdatePwdActivityActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:47
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = UpdatePwdActivityModule.class, dependencies = AppComponent.class)
public interface UpdatePwdActivityComponent {
    void inject(UpdatePwdActivityActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UpdatePwdActivityComponent.Builder view(UpdatePwdActivityContract.View view);

        UpdatePwdActivityComponent.Builder appComponent(AppComponent appComponent);

        UpdatePwdActivityComponent build();
    }
}