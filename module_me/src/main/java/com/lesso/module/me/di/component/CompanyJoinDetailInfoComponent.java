package com.lesso.module.me.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lesso.module.me.di.module.CompanyJoinDetailInfoModule;
import com.lesso.module.me.mvp.contract.CompanyJoinDetailInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lesso.module.me.mvp.ui.activity.CompanyJoinDetailInfoActivity;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/18 10:32
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = CompanyJoinDetailInfoModule.class, dependencies = AppComponent.class)
public interface CompanyJoinDetailInfoComponent {
    void inject(CompanyJoinDetailInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CompanyJoinDetailInfoComponent.Builder view(CompanyJoinDetailInfoContract.View view);

        CompanyJoinDetailInfoComponent.Builder appComponent(AppComponent appComponent);

        CompanyJoinDetailInfoComponent build();
    }
}