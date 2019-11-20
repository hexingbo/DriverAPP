package com.lesso.module.message.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.message.di.module.MainMessageModule;
import com.lesso.module.message.mvp.contract.MainMessageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.message.mvp.ui.activity.MainMessageActivity;



@ActivityScope
@Component(modules = MainMessageModule.class, dependencies = AppComponent.class)
public interface MainMessageComponent {
    void inject(MainMessageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainMessageComponent.Builder view(MainMessageContract.View view);

        MainMessageComponent.Builder appComponent(AppComponent appComponent);

        MainMessageComponent build();
    }
}