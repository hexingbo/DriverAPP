package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.AboutUsModule;
import com.lesso.module.me.mvp.contract.AboutUsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.AboutUsActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:27
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = AboutUsModule.class, dependencies = AppComponent.class)
public interface AboutUsComponent {
    void inject(AboutUsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AboutUsComponent.Builder view(AboutUsContract.View view);

        AboutUsComponent.Builder appComponent(AppComponent appComponent);

        AboutUsComponent build();
    }
}