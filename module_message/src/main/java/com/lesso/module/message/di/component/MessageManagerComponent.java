package com.lesso.module.message.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.message.di.module.MessageManagerModule;
import com.lesso.module.message.mvp.contract.MessageManagerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.lesso.module.message.mvp.ui.fragment.MessageManagerFragment;



@FragmentScope
@Component(modules = MessageManagerModule.class, dependencies = AppComponent.class)
public interface MessageManagerComponent {
    void inject(MessageManagerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageManagerComponent.Builder view(MessageManagerContract.View view);

        MessageManagerComponent.Builder appComponent(AppComponent appComponent);

        MessageManagerComponent build();
    }
}