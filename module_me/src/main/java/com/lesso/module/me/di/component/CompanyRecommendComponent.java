package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.CompanyRecommendModule;
import com.lesso.module.me.mvp.contract.CompanyRecommendContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.CompanyRecommendActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/22 09:41
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = CompanyRecommendModule.class, dependencies = AppComponent.class)
public interface CompanyRecommendComponent {
    void inject(CompanyRecommendActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CompanyRecommendComponent.Builder view(CompanyRecommendContract.View view);

        CompanyRecommendComponent.Builder appComponent(AppComponent appComponent);

        CompanyRecommendComponent build();
    }
}