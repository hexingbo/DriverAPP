package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.MeMainModule;
import com.lesso.module.me.mvp.contract.MeMainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.MeMainActivity;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 10:04
 * 描    述：
 * =============================================
 */
@ActivityScope
@Component(modules = MeMainModule.class, dependencies = AppComponent.class)
public interface MeMainComponent {
    void inject(MeMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MeMainComponent.Builder view(MeMainContract.View view);

        MeMainComponent.Builder appComponent(AppComponent appComponent);

        MeMainComponent build();
    }
}