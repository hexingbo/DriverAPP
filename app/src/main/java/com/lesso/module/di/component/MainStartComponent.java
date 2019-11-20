package com.lesso.module.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.di.module.MainStartModule;
import com.lesso.module.mvp.contract.MainStartContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.mvp.ui.activity.MainStartActivity;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/10/02 16:30
 * 描    述：
 * =============================================
 */
@ActivityScope
@Component(modules = MainStartModule.class, dependencies = AppComponent.class)
public interface MainStartComponent {
    void inject(MainStartActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MainStartContract.View view);

        Builder appComponent(AppComponent appComponent);

        MainStartComponent build();
    }
}