package com.lesso.module.message.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.message.di.module.MessageDetailModule;
import com.lesso.module.message.mvp.contract.MessageDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.message.mvp.ui.activity.MessageDetailActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 12:25
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = MessageDetailModule.class, dependencies = AppComponent.class)
public interface MessageDetailComponent {
    void inject(MessageDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageDetailComponent.Builder view(MessageDetailContract.View view);

        MessageDetailComponent.Builder appComponent(AppComponent appComponent);

        MessageDetailComponent build();
    }
}