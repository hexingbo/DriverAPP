package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.MainMyModule;
import com.lesso.module.me.mvp.contract.MainMyContract;

import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.me.mvp.ui.fragment.MainMyFragment;


/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/09/27 11:24
 * 描    述：
 * =============================================
 */
@FragmentScope
@Component(modules = MainMyModule.class, dependencies = AppComponent.class)
public interface MainMyComponent {
    void inject(MainMyFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainMyComponent.Builder view(MainMyContract.View view);

        MainMyComponent.Builder appComponent(AppComponent appComponent);

        MainMyComponent build();
    }
}