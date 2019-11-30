package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.ChoiceJoinCompanyCarsModule;
import com.lesso.module.me.mvp.contract.ChoiceJoinCompanyCarsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.ChoiceJoinCompanyCarsActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/26 10:42
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = ChoiceJoinCompanyCarsModule.class, dependencies = AppComponent.class)
public interface ChoiceJoinCompanyCarsComponent {
    void inject(ChoiceJoinCompanyCarsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChoiceJoinCompanyCarsComponent.Builder view(ChoiceJoinCompanyCarsContract.View view);

        ChoiceJoinCompanyCarsComponent.Builder appComponent(AppComponent appComponent);

        ChoiceJoinCompanyCarsComponent build();
    }
}